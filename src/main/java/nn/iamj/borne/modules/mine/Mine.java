package nn.iamj.borne.modules.mine;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import nn.iamj.borne.modules.api.events.mine.MineCreateEvent;
import nn.iamj.borne.modules.api.events.mine.MineDestroyEvent;
import nn.iamj.borne.modules.api.events.mine.MineRegenerateEvent;
import nn.iamj.borne.modules.api.events.mine.MineTickEvent;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.arrays.BetterArrays;
import nn.iamj.borne.modules.util.blocks.Cuboid;
import nn.iamj.borne.modules.util.blocks.CuboidUtils;
import nn.iamj.borne.modules.util.event.EventUtils;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Mine {

    private final String id;
    @Setter
    private String label;

    private final Location spawnLocation;

    private final Cuboid area;
    private final Cuboid mineArea;

    @Setter
    private Map<Material, Integer> materials;

    private final MineHologram hologram;
    private final MineSettings settings;

    private long lastRegenerate;
    private double ratio;

    public Mine(final String id, final String label, final Location spawnLocation, final Location hologramLocation, final Location firstAreaPoint, final Location secondAreaPoint, final Location firstMineLocation, final Location secondMineLocation, final MineSettings settings) {
        this.id = id;
        this.label = label;

        this.area = new Cuboid(firstAreaPoint, secondAreaPoint);
        this.mineArea = new Cuboid(firstMineLocation, secondMineLocation);

        this.materials = new ConcurrentHashMap<>();
        this.settings = settings;

        this.spawnLocation = spawnLocation;
        this.hologram = new MineHologram(this, hologramLocation);

        EventUtils.callStaticEvent(new MineCreateEvent(this));
    }

    public void tick() {
        this.ratio = BetterNumbers.floor(4, CuboidUtils.getFillRatio(this.mineArea));

        this.hologram.updateHologram();

        if (System.currentTimeMillis() < this.lastRegenerate + (this.settings.getCooldown() * 1000L)) return;

        final boolean result = EventUtils.callEvent(new MineTickEvent(this));

        if (!result) return;

        if (this.ratio < this.settings.getMinRatio()) {
            if (Bukkit.isPrimaryThread()) this.regenerate();
            else Scheduler.handle(this::regenerate);
        }
    }

    public void regenerate() {
        final boolean result = EventUtils.callEvent(new MineRegenerateEvent(this));

        if (!result) return;

        this.lastRegenerate = System.currentTimeMillis();

        if (this.materials.isEmpty()) return;

        for (final Block block : this.mineArea.blockList())
            block.setType(BetterArrays.randomElement(this.materials), false);
    }

    public void destroy() {
        EventUtils.callStaticEvent(new MineDestroyEvent(this));

        this.hologram.deleteHologram();
        this.mineArea.blockList().forEach(block ->
                block.setType(Material.AIR, false));
    }

}


package nn.iamj.borne.modules.mine.builder;

import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.mine.MineSettings;

import java.util.Map;
import java.util.Objects;

@Setter
public final class MineBuilder {

    private String id;
    private String label;

    private Location spawnPoint;
    private Location hologramPoint;

    private Location areaFirstPoint;
    private Location areaSecondPoint;

    private Location mineFirstPoint;
    private Location mineSecondPoint;

    private double ratio;
    private int cooldown;
    private int minLevel;
    private boolean allowPvP;
    private int priority;

    private Map<Material, Integer> materials;

    private MineBuilder() {}

    public static MineBuilder createBuilder() {
        return new MineBuilder();
    }

    public Mine createMine() {
        if (Objects.isNull(id)
                || Objects.isNull(areaFirstPoint)
                || Objects.isNull(areaSecondPoint)
                || Objects.isNull(mineFirstPoint)
                || Objects.isNull(mineSecondPoint)
                || Objects.isNull(materials)
                || Objects.isNull(spawnPoint)
                || Objects.isNull(hologramPoint)
                || Objects.isNull(label))
            return null;

        final MineSettings settings = new MineSettings();

        settings.setAllowPvP(this.allowPvP);
        settings.setMinLevel(this.minLevel);
        settings.setMinRatio(this.ratio);
        settings.setCooldown(this.cooldown);
        settings.setPriority(this.priority);

        final Mine mine = new Mine(id, label, spawnPoint, hologramPoint, areaFirstPoint, areaSecondPoint, mineFirstPoint, mineSecondPoint, settings);

        mine.setMaterials(this.materials);

        return mine;
    }

}

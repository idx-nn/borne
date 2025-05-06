package nn.iamj.borne.modules.mine;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.server.printing.Coloriser;
import nn.iamj.borne.modules.util.arrays.BetterProgress;

import java.util.List;
import java.util.Objects;

@Getter
public final class MineHologram {

    private final Mine mine;
    private final Location location;

    private Hologram hologram;

    public MineHologram(final Mine mine, final Location location) {
        this.mine = mine;
        this.location = location;

        this.createHologram();
        this.updateHologram();
    }

    public void createHologram() {
        this.hologram = DHAPI.getHologram(mine.getId()) == null ?
                DHAPI.createHologram(mine.getId(), location, false) :
                DHAPI.getHologram(mine.getId());
    }

    public void updateHologram() {
        if (this.hologram == null)
            return;

        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null) return;

        final String progress = BetterProgress.generateProgress(mine.getRatio());

        if (progress == null) return;

        final List<String> strings = configuration.getStringList("MINES.DISPLAY.HOLOGRAM").stream()
                .map(string -> string
                        .replace("{MINE_LABEL}", Coloriser.colorify(mine.getLabel()))
                        .replace("{MINE_RATIO}", mine.getRatio() + "")
                        .replace("{MINE_MIN_LEVEL}", mine.getSettings().getMinLevel() + "")
                        .replace("{MINE_ALLOW_PVP}", mine.getSettings().isAllowPvP() ?
                                configuration.getString("MINES.PLACEHOLDER.ALLOW-PVP.TRUE", "&x&1&1&f&f&1&1Включено") :
                                configuration.getString("MINES.PLACEHOLDER.ALLOW-PVP.FALSE", "&x&f&f&1&1&1&1Выключено"))
                        .replace("{MINE_PROGRESS_BAR}", progress))
                .toList();

        DHAPI.setHologramLines(this.hologram, strings);
    }

    public void deleteHologram() {
        if (this.hologram == null)
            return;

        this.hologram.delete();
    }

}

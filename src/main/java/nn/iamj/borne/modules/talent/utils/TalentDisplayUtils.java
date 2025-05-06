package nn.iamj.borne.modules.talent.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.List;
import java.util.Locale;

public final class TalentDisplayUtils {

    private TalentDisplayUtils() {}

    public static Material getIcon(final TalentType type) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return Material.AIR;

        return Material.valueOf(configuration.getString("TALENTS." + type + ".MATERIAL", "stone").toUpperCase(Locale.ROOT));
    }

    public static String getDisplay(final TalentType type) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return null;

        return configuration.getString("TALENTS." + type + ".NAME", "");
    }

    public static List<String> getDescription(final TalentType type) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return null;

        return configuration.getStringList("TALENTS." + type + ".DESCRIPTION");
    }

    public static double getSellRatio(final TalentType type) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return 0.0D;

        return configuration.getDouble("TALENTS." + type + ".RATIO", 0.0D);
    }

}

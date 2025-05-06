package nn.iamj.borne.modules.skill.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.skill.SkillType;

import java.util.Locale;

public final class SkillDisplayUtils {

    private SkillDisplayUtils() {}

    public static Material getIcon(final SkillType type) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return Material.AIR;

        return Material.valueOf(configuration.getString("SKILLS." + type + ".MATERIAL", "stone").toUpperCase(Locale.ROOT));
    }

    public static String getDisplay(final SkillType type) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return null;

        return configuration.getString("SKILLS." + type + ".NAME", "");
    }

    public static double getSellRatio(final SkillType type) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return 0.0D;

        return configuration.getDouble("SKILLS." + type + ".RATIO", 0.0D);
    }

}

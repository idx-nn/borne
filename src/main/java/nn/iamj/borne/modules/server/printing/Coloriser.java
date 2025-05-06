package nn.iamj.borne.modules.server.printing;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Coloriser {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    private Coloriser() {}

    public static String colorifyLegacy(final String string) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null.");
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String colorify(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null.");
        }

        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            String hex = string.substring(matcher.start(), matcher.end());
            string = string.replace(hex, "" + ChatColor.of(hex));
            matcher = pattern.matcher(string);
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

}

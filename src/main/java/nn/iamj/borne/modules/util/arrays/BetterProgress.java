package nn.iamj.borne.modules.util.arrays;

import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;

public final class BetterProgress {

    private BetterProgress() {}

    public static String generateProgress(double progress) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return null;

        final String adderStart = configuration.getString("MINES.PROGRESS_BAR.BUILDER.START", "");
        final String adderEnd = configuration.getString("MINES.PROGRESS_BAR.BUILDER.END", "");

        final StringBuilder builder = new StringBuilder(adderStart);

        final String charFill = configuration.getString("MINES.PROGRESS_BAR.CHARS.FILL", "");
        final String charEmpty = configuration.getString("MINES.PROGRESS_BAR.CHARS.EMPTY", "");

        final int number = configuration.getInt("MINES.PROGRESS_BAR.PARTS", 20);

        for (int i = 0; i <= number; progress -= (100.0D / number)) {
            if (progress < 0)
                builder.append(charEmpty);
            else builder.append(charFill);
            i++;
        }

        return builder.append(adderEnd).toString();
    }

}

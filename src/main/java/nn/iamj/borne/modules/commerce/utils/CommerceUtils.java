package nn.iamj.borne.modules.commerce.utils;

import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.commerce.unit.CommerceUnit;
import nn.iamj.borne.modules.commerce.wallet.CommercePrice;
import nn.iamj.borne.modules.commerce.wallet.CommerceWallet;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CommerceUtils {

    private static final List<CommerceUnit> commerceList = new ArrayList<>();

    private CommerceUtils() {}

    public static void registerInflationUnit(final CommerceUnit unit) {
        commerceList.add(unit);
    }

    public static void loadTask() {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("config.yml");

        if (configuration == null) return;

        Scheduler.asyncHandleRate(() -> {
            commerceList.forEach(CommerceUnit::tick);
        }, configuration.getInt("COMMERCE.TICK-RATE", 1200), configuration.getInt("COMMERCE.TICK-RATE", 1200));
    }

    public static boolean has(final @NotNull Profile profile, final CommercePrice price) {
        final Map<CommerceWallet, Double> priceList = price.getPriceList();

        if (priceList.isEmpty())
            return true;

        for (final Map.Entry<CommerceWallet, Double> wallet : priceList.entrySet()) {
            final CommerceWallet commerce = wallet.getKey();
            final double count = wallet.getValue();

            switch (commerce) {
                case MONEY -> { if (profile.getWallet().getMoney() < count) return false; }
                case DONATED -> { if (profile.getWallet().getDonated() < count) return false; }

                default -> { if (profile.getStorage().getWallet(commerce.convert()) < count) return false; }
            }
        }

        return true;
    }

    public static void pay(final @NotNull Profile profile, final CommercePrice price) {
        final Map<CommerceWallet, Double> priceList = price.getPriceList();

        if (priceList.isEmpty())
            return;

        for (final Map.Entry<CommerceWallet, Double> wallet : priceList.entrySet()) {
            final CommerceWallet commerce = wallet.getKey();
            final double count = wallet.getValue();

            switch (commerce) {
                case MONEY -> profile.getWallet().removeMoney(count);
                case DONATED -> profile.getWallet().removeDonated(count);

                default -> profile.getStorage().removeWallet(commerce.convert(), (int) count);
            }
        }

        profile.save(true);
    }

}

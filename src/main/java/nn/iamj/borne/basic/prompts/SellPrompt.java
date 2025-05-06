package nn.iamj.borne.basic.prompts;

import nn.iamj.borne.modules.booster.Booster;
import nn.iamj.borne.modules.booster.BoosterStorage;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.storage.PricesStorage;
import nn.iamj.borne.modules.util.addons.messenger.Messenger;
import nn.iamj.borne.modules.util.collection.pair.Pair;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.inventory.InventoryUtils;
import nn.iamj.borne.modules.util.logger.LoggerProvider;
import nn.iamj.borne.modules.util.math.BetterNumbers;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class SellPrompt {

    private SellPrompt() {}

    public static boolean handle(final @NotNull Profile profile) {
        final Player player = profile.asBukkit();

        if (player == null || !player.isOnline()) return false;

        final Inventory inventory = player.getInventory();

        double result = 0.0D;
        for (final ItemStack stack : inventory) {
            if (stack == null || stack.getType() == Material.AIR) continue;

            final Material material = stack.getType();

            final double price = PricesStorage.getPrice(material);
            if (price != 0.0D) {
                result += price * stack.getAmount();

                InventoryUtils.removeItems(player, stack,
                        stack.getAmount());
            }
        }

        if (result == 0.0D) {
            Messenger.sendMessage(player, "&cУ Вас нету чего продать..");
            return false;
        }

        final BoosterStorage storage = BoosterStorage.getInstance();

        if (storage != null) {
            final Pair<String, Booster> pair = storage.getActiveBooster();

            if (pair != null) {
                final Booster booster = pair.getValue();

                result = result * booster.getModifier();

                Messenger.sendMessage(player, "&7Вы получили &e" + BetterNumbers.floor(4, result) + " &f혤 &6[x" + booster.getModifier() + "]");
            } else Messenger.sendMessage(player, "&7Вы получили &e" + BetterNumbers.floor(4, result) + " &f혤");
        }

        profile.getWallet().addMoney(result);
        profile.save(true);

        return true;
    }

    public static boolean handle(final @NotNull Profile profile, final ItemStack stack) {
        final Player player = profile.asBukkit();

        if (player == null || !player.isOnline()) return false;

        final Material material = stack.getType();

        final double price = PricesStorage.getPrice(material);
        if (price != 0.0D) {
            InventoryUtils.removeItems(player, stack,
                    stack.getAmount());
        }

        final double result = price * stack.getAmount();

        profile.getWallet().addMoney(result);
        profile.save(true);

        Messenger.sendMessage(player, "&7Вы получили &e" + result + " &f혤");

        return true;
    }

}

package nn.iamj.borne.modules.commerce.unit;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.commerce.utils.CommerceUtils;
import nn.iamj.borne.modules.commerce.wallet.CommercePrice;
import nn.iamj.borne.modules.commerce.wallet.CommerceWallet;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.inventory.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommerceSlot extends CommerceUnit {

    private int slot = -1;

    public static MenuSlot convertToMenu(final CommerceSlot slot) {
        final MenuSlot item = new MenuSlot(slot.getDisplay().clone());

        item.setPosition(slot.getSlot());

        final List<Text> lore = new ArrayList<>();
        lore.add(new Text(" " + Component.ORANGE + "Для покупки требуется:"));
        for (final CommerceWallet wallet : slot.getPrice().getPriceList().keySet()) {
            if (wallet == CommerceWallet.MONEY)
                lore.add(new Text(" &8- " + Component.ORANGE + slot.getPrice().getPrice(CommerceWallet.MONEY) + " &f혤"));
            if (wallet == CommerceWallet.DONATED)
                lore.add(new Text(" &8- &x&e&7&9&2&f&0" + slot.getPrice().getPrice(CommerceWallet.DONATED) + " &f혣"));
        }
        lore.add(new Text());
        lore.add(new Text("&8» &eНажмите для покупки.."));

        item.addLore(lore);
        item.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final @NotNull Profile profile) {
                final Player player = profile.asBukkit();

                if (player == null) return;

                if (!CommerceUtils.has(profile, slot.getPrice())) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно материалов для покупки этого товара."));
                    return;
                }

                CommerceUtils.pay(profile, slot.getPrice());

                InventoryUtils.addItems(player, slot.getStackList());
                slot.getExecutable().onBuy(profile);

                if (Bukkit.isPrimaryThread()) {
                    slot.getCommandList().forEach(command ->
                            Borne.getBorne().getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), command));
                } else Scheduler.handle(() ->
                        slot.getCommandList().forEach(command ->
                            Borne.getBorne().getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), command)));

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно приобрели этот товар."));
            }
        });

        return item;
    }

    public static CommerceSlot convertToSlot(final CommerceUnit unit) {
        final CommerceSlot slot = new CommerceSlot();

        slot.setDisplay(unit.getDisplay());
        slot.setPrice(unit.getPrice());
        slot.setInflation(unit.getInflation());
        slot.setRatio(unit.getRatio());

        slot.setStackList(unit.getStackList());
        slot.setCommandList(unit.getCommandList());

        return slot;
    }

}

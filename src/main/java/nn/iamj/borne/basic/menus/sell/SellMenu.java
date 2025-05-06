package nn.iamj.borne.basic.menus.sell;

import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.prompts.SellPrompt;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.component.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class SellMenu {

    private SellMenu() {}

    public static void open(final Profile profile) {
        final Menu menu = Borne.getBorne().getMenuManager().createMenu("sell", profile.getName(), new Text("&8Продажа блоков"), 6);

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++)
            menu.setSlot(i, fill);
        for (int i = 45; i < 54; i++)
            menu.setSlot(i, fill);

        final MenuSlot sell = new MenuSlot(Material.GREEN_STAINED_GLASS_PANE);
        sell.setDisplay(new Text(Component.GREEN + "Продать все блоки"));
        sell.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                SellPrompt.handle(profile);

                final Player player = profile.asBukkit();

                if (player == null) return;

                player.closeInventory();
            }
        });

        menu.setSlot(21, sell);
        menu.setSlot(22, sell);
        menu.setSlot(23, sell);
        menu.setSlot(30, sell);
        menu.setSlot(31, sell);
        menu.setSlot(32, sell);

        final MenuSlot exit = new MenuSlot(Material.IRON_BARS);
        exit.setDisplay(new Text(Component.RED + "Выйти"));
        exit.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();

                if (player == null) return;

                player.closeInventory();
            }
        });

        menu.setSlot(49, exit);

        final Player player = profile.asBukkit();

        if (player == null) return;

        player.openInventory(menu.getInventory());
    }

}

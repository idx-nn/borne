package nn.iamj.borne.modules.commerce.menu;

import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.commerce.Commerce;
import nn.iamj.borne.modules.commerce.page.CommercePage;
import nn.iamj.borne.modules.commerce.unit.CommerceSlot;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public final class CommerceMenu extends Menu {

    private final Player player;
    private final Commerce commerce;

    private int page;

    public CommerceMenu(final Player player, final Commerce commerce) {
        super("commerce", "Магазин", 6);

        this.player = player;
        this.commerce = commerce;

        this.page = 1;

        Borne.getBorne().getMenuManager().registerMenu(this.getId(), player.getName(), this);
    }

    public void open() {
        if (this.player == null) return;

        this.clean();

        final List<CommercePage> pages = commerce.getPages();

        if (pages.isEmpty())
            return;

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);

        fill.setDisplay(new Text("&8*"));

        for (int i = 0; i < 9; i++)
            this.setSlot(i, fill);
        for (int i = 45; i < 54; i++)
            this.setSlot(i, fill);

        final CommercePage commercePage = commerce.getPage(page);

        if (commercePage == null)
            return;

        for (final CommerceSlot slot : commercePage.getUnitList()) {
            if (slot.getSlot() == -1) {
                final int first = this.getInventory().firstEmpty();

                if (first == -1)
                    continue;

                this.setSlot(first, CommerceSlot.convertToMenu(slot));

                continue;
            }
            this.setSlot(CommerceSlot.convertToMenu(slot));
        }

        final MenuSlot exit = new MenuSlot(Material.IRON_BARS);

        exit.setDisplay(new Text("&x&f&f&1&1&1&1Выйти.."));
        exit.setExecutableClick(new ExecutableClick()  {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();
                if (player == null)
                    return;

                player.closeInventory();
            }
        });

        if (pages.size() > 1 && pages.size() != page) {
            final MenuSlot next = new MenuSlot(Material.ARROW);

            next.setDisplay(new Text("&eСледующая страница &8»"));
            next.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    page++;
                    open();
                }
            });

            this.setSlot(53, next);
        }

        if (pages.size() > 1 && page != 1) {
            final MenuSlot prev = new MenuSlot(Material.ARROW);

            prev.setDisplay(new Text("&8« &eПредыдущая страница"));
            prev.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    page--;
                    open();
                }
            });

            this.setSlot(45, prev);
        }

        setSlot(49, exit);

        this.player.openInventory(this.getInventory());
    }

}

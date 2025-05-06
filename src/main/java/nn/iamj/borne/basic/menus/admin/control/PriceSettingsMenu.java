package nn.iamj.borne.basic.menus.admin.control;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.storage.PricesStorage;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.List;

public final class PriceSettingsMenu extends Menu {

    private final Player player;

    public PriceSettingsMenu(final Player player) {
        super("price_settings", new Text("&8Настройка экономики").getRaw(), 5);

        this.setInteractDisabled(true);

        this.player = player;

        Borne.getBorne().getMenuManager().registerMenu(this.getId(), player.getName(), this);
    }

    public void openMenu(int page, boolean onlyBlocks) {
        if (this.player == null)
            return;

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);

        fill.setDisplay(new Text("&8*"));

        for (int i = 36; i < 45; i++)
            this.setSlot(i, fill);

        // -------------------------------

        List<Material> resources = List.of(Material.values());
        if (onlyBlocks)
            resources = resources.stream().filter(Material::isBlock).toList();

        final int pages = (int) Math.round(resources.size() / 36.0D);
        final int startAt = (page - 1) * 36;

        for (int i = startAt; i < resources.size(); i++) {
            final int firstEmpty = this.getInventory().firstEmpty();

            if (firstEmpty == -1)
                continue;

            final Material material = resources.get(i);

            final MenuSlot slot = new MenuSlot(material);
            final double price = PricesStorage.getPrice(material);

            slot.setLore(
                    new Text(),
                    new Text("&8| &fЦена: &e" + BetterNumbers.floor(4, price) + " &f혤 "),
                    new Text(),
                    new Text("&8«" + Component.ORANGE + " ЛКМ +           - ПКМ &8»")
            );

            slot.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    PricesStorage.addPrice(material, Math.min(65535, price + 0.0001));

                    clean();
                    openMenu(page, onlyBlocks);
                    player.playSound(player.getLocation(),
                            Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
                }

                @Override
                public void onRight(final Profile profile) {
                    PricesStorage.addPrice(material, Math.max(0.0001, price - 0.0001));

                    clean();
                    openMenu(page, onlyBlocks);
                    player.playSound(player.getLocation(),
                            Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
                }

                @Override
                public void onShiftLeft(final Profile profile) {
                    PricesStorage.addPrice(material, Math.min(65535, price + 0.001));

                    clean();
                    openMenu(page, onlyBlocks);
                    player.playSound(player.getLocation(),
                            Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
                }

                @Override
                public void onShiftRight(final Profile profile) {
                    PricesStorage.addPrice(material, Math.max(0.0001, price - 0.001));

                    clean();
                    openMenu(page, onlyBlocks);
                    player.playSound(player.getLocation(),
                            Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
                }
            });

            this.setSlot(firstEmpty, slot);
        }

        // -------------------------------

        if (page != pages) {
            final MenuSlot to = new MenuSlot(Material.ARROW);
            to.setDisplay(new Text(Component.ORANGE + "Вперед" + " &8»"));

            to.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    clean();
                    openMenu(page + 1, onlyBlocks);
                }
            });

            setSlot(44, to);
        }

        if (page > 1) {
            final MenuSlot back = new MenuSlot(Material.ARROW);
            back.setDisplay(new Text("&8«" + Component.ORANGE + " Назад"));

            back.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    clean();
                    openMenu(page - 1, onlyBlocks);
                }
            });

            setSlot(36, back);
        }

        final MenuSlot exit = new MenuSlot(Material.IRON_BARS);

        exit.setDisplay(new Text("&x&1&1&f&f&1&1Сохранить изменения.."));
        exit.setExecutableClick(new ExecutableClick()  {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();
                if (player == null)
                    return;

                player.closeInventory();

                PricesStorage.savePrices();

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно сохранили текущие настройки!"));
            }
        });

        setSlot(40, exit);

        final MenuSlot change = new MenuSlot(onlyBlocks ? Material.GREEN_DYE : Material.ORANGE_DYE);

        change.setDisplay(new Text("&eФильтр: &6Только блоки.. " + (!onlyBlocks ? Component.RED + "(Выкл.)" : Component.GREEN + "(Вкл.)")));
        change.setEnchanted(onlyBlocks);

        change.setExecutableClick(new ExecutableClick()  {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();
                if (player == null)
                    return;

                clean();
                openMenu(1, !onlyBlocks);
            }
        });

        setSlot(42, change);

        this.player.openInventory(this.getInventory());
    }

}

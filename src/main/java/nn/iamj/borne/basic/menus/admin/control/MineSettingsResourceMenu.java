package nn.iamj.borne.basic.menus.admin.control;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.gameplay.listeners.menu.admin.MineSettingsListener;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.mine.MineSettings;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.component.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MineSettingsResourceMenu extends Menu {

    private final Player player;
    private final Mine mine;

    private int chance;

    public MineSettingsResourceMenu(final Player player, final Mine mine) {
        super("mine_settings", new Text("&8Настройка шахты " + mine.getId().toUpperCase()).getRaw(), 5);

        this.setInteractDisabled(true);

        this.player = player;
        this.mine = mine;

        this.chance = 10;

        Borne.getBorne().getMenuManager().registerMenu(this.getId(), player.getName(), this);
    }

    public void openMenu(int page) {
        if (this.player == null || this.mine == null)
            return;

        MineSettingsListener.getChangeLabelCache().remove(this.player.getName());

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);

        fill.setDisplay(new Text("&8*"));

        for (int i = 36; i < 45; i++)
            this.setSlot(i, fill);

        // -------------------------------

        final List<Material> resources = new ArrayList<>(Arrays.stream(Material.values()).filter(Material::isBlock).toList());
        final int pages = (int) Math.round(resources.size() / 36.0D);
        final int startAt = (page - 1) * 36;

        for (int i = startAt; i < resources.size(); i++) {
            final int firstEmpty = this.getInventory().firstEmpty();

            if (firstEmpty == -1)
                continue;

            final Material material = resources.get(i);
            final MenuSlot slot = new MenuSlot(material);

            slot.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    mine.getMaterials().put(material, chance);
                    (new MineSettingsMenu(player, mine)).openMenu();
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
                    openMenu(page + 1);
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
                    openMenu(page - 1);
                }
            });

            setSlot(36, back);
        }

        resources.clear();

        final MenuSlot chanceSlot = new MenuSlot(Material.ORANGE_DYE);

        chanceSlot.setDisplay(new Text("&f혲 " + Component.ORANGE + "Шансы на появление ресурса."));
        chanceSlot.setLore(
                new Text(""),
                new Text("&8| &fШансы: &e" + chance + "%"),
                new Text(""),
                new Text("&8«" + Component.ORANGE + " ЛКМ -           + ПКМ &8»")
        );
        chanceSlot.removeAttributes(true);

        chanceSlot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                chance = Math.max(chance - 1, 1);

                openMenu(page);
            }

            @Override
            public void onRight(final Profile profile) {
                chance = Math.min(chance + 1, 100);

                openMenu(page);
            }

            @Override
            public void onShiftLeft(final Profile profile) {
                chance = Math.max(chance - 10, 1);

                openMenu(page);
            }

            @Override
            public void onShiftRight(final Profile profile) {
                chance = Math.min(chance + 10, 100);

                openMenu(page);
            }
        });

        setSlot(38, chanceSlot);

        final MenuSlot slot = new MenuSlot(Material.IRON_BARS);

        slot.setDisplay(new Text("&8» &x&f&f&1&1&1&1Назад, в меню редактирования.."));
        slot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                (new MineSettingsMenu(player, mine)).openMenu();
                player.playSound(player.getLocation(),
                        Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
            }
        });

        setSlot(40, slot);

        this.player.openInventory(this.getInventory());
    }

}

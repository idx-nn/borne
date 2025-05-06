package nn.iamj.borne.basic.menus.admin.control;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.gameplay.listeners.menu.admin.MineSettingsListener;
import nn.iamj.borne.managers.impl.addons.MineManager;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.mine.MineSettings;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.component.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class MineSettingsMenu extends Menu {

    private final Player player;
    private final Mine mine;

    public MineSettingsMenu(final Player player, final Mine mine) {
        super("mine_settings", new Text("&8Настройка шахты " + mine.getId().toUpperCase()).getRaw(), 5);

        this.setInteractDisabled(true);

        this.player = player;
        this.mine = mine;

        Borne.getBorne().getMenuManager().registerMenu(this.getId(), player.getName(), this);
    }

    public void openMenu() {
        if (this.player == null || this.mine == null)
            return;

        MineSettingsListener.getChangeLabelCache().remove(this.player.getName());

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);

        fill.setDisplay(new Text("&8*"));

        for (int i = 0; i < 9; i++)
            this.setSlot(i, fill);
        for (int i = 36; i < 45; i++)
            this.setSlot(i, fill);

        // -------------------------------

        this.setSlot(13, changeLabelSlot());

        this.setSlot(20, allowPvPSlot());
        this.setSlot(21, minLevelSlot());

        this.setSlot(22, prioritySlot());

        this.setSlot(23, minRatioSlot());
        this.setSlot(24, sleepSlot());

        this.setSlot(31, materialsSlot());

        // -------------------------------

        final MenuSlot exit = new MenuSlot(Material.IRON_BARS);

        exit.setDisplay(new Text("&x&1&1&f&f&1&1Сохранить изменения.."));
        exit.setExecutableClick(new ExecutableClick()  {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();
                if (player == null)
                    return;

                player.closeInventory();

                saveChanges(profile);
            }
        });

        setSlot(40, exit);

        this.player.openInventory(this.getInventory());
    }

    private MenuSlot changeLabelSlot() {
        final MenuSlot changeLabel = new MenuSlot(Material.NAME_TAG);

        changeLabel.setDisplay(new Text("&f혲 " + Component.ORANGE + "Имя шахты."));
        changeLabel.setLore(
                new Text(""),
                new Text("&8| &fИмя: &e" + this.mine.getLabel() + " "),
                new Text(""),
                new Text("&8» " + Component.ORANGE + "Нажмите, чтобы сменить имя.")
        );
        changeLabel.removeAttributes(true);

        changeLabel.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();
                if (player == null)
                    return;

                MineSettingsListener.getChangeLabelCache().put(profile.getName(), mine);

                player.closeInventory();

                profile.sendText(Component.text(Component.Type.SUCCESS, "У Вас есть &e180" + Component.GREEN + " секунд, для того чтобы написать новое имя в чат."));
            }
        });

        return changeLabel;
    }

    private MenuSlot allowPvPSlot() {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return null;

        final MenuSlot allowPvPSlot = new MenuSlot(Material.IRON_SWORD);

        allowPvPSlot.setDisplay(new Text("&f혲 " + Component.ORANGE + "PvP на территории шахты."));
        allowPvPSlot.setLore(
                new Text(""),
                new Text("&8| &fСтатус: " + (mine.getSettings().isAllowPvP() ?
                        configuration.getString("MINES.PLACEHOLDER.ALLOW-PVP.TRUE", "&x&1&1&f&f&1&1Включено") :
                        configuration.getString("MINES.PLACEHOLDER.ALLOW-PVP.FALSE", "&x&f&f&1&1&1&1Выключено"))),
                new Text(""),
                new Text("&8» " + Component.ORANGE + "Нажмите, чтобы переключить статус.")
        );
        allowPvPSlot.removeAttributes(true);

        allowPvPSlot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setAllowPvP(!settings.isAllowPvP());

                openMenu();
            }
        });

        return allowPvPSlot;
    }

    private MenuSlot prioritySlot() {
        final MenuSlot prioritySlot = new MenuSlot(Material.ENDER_EYE);

        prioritySlot.setDisplay(new Text("&f혲 " + Component.ORANGE + "Приоритет."));
        prioritySlot.setLore(
                new Text(""),
                new Text("&8| &fЗначение: &e" + mine.getSettings().getPriority()),
                new Text(""),
                new Text("&8«" + Component.ORANGE + " ЛКМ -           + ПКМ &8»")
        );
        prioritySlot.removeAttributes(true);

        prioritySlot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setPriority(Math.max(settings.getPriority() - 1, 1));

                openMenu();
            }

            @Override
            public void onRight(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setPriority(Math.min(settings.getPriority() + 1, 65535));

                openMenu();
            }
        });

        return prioritySlot;
    }

    private MenuSlot minLevelSlot() {
        final MenuSlot minLevelSlot = new MenuSlot(Material.TRIDENT);

        minLevelSlot.setDisplay(new Text("&f혲 " + Component.ORANGE + "Минимальный уровень для доступа."));
        minLevelSlot.setLore(
                new Text(""),
                new Text("&8| &fУровень: &e" + mine.getSettings().getMinLevel()),
                new Text(""),
                new Text("&8«" + Component.ORANGE + " ЛКМ -           + ПКМ &8»")
        );
        minLevelSlot.removeAttributes(true);

        minLevelSlot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setMinLevel(Math.max(settings.getMinLevel() - 1, 1));

                openMenu();
            }

            @Override
            public void onRight(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setMinLevel(Math.min(settings.getMinLevel() + 1, 65535));

                openMenu();
            }
        });

        return minLevelSlot;
    }

    private MenuSlot minRatioSlot() {
        final MenuSlot minRatioSlot = new MenuSlot(Material.OBSERVER);

        minRatioSlot.setDisplay(new Text("&f혲 " + Component.ORANGE + "Минимальный коэффициент для восстановления."));
        minRatioSlot.setLore(
                new Text(""),
                new Text("&8| &fКоэффициент: &e" + mine.getSettings().getMinRatio() + "%"),
                new Text(""),
                new Text("&8«" + Component.ORANGE + " ЛКМ -           + ПКМ &8»")
        );
        minRatioSlot.removeAttributes(true);

        minRatioSlot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setMinRatio(Math.max(settings.getMinRatio() - 1, 1));

                openMenu();
            }

            @Override
            public void onRight(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setMinRatio(Math.min(settings.getMinRatio() + 1, 100));

                openMenu();
            }
        });

        return minRatioSlot;
    }

    private MenuSlot sleepSlot() {
        final MenuSlot sleepSlot = new MenuSlot(Material.RED_BED);

        sleepSlot.setDisplay(new Text("&f혲 " + Component.ORANGE + "Спячка после выполнение регенерации."));
        sleepSlot.setLore(
                new Text(""),
                new Text("&8| &fВремя: &e" + mine.getSettings().getCooldown() + " сек."),
                new Text(""),
                new Text("&8«" + Component.ORANGE + " ЛКМ -           + ПКМ &8»")
        );
        sleepSlot.removeAttributes(true);

        sleepSlot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setCooldown(Math.max(settings.getCooldown() - 1, 0));

                openMenu();
            }

            @Override
            public void onRight(final Profile profile) {
                final MineSettings settings = mine.getSettings();

                settings.setCooldown(Math.min(settings.getCooldown() + 1, 4890));

                openMenu();
            }
        });

        return sleepSlot;
    }

    private MenuSlot materialsSlot() {
        final MenuSlot materialsSlot = new MenuSlot(Material.IRON_INGOT);

        materialsSlot.setDisplay(new Text("&f혲 " + Component.ORANGE + "Ресурсы шахты."));

        final List<Text> lore = new ArrayList<>();

        lore.add(new Text());
        lore.add(new Text("&8| &fСписок ресурсов: "));
        for (final Map.Entry<Material, Integer> entry : this.mine.getMaterials().entrySet())
            lore.add(new Text("  &e- &6" + entry.getKey().name().toUpperCase() + " &7(&e" + entry.getValue() + "%&7)  "));
        lore.add(new Text());
        lore.add(new Text("&8«" + Component.ORANGE + " ЛКМ +           - ПКМ &8»"));

        materialsSlot.setLore(lore);
        materialsSlot.removeAttributes(true);

        materialsSlot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                (new MineSettingsResourceMenu(player, mine)).openMenu(1);
                player.playSound(player.getLocation(),
                        Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
            }

            @Override
            public void onRight(final Profile profile) {
                mine.getMaterials().clear();

                openMenu();
            }
        });

        return  materialsSlot;
    }

    private void saveChanges(final Profile profile) {
        final MineManager mineManager = Borne.getBorne().getMineManager();

        if (mineManager.getMine(this.mine.getId()) == null) {
            profile.sendText(Component.text(Component.Type.ERROR, "Шахта, которую Вы редактировали, была удалена."));
            return;
        }

        this.mine.destroy();

        mineManager.saveMine(this.mine);
        mineManager.registerMine(this.mine);

        this.mine.getHologram().createHologram();

        this.mine.regenerate();
        this.mine.tick();

        Borne.getBorne().getConfigManager().reloadFile("mines.yml");

        profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно сохранили изменения, для полного вступления в силу действий перезапустите плагин."));
    }

}

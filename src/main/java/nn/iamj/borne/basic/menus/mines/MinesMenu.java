package nn.iamj.borne.basic.menus.mines;

import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.component.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MinesMenu extends Menu {

    private final Profile profile;

    public MinesMenu(final Profile profile) {
        super("mines", new Text("&8Шахты").getRaw(), 6);

        this.setInteractDisabled(true);

        this.profile = profile;

        Borne.getBorne().getMenuManager().registerMenu(this.getId(), profile.getName(), this);
    }

    public void open() {
        if (this.profile == null) return;

        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");
        final Player player = this.profile.asBukkit();

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);
        fill.setDisplay(new Text("&8*"));

        for (int i = 0; i < 9; i++)
            this.setSlot(i, fill);
        for (int i = 45; i < 54; i++)
            this.setSlot(i, fill);

        if (player == null) return;

        final LinkedList<Mine> mines = new LinkedList<>();

        for (int i = 0; i < 512; i++) {
            int finalI = i;
            final List<Mine> m = Borne.getBorne().getMineManager().getMines().values().stream().filter(mine -> mine.getSettings().getPriority() == finalI).toList();
            mines.addAll(m);
        }

        for (final Mine mine : mines) {
            final MenuSlot slot = new MenuSlot(mine.getMaterials().keySet()
                    .stream()
                    .findFirst()
                    .orElse(Material.STONE));

            slot.setDisplay(new Text("&eШахта &6" + mine.getLabel() + " "));

            final List<Text> lore = new ArrayList<>();

            lore.add(new Text());
            lore.add(new Text(" &8&fМинимальный уровень: &e" + mine.getSettings().getMinLevel()));
            lore.add(new Text(" &8&fPvP: " + (mine.getSettings().isAllowPvP() ?
                    configuration.getString("MINES.PLACEHOLDER.ALLOW-PVP.TRUE", "&x&1&1&f&f&1&1Включено") :
                    configuration.getString("MINES.PLACEHOLDER.ALLOW-PVP.FALSE", "&x&f&f&1&1&1&1Выключено"))));
            lore.add(new Text());
            if (mine.getSettings().getMinLevel() >= profile.getLevel().getLevel())
                lore.add(new Text("&8» &eНажмите для телепортации"));

            slot.setLore(lore);

            slot.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    if (profile.getLevel().getLevel() < mine.getSettings().getMinLevel() && !profile.asBukkit().hasPermission("borne.admin")) {
                        profile.sendText(Component.text(Component.Type.ERROR, "Ваш уровень мал для телепортации на эту шахту."));
                        return;
                    }

                    final Location location = mine.getSpawnLocation();

                    player.teleportAsync(location);
                    player.closeInventory();
                }
            });

            this.setSlot(this.getInventory().firstEmpty(), slot);
        }

        final MenuSlot exit = new MenuSlot(Material.IRON_BARS);

        exit.setDisplay(new Text("#ff0000Выход"));
        exit.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                player.closeInventory();
            }
        });

        this.setSlot(49, exit);

        Scheduler.handle(() -> player.openInventory(this.getInventory()));
    }

}

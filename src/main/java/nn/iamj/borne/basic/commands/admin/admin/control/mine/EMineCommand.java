package nn.iamj.borne.basic.commands.admin.admin.control.mine;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.menus.admin.control.MineSettingsMenu;
import nn.iamj.borne.managers.impl.ConfigManager;
import nn.iamj.borne.managers.impl.addons.MineManager;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.command.intefaces.CommandCompleter;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.mine.builder.MineBuilder;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.arrays.BetterParser;
import nn.iamj.borne.modules.util.component.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@CommandMeta(rights = "command.emine")
public final class EMineCommand extends Command implements CommandCompleter, Listener {

    private static final Map<String, List<Location>> cache = new ConcurrentHashMap<>();

    public EMineCommand() {
        super("emine", "The mine command.", "emines");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.isCancelled()) return;

        final Player player = event.getPlayer();

        if (!cache.containsKey(player.getName())) return;

        event.setCancelled(true);

        final List<Location> locations = cache.get(player.getName());

        final Profile profile = Profile.asEntity(player);

        if (profile == null)
            return;

        if (locations.size() > 4) {
            profile.sendText(Component.text(Component.Type.ERROR, "Вы уже указали все точки, встаньте на место голограммы и создайте шахту."));
            return;
        }

        locations.add(event.getBlock().getLocation());

        profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно добавили точку! :)"));
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (args.isEmpty()) {
            profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/mine <параметры>"));
            return;
        }

        final String arg = args.get(0);

        switch (arg) {
            case "edit" -> {
                if (profile.isSystemProvider()) {
                    profile.sendText(Component.text(Component.Type.WARNING, "Это действие не может быть выполнено."));
                    return;
                }

                if (args.size() != 2) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Укажите айди шахты, которую хотите изменить."));
                    return;
                }

                final Mine mine = Borne.getBorne().getMineManager().getMine(args.get(1).toUpperCase(Locale.ROOT));

                if (mine == null) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Шахта не найдена, попробуйте другой айди."));
                    return;
                }

                final Player player = profile.asBukkit();
                if (player == null) {
                    return;
                }

                (new MineSettingsMenu(player, mine)).openMenu();
                player.playSound(player.getLocation(),
                        Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);

                return;
            }
            case "create" -> {
                if (profile.isSystemProvider()) {
                    profile.sendText(Component.text(Component.Type.WARNING, "Это действие не может быть выполнено."));
                    return;
                }

                if (args.size() == 2) {
                    final String id = args.get(1);

                    if (id.equals("cancel")) {
                        if (!cache.containsKey(profile.getName())) {
                            profile.sendText(Component.text(Component.Type.WARNING, "Ничего не изменилось, у Вас небыло сессии."));
                            return;
                        }

                        cache.remove(profile.getName());

                        profile.sendText(Component.text(Component.Type.ERROR, "Вы снесли свою сессию создания шахты."));
                        return;
                    }

                    final Mine originalMine = Borne.getBorne().getMineManager().getMine(id);

                    if (originalMine != null) {
                        profile.sendText(Component.text(Component.Type.ERROR, "Такая шахта уже существует!"));
                        return;
                    }

                    final List<Location> locations = cache.get(profile.getName());

                    if (locations == null || locations.size() != 5) {
                        profile.sendText(Component.text(Component.Type.ERROR, "Вы не отметили точки."));
                        return;
                    }

                    final Location firstArea = locations.get(0);
                    final Location secondArea = locations.get(1);

                    final Location firstMineArea = locations.get(2);
                    final Location secondMineArea = locations.get(3);

                    final Location spawn = profile.asBukkit().getLocation();
                    final Location hologram = locations.get(4).clone().add(0.5, 0, 0.5);

                    final MineBuilder builder = MineBuilder.createBuilder();

                    builder.setId(id.toUpperCase(Locale.ROOT));
                    builder.setLabel(UUID.randomUUID().toString());

                    builder.setAreaFirstPoint(firstArea);
                    builder.setAreaSecondPoint(secondArea);

                    builder.setMineFirstPoint(firstMineArea);
                    builder.setMineSecondPoint(secondMineArea);

                    builder.setSpawnPoint(spawn);
                    builder.setHologramPoint(hologram);

                    final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("config.yml");

                    if (configuration == null) {
                        profile.sendText(Component.text(Component.Type.ERROR, "Произошла ошибка в создании шахты."));
                        return;
                    }

                    builder.setAllowPvP(configuration.getBoolean("MINES.DEFAULT.ALLOW-PVP", false));
                    builder.setRatio(configuration.getInt("MINES.DEFAULT.MIN-RATIO", 35));
                    builder.setMinLevel(configuration.getInt("MINES.DEFAULT.MIN-LEVEL", 1));
                    builder.setCooldown(configuration.getInt("MINES.DEFAULT.COOLDOWN", 140));

                    final Map<Material, Integer> map = new ConcurrentHashMap<>();
                    map.put(Material.STONE, 90);
                    map.put(Material.COBBLESTONE, 10);

                    builder.setMaterials(map);

                    final Mine mine = builder.createMine();

                    if (mine == null) {
                        profile.sendText(Component.text(Component.Type.ERROR, "Произошла ошибка в создании шахты."));
                        return;
                    }

                    mine.regenerate();

                    final MineManager mineManager = Borne.getBorne().getMineManager();

                    mineManager.saveMine(mine);
                    mineManager.registerMine(mine);

                    profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно создали шахту " + mine.getId() + "."));

                    cache.remove(profile.getName());

                    return;
                }

                cache.put(profile.getName(), new ArrayList<>());

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно создали сессию, сломайте точки."));

                return;
            }
            case "remove" -> {
                if (args.size() != 2) {
                    profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/mine remove <айди>"));
                    return;
                }

                final MineManager mineManager = Borne.getBorne().getMineManager();
                final Mine mine = mineManager.getMine(args.get(1).toUpperCase(Locale.ROOT));

                if (mine == null) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Шахта не найдена."));
                    return;
                }

                mineManager.unregisterMine(mine.getId());
                mine.destroy();

                final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("mines.yml");

                if (configuration != null) {
                    configuration.set("MINES." + mine.getId(), null);

                    final ConfigManager configManager = Borne.getBorne().getConfigManager();

                    configManager.saveFile("mines.yml", configuration);
                    configManager.reloadFile("mines.yml");
                }

                profile.sendText(Component.text(Component.Type.SUCCESS, "Шахта успешно удалена."));

                return;
            }
        }

        final BetterParser parser = BetterParser.parse(args);

        if (!parser.isExists("execute")) {
            profile.sendText(Component.text(Component.Type.ERROR, "Действие не найдено."));
            return;
        }

        final List<Mine> executeMines = new ArrayList<>();
        final String execute = parser.getValue("execute");

        if (!parser.isExists("id")) {
            executeMines.addAll(Borne.getBorne().getMineManager().getMines().values());
        } else {
            final String id = parser.getValue("id");
            final Mine mine = Borne.getBorne().getMineManager().getMine(id);

            if (mine == null) {
                profile.sendText(Component.text(Component.Type.ERROR, "Шахта не найдена."));
                return;
            }

            executeMines.add(mine);
        }

        if (executeMines.isEmpty()) {
            profile.sendText(Component.text(Component.Type.ERROR, "Ваша команда не затронула никаких шахт."));
            return;
        }

        switch (execute) {
            case "tick" -> executeMines.forEach(Mine::tick);
            case "regenerate" -> executeMines.forEach(Mine::regenerate);
            default -> profile.sendText(Component.text(Component.Type.ERROR, "Действие не найдено."));
        }

        profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно выполнили действие."));
    }

    @Override
    public List<String> onHint(@NotNull CommandSender sender, List<String> args) {
        final List<String> hints = new ArrayList<>();

        for (final Mine mine : Borne.getBorne().getMineManager().getMines().values())
            hints.add("id:" + mine.getId().toLowerCase());

        hints.add("execute:tick");
        hints.add("execute:regenerate");

        if (args.size() == 1)
            hints.addAll(List.of("create", "remove", "edit"));
        if (args.size() == 2)
            if (args.get(0).equals("remove") || args.get(0).equals("edit")) {
                hints.clear();
                for (final Mine mine : Borne.getBorne().getMineManager().getMines().values())
                    hints.add(mine.getId().toLowerCase(Locale.ROOT));
            }
            else if (args.get(0).equals("create")) {
                hints.clear();
                hints.add("cancel");
            }

        return hints;
    }

}

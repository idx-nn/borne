package nn.iamj.borne.basic.commands.admin.admin.control;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.command.intefaces.CommandCompleter;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.component.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@CommandMeta(rights = "command.gamemode")
public final class GamemodeCommand extends Command implements CommandCompleter {

    public GamemodeCommand() {
        super("gamemode", "The gamemode command.", "gm");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (args.isEmpty()) {
            profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/gamemode <режим>"));
            return;
        }

        if (args.size() != 1 && profile.isSystemProvider()) {
            profile.sendText(Component.text(Component.Type.ERROR, "Вы не можете использовать команду таким способом."));
            return;
        }

        switch (args.get(0)) {
            case "survival", "s", "0": {
                final Player player = (Player) sender;
                if (failedPermission(player, GameMode.SURVIVAL)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                    return;
                }

                final boolean result = this.changeMode(player, GameMode.SURVIVAL);

                if (result) {
                    profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили свой режим игры на &e" + GameMode.SURVIVAL));
                } else profile.sendText(Component.text(Component.Type.WARNING, "У Вас уже стоит этот режим игры, ничего не изменилось."));

                break;
            }
            case "creative", "c", "1": {
                final Player player = (Player) sender;
                if (failedPermission(player, GameMode.CREATIVE)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                    return;
                }

                final boolean result = this.changeMode(player, GameMode.CREATIVE);

                if (result) {
                    profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили свой режим игры на &e" + GameMode.CREATIVE));
                } else profile.sendText(Component.text(Component.Type.WARNING, "У Вас уже стоит этот режим игры, ничего не изменилось."));

                break;
            }
            case "adventure", "a", "2": {
                final Player player = (Player) sender;
                if (failedPermission(player, GameMode.ADVENTURE)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                    return;
                }

                final boolean result = this.changeMode(player, GameMode.ADVENTURE);

                if (result) {
                    profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили свой режим игры на &e" + GameMode.ADVENTURE));
                } else profile.sendText(Component.text(Component.Type.WARNING, "У Вас уже стоит этот режим игры, ничего не изменилось."));

                break;
            }
            case "spectator", "sp", "3": {
                final Player player = (Player) sender;
                if (failedPermission(player, GameMode.SPECTATOR)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                    return;
                }

                final boolean result = this.changeMode(player, GameMode.SPECTATOR);

                if (result) {
                    profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили свой режим игры на &e" + GameMode.SPECTATOR));
                } else profile.sendText(Component.text(Component.Type.WARNING, "У Вас уже стоит этот режим игры, ничего не изменилось."));

                break;
            }
            default: {
                if (!sender.hasPermission("command.gamemode.others")) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас не хватает полномочий изменять другим игрокам режим игры."));
                    return;
                }

                if (args.get(0).equals(sender.getName())) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Вы не можете изменить свой режим игры таким способом."));
                    return;
                }

                final Player player = Bukkit.getPlayerExact(args.get(0));

                if (player == null) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Игрок не найден."));
                    return;
                }

                if (args.size() != 2) {
                    profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/gamemode <никнейм> <режим>"));
                    return;
                }

                switch (args.get(1)) {
                    case "survival", "s", "0": {
                        if (failedPermission(sender, GameMode.SURVIVAL)) {
                            profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                            return;
                        }

                        final boolean result = this.changeMode(player, GameMode.SURVIVAL);

                        if (result) {
                            profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили режим игры игрока &e" + player.getName() + " &x&0&0&f&f&1&1на &e" + GameMode.SURVIVAL));
                        } else profile.sendText(Component.text(Component.Type.WARNING, "У игрока уже стоит этот режим игры, ничего не изменилось."));

                        break;
                    }
                    case "creative", "c", "1": {
                        if (failedPermission(sender, GameMode.CREATIVE)) {
                            profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                            return;
                        }

                        final boolean result = this.changeMode(player, GameMode.CREATIVE);

                        if (result) {
                            profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили режим игры игрока &e" + player.getName() + " &x&0&0&f&f&1&1на &e" + GameMode.CREATIVE));
                        } else profile.sendText(Component.text(Component.Type.WARNING, "У игрока уже стоит этот режим игры, ничего не изменилось."));

                        break;
                    }
                    case "adventure", "a", "2": {
                        if (failedPermission(sender, GameMode.ADVENTURE)) {
                            profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                            return;
                        }

                        final boolean result = this.changeMode(player, GameMode.ADVENTURE);

                        if (result) {
                            profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили режим игры игрока &e" + player.getName() + " &x&0&0&f&f&1&1на &e" + GameMode.ADVENTURE));
                        } else profile.sendText(Component.text(Component.Type.WARNING, "У игрока уже стоит этот режим игры, ничего не изменилось."));

                        break;
                    }
                    case "spectator", "sp", "3": {
                        if (failedPermission(sender, GameMode.SPECTATOR)) {
                            profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно полномочий для изменение режима."));
                            return;
                        }

                        final boolean result = this.changeMode(player, GameMode.SPECTATOR);

                        if (result) {
                            profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно изменили режим игры игрока &e" + player.getName() + " &x&0&0&f&f&1&1на &e" + GameMode.SPECTATOR));
                        } else profile.sendText(Component.text(Component.Type.WARNING, "У игрока уже стоит этот режим игры, ничего не изменилось."));

                        break;
                    }
                }

                break;
            }
        }
    }

    private boolean failedPermission(final CommandSender sender, final GameMode mode) {
        return !sender.hasPermission("command.gamemode.*") && !sender.hasPermission("command.gamemode." + mode.name().toLowerCase(Locale.ROOT));
    }

    private boolean changeMode(final Player player, final GameMode mode) {
        if (player.getGameMode() == mode)
            return false;

        player.setGameMode(mode);
        return true;
    }

    @Override
    public List<String> onHint(@NotNull CommandSender sender, List<String> args) {
        if (args.size() == 1) {
            if (!sender.hasPermission("command.gamemode"))
                return null;

            final List<String> list = new ArrayList<>();

            if (!failedPermission(sender, GameMode.SURVIVAL))
                list.addAll(List.of("survival", "s", "0"));
            if (!failedPermission(sender, GameMode.CREATIVE))
                list.addAll(List.of("creative", "c", "1"));
            if (!failedPermission(sender, GameMode.ADVENTURE))
                list.addAll(List.of("adventure", "a", "2"));
            if (!failedPermission(sender, GameMode.SPECTATOR))
                list.addAll(List.of("spectator", "sp", "3"));

            list.addAll(Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Player::getName)
                    .toList());

            return list;
        }

        if (args.size() == 2) {
            if (!sender.hasPermission("command.gamemode"))
                return null;

            final List<String> list = new ArrayList<>();

            if (!failedPermission(sender, GameMode.SURVIVAL))
                list.addAll(List.of("survival", "s", "0"));
            if (!failedPermission(sender, GameMode.CREATIVE))
                list.addAll(List.of("creative", "c", "1"));
            if (!failedPermission(sender, GameMode.ADVENTURE))
                list.addAll(List.of("adventure", "a", "2"));
            if (!failedPermission(sender, GameMode.SPECTATOR))
                list.addAll(List.of("spectator", "sp", "3"));

            return list;
        }

        return null;
    }

}

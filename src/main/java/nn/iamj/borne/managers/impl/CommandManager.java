package nn.iamj.borne.managers.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.Executable;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.logger.LoggerProvider;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class CommandManager implements Manager {

    private final Map<String, Command> labels;
    private final List<Executable> commands;

    public CommandManager() {
        this.labels = new ConcurrentHashMap<>();
        this.commands = new ArrayList<>();
    }

    public void registerCommand(final Command command) {
        this.labels.put(command.getLabel(), command);
        if (command.hasAliases())
            command.getAliases().forEach(alias -> this.labels.put(alias, command));
        this.commands.add(command);

        validateCommand(command);
    }

    public void unregisterCommand(final String name) {
        final Command command = this.labels.get(name);
        this.labels.remove(command.getLabel());

        command.getAliases().forEach(this.labels::remove);
        this.commands.remove(command);

        if (command.hasPermission()) {
            final String permission = command.getPermission();
            Borne.getBorne().getPlugin().getServer().getPluginManager().removePermission(permission);
        }
    }

    public Executable getCommand(final String name) { return this.labels.get(name); }

    public Collection<Executable> getCommands() { return this.commands; }

    @Override
    public void preload() {}

    @Override
    public void initialize() {}

    @Override
    public void shutdown() {
        this.commands.forEach(executable -> {
            if (executable.hasPermission())
                Borne.getBorne().getPlugin().getServer().getPluginManager().removePermission(executable.getPermission());
        });

        this.labels.clear();
        this.commands.clear();
    }

    private static void validateCommand(final Command command) {
        try {
            final Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            final CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.register(command.getLabel(), new BukkitCommand(command.getLabel(), command.getDescription(), "", command.getAliases()) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
                    if (sender instanceof ConsoleCommandSender && !command.canConsoleExecute()) {
                        return false;
                    }

                    if (command.hasPermission() && !command.checkPermissions(sender)) {
                        final FileConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

                        if (configuration == null) {
                            throw new IllegalStateException("Configuration cannot be null.");
                        }

                        final String message = configuration.getString("global.no-rights", "");
                        if (message.isEmpty()) return false;

                        sender.sendMessage(new Text(message).getRaw());
                        return false;
                    }

                    command.execute(sender, command, Arrays.asList(args));
                    return false;
                }
            }.setAliases(command.getAliases()));

            if (command.hasAliases()) {
                for (final String aliases : command.getAliases()) {
                    commandMap.register(aliases, new BukkitCommand(aliases, command.getDescription(), "", Collections.emptyList()) {
                        @Override
                        public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
                            if (sender instanceof ConsoleCommandSender && !command.canConsoleExecute()) {
                                return false;
                            }

                            if (command.hasPermission() && !sender.hasPermission(command.getPermission())) {
                                final FileConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

                                if (configuration == null) {
                                    throw new IllegalStateException("Configuration cannot be null.");
                                }

                                final String message = configuration.getString("global.no-rights", "");
                                if (message.isEmpty()) return false;

                                sender.sendMessage(new Text(message).getRaw());
                                return false;
                            }

                            command.execute(sender, command, Arrays.asList(args));
                            return false;
                        }
                    }.setAliases(Collections.emptyList()));
                }
            }

            if (command.hasPermission()) {
                final Permission permission = new Permission(command.getPermission(), PermissionDefault.OP);
                Borne.getBorne().getPlugin().getServer().getPluginManager().addPermission(permission);
            }
        } catch (Exception exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
        }
    }

}

package nn.iamj.borne.modules.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.command.annotations.CommandMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Command implements Executable {

    private final String label;
    private final String description;
    private final String permission;

    private final List<String> aliases;
    private final boolean canConsoleExecute;

    @Deprecated
    public Command(final String label, final String description) {
        this.label = label;
        this.description = description;
        this.aliases = Collections.emptyList();

        this.permission = (this.getClass().isAnnotationPresent(CommandMeta.class) ? this.getClass().getAnnotation(CommandMeta.class).rights() : "");
        this.canConsoleExecute = (!this.getClass().isAnnotationPresent(CommandMeta.class) || this.getClass().getAnnotation(CommandMeta.class).consoleExecute());
    }

    public Command(final String label, final String description, final List<String> aliases) {
        this.label = label;
        this.description = description;
        this.aliases = aliases;

        this.permission = (this.getClass().isAnnotationPresent(CommandMeta.class) ? this.getClass().getAnnotation(CommandMeta.class).rights() : "");
        this.canConsoleExecute = (!this.getClass().isAnnotationPresent(CommandMeta.class) || this.getClass().getAnnotation(CommandMeta.class).consoleExecute());
    }

    public Command(final String label, final String description, final String... aliases) {
        this.label = label;
        this.description = description;
        this.aliases = Arrays.asList(aliases);

        this.permission = (this.getClass().isAnnotationPresent(CommandMeta.class) ? this.getClass().getAnnotation(CommandMeta.class).rights() : "");
        this.canConsoleExecute = (!this.getClass().isAnnotationPresent(CommandMeta.class) || this.getClass().getAnnotation(CommandMeta.class).consoleExecute());
    }

    @Override
    public String getLabel() {
        return this.label;
    }
    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }
    @Override
    public boolean hasAliases() {
        return !this.aliases.isEmpty();
    }

    @Override
    public String getPermission() {
        return permission;
    }
    @Override
    public boolean hasPermission() {
        return !permission.isEmpty();
    }

    @Override
    public boolean checkPermissions(final CommandSender sender) {
        if (!this.hasPermission()) return true;

        return sender.hasPermission(this.getPermission());
    }
    @Override
    public boolean canConsoleExecute() {
        return this.canConsoleExecute;
    }

    @Override
    public abstract void execute(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final List<String> args);

}
package nn.iamj.borne.modules.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface Executable {

    String getLabel();
    String getDescription();

    List<String> getAliases();
    boolean hasAliases();

    String getPermission();
    boolean hasPermission();

    boolean checkPermissions(final CommandSender sender);
    boolean canConsoleExecute();

    void execute(final CommandSender sender, final Command command, final List<String> args);

}

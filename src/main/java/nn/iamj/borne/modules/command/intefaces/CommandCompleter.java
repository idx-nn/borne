package nn.iamj.borne.modules.command.intefaces;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandCompleter {

    List<String> onHint(@NotNull final CommandSender sender, final List<String> args);

}


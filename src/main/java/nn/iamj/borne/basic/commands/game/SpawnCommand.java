package nn.iamj.borne.basic.commands.game;

import nn.iamj.borne.basic.gameplay.listeners.BasicListener;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandMeta(consoleExecute = false)
public final class SpawnCommand extends Command {

    public SpawnCommand() {
        super("spawn", "The spawn command.", "spwn");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Player player = (Player) sender;

        player.teleportAsync(BasicListener.getSpawnLocation());
    }

}

package nn.iamj.borne.basic.commands.game;

import nn.iamj.borne.basic.menus.mines.MinesMenu;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandMeta(consoleExecute = false)
public class MinesCommand extends Command {

    public MinesCommand() {
        super("mines", "The mines command.", "mn");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        Scheduler.asyncHandle(() -> {
           final MinesMenu menu = new MinesMenu(Profile.asSender(sender));

            menu.open();
        });
    }
}

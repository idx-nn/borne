package nn.iamj.borne.basic.commands.game.boosters;

import nn.iamj.borne.basic.menus.boosters.BoostersMenu;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.profile.Profile;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandMeta(consoleExecute = false)
public final class BoostersCommand extends Command {

    public BoostersCommand() {
        super("boosters", "The boosters command.", "boosts", "bts");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (profile == null) return;

        new BoostersMenu(profile).open();
    }

}

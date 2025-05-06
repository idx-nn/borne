package nn.iamj.borne.basic.commands.game.talents;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.basic.menus.talents.TalentsMenu;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.profile.Profile;

import java.util.List;

@CommandMeta(consoleExecute = false)
public final class TalentsCommand extends Command {

    public TalentsCommand() {
        super("talent", "The talent command.", "talents");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (profile == null || profile.isSystemProvider())
            return;

        (new TalentsMenu(profile)).openMenu();

        final Player player = (Player) sender;
        player.playSound(player.getLocation(),
                Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
    }

}

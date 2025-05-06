package nn.iamj.borne.basic.commands.admin.admin.control.mine;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.basic.menus.admin.control.PriceSettingsMenu;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;

import java.util.List;

@CommandMeta(rights = "command.eprice", consoleExecute = false)
public final class EPriceCommand extends Command {

    public EPriceCommand() {
        super("eprice", "The eprice command.", "editprice");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Player player = (Player) sender;

        (new PriceSettingsMenu(player)).openMenu(1, false);
        player.playSound(player.getLocation(),
                Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
    }

}

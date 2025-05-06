package nn.iamj.borne.basic.commands.admin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.command.intefaces.CommandCompleter;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.addons.messenger.Messenger;
import nn.iamj.borne.modules.util.arrays.BetterArrays;
import nn.iamj.borne.modules.util.arrays.BetterParser;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.plugin.helper.BorneHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@CommandMeta(rights = "command.borne")
public final class BorneCommand extends Command implements CommandCompleter {

    public BorneCommand() {
        super("borne", "The main borne command.", "brn");
    }

    @Override @SuppressWarnings("all")
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (args.isEmpty()) {
            profile.sendText(new Text(""));
            profile.sendText(new Text(" &x&0&0&f&f&1&1Borne plugin. inc."));
            profile.sendText(new Text(""));
            profile.sendText(new Text("      &7Args:  "));
            profile.sendText(new Text(" &x&0&0&f&f&1&1reload &f- Reload Borne.  "));
            profile.sendText(new Text(""));
            return;
        }

        switch (args.get(0)) {
            case "reload": {
                final BetterParser parser = BetterParser.parse(BetterArrays.copyOfRange(args, 1, args.size()));

                final boolean result = BorneHelper.sendReloadRequest(parser.isFlagSpecified("f") || parser.isFlagSpecified("force"));

                if (result) {
                    profile.sendText(Component.text(Component.Type.SUCCESS, "Плагин успешно был перезагружен!"));
                } else profile.sendText(Component.text(Component.Type.ERROR, "Плагин не принял запрос для перезапуска."));

                break;
            }
        }
    }

    @Override
    public List<String> onHint(@NotNull CommandSender sender, List<String> args) {
        if (args.isEmpty()) return null;

        if (args.size() == 1)
            return List.of("reload", "-f", "-force");

        return List.of("-f", "-force");
    }

}

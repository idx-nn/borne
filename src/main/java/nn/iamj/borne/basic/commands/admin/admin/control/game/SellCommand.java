package nn.iamj.borne.basic.commands.admin.admin.control.game;

import nn.iamj.borne.basic.menus.sell.SellMenu;
import nn.iamj.borne.basic.prompts.SellPrompt;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.arrays.BetterParser;
import nn.iamj.borne.modules.util.component.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SellCommand extends Command {

    public SellCommand() {
        super("sell", "The sell command.", "fsell");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (profile == null) return;

        final BetterParser parser = BetterParser.parse(args);

        if (!sender.hasPermission("command.sell") || parser.isFlagSpecified("p")) {
            SellMenu.open(profile);
            return;
        }

        if (args.isEmpty()) {
            if (profile.isSystemProvider()) {
                profile.sendText(Component.text(Component.Type.ERROR, "Ваш запрос не был обработан."));
                return;
            }

            SellPrompt.handle(profile);

            profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно вызвали принудительную продажу предметов у себя."));
            return;
        }

        final Profile target = Profile.asName(args.get(0));

        if (target == null || !target.isOnline()) {
            profile.sendText(Component.text(Component.Type.ERROR, "Игрок, которого Вы указали, сейчас оффлайн."));
            return;
        }

        SellPrompt.handle(target);

        profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно вызвали принудительную продажу предметов у &e" + target.getName()));
    }

}

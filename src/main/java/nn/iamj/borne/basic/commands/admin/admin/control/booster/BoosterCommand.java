package nn.iamj.borne.basic.commands.admin.admin.control.booster;

import nn.iamj.borne.modules.booster.Booster;
import nn.iamj.borne.modules.booster.BoosterStorage;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.command.intefaces.CommandCompleter;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.BetterNumbers;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@CommandMeta(rights = "command.booster")
public final class BoosterCommand extends Command implements CommandCompleter {

    public BoosterCommand() {
        super("booster", "The booster command.", "boost", "bst");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (profile == null) return;

        if (args.isEmpty()) {
            profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/booster [add/active/reset] {target} {modifier} {seconds}"));
            return;
        }

        final BoosterStorage storage = BoosterStorage.getInstance();

        if (storage == null) return;

        switch (args.get(0)) {
            case "add": {
                if (args.size() < 4) {
                    profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/booster add [target] [modifier] [seconds]"));
                    return;
                }

                final String author = args.get(1);
                final String sm = args.get(2);
                final String ss = args.get(3);

                if (!BetterNumbers.isDouble(sm)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Вы должны указать вещественное число."));
                    return;
                }

                if (!BetterNumbers.isInteger(ss)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Вы должны указать число."));
                    return;
                }

                final Booster booster = new Booster(Double.parseDouble(sm), Integer.parseInt(ss));

                final Profile target = Profile.asName(author);

                if (target == null) return;

                target.getStorage().addBooster(booster);
                target.save(target.isOnline());

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно выдали &eбустер [x" + sm + "]" + Component.GREEN + " игроку &e" + target.getName()));

                break;
            }
            case "active": {
                if (args.size() < 4) {
                    profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/booster active [author] [modifier] [seconds]"));
                    return;
                }

                final String author = args.get(1);
                final String sm = args.get(2);
                final String ss = args.get(3);

                if (!BetterNumbers.isDouble(sm)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Вы должны указать вещественное число."));
                    return;
                }

                if (!BetterNumbers.isInteger(ss)) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Вы должны указать число."));
                    return;
                }

                final Booster booster = new Booster(Double.parseDouble(sm), Integer.parseInt(ss));

                storage.activeBooster(author, booster);

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно отправили запрос на активацию цифрового бустера."));

                break;
            }
            case "reset": {
                storage.resetBooster();

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно отправили запрос на снос активного бустера."));

                break;
            }
            default: {
                profile.sendText(Component.text(Component.Type.ERROR, "Действие &e" + args.get(0) + Component.RED + " не найдено."));
            }
        }
    }

    @Override
    public List<String> onHint(@NotNull CommandSender sender, List<String> args) {
        if (args.size() == 1)
            return List.of("add", "active", "reset");

        if (args.size() == 2 && (args.get(0).equals("active") || args.get(0).equals("add")))
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();

        if (args.size() == 3 && (args.get(0).equals("active") || args.get(0).equals("add"))) {
            final List<String> doubles = new ArrayList<>();

            for (double d = 1.0D; d < 4.0D; d += 0.1)
                doubles.add(BigDecimal.valueOf(d)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue() + "");

            return doubles;
        }

        if (args.size() == 4 && (args.get(0).equals("active") || args.get(0).equals("add"))) {
            final List<String> ints = new ArrayList<>();

            for (int i = 0; i < 1200; i += 120)
                ints.add(i + "");

            return ints;
        }

        return List.of();
    }
}

package nn.iamj.borne.basic.commands.game.eco;

import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.command.intefaces.CommandCompleter;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.BetterNumbers;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@CommandMeta(consoleExecute = false)
public final class PayCommand extends Command implements CommandCompleter {

    public PayCommand() {
        super("pay", "The pay command.", "py", "transfer");
    }

    @Override
    public void execute(CommandSender sender, Command command, List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (profile == null) return;

        if (args.size() != 2) {
            profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/pay <получатель> <сумма>"));
            return;
        }

        if (args.get(0).equals(profile.getName())) {
            profile.sendText(Component.text(Component.Type.ERROR, "Вы не можете переводить деньги себе же!"));
            return;
        }

        final Profile target = Profile.asName(args.get(0));
        final String rsumm = args.get(1);

        if (target == null || !target.isOnline()) {
            profile.sendText(Component.text(Component.Type.ERROR, "Игрок не найден!"));
            return;
        }

        if (!BetterNumbers.isDouble(rsumm)) {
            profile.sendText(Component.text(Component.Type.ERROR, "Вы должны указать число!"));
            return;
        }

        final double sum = Double.parseDouble(rsumm);

        if (profile.getWallet().getMoney() < sum) {
            profile.sendText(Component.text(Component.Type.ERROR, "У Вас на счету недостаточно средств, для такого перевода!"));
            return;
        }

        if (sum < 20) {
            profile.sendText(Component.text(Component.Type.ERROR, "Количество монет для операции должно быть не меньше 20!"));
            return;
        }

        if (sum > 5000) {
            profile.sendText(Component.text(Component.Type.ERROR, "Количество монет для операции должно быть не больше 5000!"));
            return;
        }

        final double apply = sum * 0.8D;

        profile.getWallet().removeMoney(sum);
        target.getWallet().addMoney(apply);

        target.save(true);
        profile.save(true);

        profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно отправили игроку &e" + target.getName() + Component.GREEN + " " + BetterNumbers.floor(3, apply) + " &f혤"));
        target.sendText(Component.text(Component.Type.INFO, "Вы получили &e" + BetterNumbers.floor(3, apply) + " &f혤 от игрока &e" + profile.getName()));
    }

    @Override
    public List<String> onHint(@NotNull CommandSender sender, List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (args.size() == 1) {
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).toList();
        }

        if (args.size() == 2 && profile != null) {
            final List<String> ints = new ArrayList<>();

            for (int i = 20; i <= 5000; i += 20) {
                if (profile.getWallet().getMoney() >= i)
                    ints.add(i + "");
            }

            return ints;
        }

        return List.of();
    }
}

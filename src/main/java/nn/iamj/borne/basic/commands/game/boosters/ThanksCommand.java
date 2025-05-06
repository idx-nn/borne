package nn.iamj.borne.basic.commands.game.boosters;

import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.providers.HudProvider;
import nn.iamj.borne.modules.booster.Booster;
import nn.iamj.borne.modules.booster.BoosterStorage;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.collection.ExpiringSet;
import nn.iamj.borne.modules.util.collection.pair.Pair;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.BetterNumbers;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@CommandMeta(consoleExecute = false)
public final class ThanksCommand extends Command {

    private static final ExpiringSet<String> cooldown = new ExpiringSet<>(42400000);

    public static void clearCooldown() {
        cooldown.clear();
    }

    public ThanksCommand() {
        super("thanks", "The thanks command.", "thx");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (profile == null) return;

        if (cooldown.contains(profile.getName())) {
            profile.sendText(Component.text(Component.Type.WARNING, "У Вас задержка на использование благодарностей."));
            return;
        }

        final BoosterStorage storage = BoosterStorage.getInstance();

        if (storage == null) return;

        final Pair<String, Booster> pair = storage.getActiveBooster();

        if (pair == null) {
            profile.sendText(Component.text(Component.Type.ERROR, "Сейчас нету активных бустеров."));
            return;
        }

        if (pair.getKey().equals(profile.getName())) {
            cooldown.add(profile.getName());

            if (!profile.getStatistic().isSelfThanks()) {
                profile.getStatistic().setSelfThanks(true);

                profile.sendText(Component.text(Component.Type.INFO, "Вы отблагодарили самого себя :) &x&e&7&9&2&f&0(+20 혣)"));
                profile.getWallet().addDonated(20);

                profile.save(true);

                return;
            }

            profile.sendText(Component.text(Component.Type.INFO, "Вы отблагодарили самого себя :)"));
            return;
        }

        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("config.yml");

        if (configuration == null) return;

        final Profile author = Profile.asName(pair.getKey());

        if (author == null) {
            profile.sendText(Component.text(Component.Type.ERROR, "Бустер запущен системой, Вы не можете отблагодарить это."));
            return;
        }

        final double modifier = configuration.getDouble("THANKS-MODIFIER", 0.006);
        final double balance = profile.getWallet().getMoney() * (modifier * pair.getValue().getModifier());

        if (balance < 0.05) {
            profile.sendText(Component.text(Component.Type.ERROR, "У Вас очень маленький баланс, для того чтобы отблагодарить бустера :("));
            return;
        }

        author.getWallet().addMoney(balance);
        profile.getWallet().removeMoney(balance * 1.02);

        profile.getStatistic().addFee((balance * 1.02) - balance);

        if (author.isOnline()) {
            author.sendText(Component.text(Component.Type.INFO, "&fИгрок &e" + sender.getName() + " &fотблагодарил Вас. Вы получили &e" + BetterNumbers.floor(4, balance) + " &f혤"));

            HudProvider.updateHud(author);
            author.save(true);
        }
        else author.save(false);

        profile.sendText(Component.text(Component.Type.INFO, "&fВы отблагодарили &e" + author.getName() + " &fи отправили ему &e" + BetterNumbers.floor(4, balance * 1.02) + " &f혤"));
        profile.save(true);

        cooldown.add(profile.getName());
    }

}

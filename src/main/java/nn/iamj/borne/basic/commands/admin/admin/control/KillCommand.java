package nn.iamj.borne.basic.commands.admin.admin.control;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.annotations.CommandMeta;
import nn.iamj.borne.modules.command.intefaces.CommandCompleter;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.arrays.BetterParser;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.entity.MobUtils;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@CommandMeta(rights = "command.kill", consoleExecute = false)
public final class KillCommand extends Command implements CommandCompleter {

    public KillCommand() {
        super("kill", "The kill command.", "die");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull List<String> args) {
        final Profile profile = Profile.asSender(sender);

        if (args.isEmpty()) {
            profile.sendText(Component.text(Component.Type.INFO, "Использование: &e/kill <параметры>"));
            return;
        }

        final BetterParser parser = BetterParser.parse(args);

        if (!parser.isExists("player") && !parser.isExists("radius") && !parser.isExists("type")) {
            profile.sendText(Component.text(Component.Type.ERROR, "Игрок не найден."));
            return;
        }

        final String playerOption = parser.getValue("player");
        final String radiusOption = parser.getValue("radius");
        final String typeOption = parser.getValue("type");

        final Player player = profile.asBukkit();

        if (player == null) return;

        final List<LivingEntity> entityList = new ArrayList<>();

        if (radiusOption != null) {
            if (!BetterNumbers.isInteger(radiusOption)) {
                profile.sendText(Component.text(Component.Type.ERROR, "В параметр радуса нужно указать число."));
                return;
            }

            final int radius = Integer.parseInt(radiusOption);

            if (radius > 1.0E6 || radius < 0) {
                profile.sendText(Component.text(Component.Type.ERROR, "В параметр радуса нужно указать число."));
                return;
            }

            entityList.addAll(player.getWorld()
                    .getNearbyEntities(player.getLocation(), radius, radius, radius)
                    .stream()
                    .filter(entity -> entity instanceof Monster
                            || entity instanceof Animals
                            || MobUtils.isBoss(entity.getType())
                            || entity.getType() == EntityType.PLAYER)
                    .filter(entity -> !entity.getName().equals(profile.getName()))
                    .map(entity -> (LivingEntity) entity)
                    .toList());
        } else if (playerOption == null) {
            entityList.addAll(player.getWorld()
                    .getEntities()
                    .stream()
                    .filter(entity -> entity instanceof Monster
                            || entity instanceof Animals
                            || MobUtils.isBoss(entity.getType())
                            || entity.getType() == EntityType.PLAYER)
                    .filter(entity -> !entity.getName().equals(profile.getName()))
                    .map(entity -> (LivingEntity) entity)
                    .toList());
        }

        if (playerOption != null) {
            final Player target = Bukkit.getPlayerExact(playerOption);

            if (target == null) {
                profile.sendText(Component.text(Component.Type.ERROR, "Игрок не найден."));
                return;
            }

            entityList.add(target);

            entityList.removeIf(entity ->
                    !entity.getName().equals(target.getName()));
        }

        if (typeOption != null) {
            try {
                 entityList.removeIf(entity ->
                         !entity.getType().equals(EntityType.valueOf(typeOption.toUpperCase(Locale.ROOT))));
            } catch (Exception exception) {
                profile.sendText(Component.text(Component.Type.ERROR, "Вы указали несуществующий тип сущности."));
                return;
            }
        }

        if (entityList.isEmpty()) {
            profile.sendText(Component.text(Component.Type.ERROR, "Ваши настройки не затронули существ, ничего не изменилось."));
            return;
        }

        entityList.forEach(entity -> entity.setHealth(0.0D));

        profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно уничтожили &e" + entityList.size() + Component.GREEN + " сущностей >:)"));

        entityList.clear();
    }

    @Override
    public List<String> onHint(@NotNull CommandSender sender, List<String> args) {
        final List<String> hints = new ArrayList<>();

        for (final EntityType type : MobUtils.getMonsters())
            hints.add("type:" + type.name().toLowerCase(Locale.ROOT));
        for (final EntityType type : MobUtils.getAnimals())
            hints.add("type:" + type.name().toLowerCase(Locale.ROOT));
        for (final EntityType type : MobUtils.getBosses())
            hints.add("type:" + type.name().toLowerCase(Locale.ROOT));
        hints.add("type:player");

        for (int i = 5; i < 100; i += 5)
            hints.add("radius:" + i);

        hints.addAll(Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList());

        return hints;
    }

}

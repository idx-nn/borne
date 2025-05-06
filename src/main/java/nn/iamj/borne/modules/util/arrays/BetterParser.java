package nn.iamj.borne.modules.util.arrays;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import nn.iamj.borne.modules.util.arrays.concurrent.ConcurrentSet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class BetterParser {

    private final Set<String> flags = new ConcurrentSet<>();
    private final Map<String, String> keys = new ConcurrentHashMap<>();

    public static BetterParser parse(final List<String> args) {
        final BetterParser parser = new BetterParser();

        args.forEach(arg -> {
            if (arg.startsWith("-") && arg.length() > 1) {
                parser.flags.add(arg.substring(1));

                return;
            }

            if (arg.contains(":")) {
                final String[] pars = arg.split(":");

                if (pars.length != 2) return;

                final String key = pars[0];
                final String value = pars[1];

                parser.keys.put(key, value);

                return;
            }

            final List<String> nicknames = Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Player::getName)
                    .toList();

            if (nicknames.contains(arg))
                parser.keys.put("player", arg);
        });

        return parser;
    }

    private static BetterParser parse(final String[] args) {
        return parse(Arrays.asList(args));
    }

    @SuppressWarnings("all")
    public boolean isExists(final String key) {
        return this.keys.containsKey(key);
    }

    public String getValue(final String key) {
        return this.keys.get(key);
    }

    public boolean isFlagSpecified(final String flag) {
        return this.flags.contains(flag);
    }

}

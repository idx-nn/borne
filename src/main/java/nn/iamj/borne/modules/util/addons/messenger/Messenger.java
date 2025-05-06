package nn.iamj.borne.modules.util.addons.messenger;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.server.scheduler.Scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Messenger {

    private static final Map<String, BukkitTask> cache = new ConcurrentHashMap<>();

    private Messenger() {}

    @SuppressWarnings("all")
    public static void sendMessage(final Player player, String message) {
        if (player == null) return;

        final BukkitTask oldTask = cache.get(player.getName());

        if (oldTask != null)
            oldTask.cancel();

        final String string = ChatColor.translateAlternateColorCodes('&', message);

        final BukkitTask task = Scheduler.asyncHandleRate(new BukkitRunnable() {
            int index = 0;
            @Override
            public void run() {
                final String message = Text.strip(string);

                final TextComponent component = new TextComponent(
                        " ".repeat(string.length() - index) + string.substring(0, index));

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);

                if (index < string.length())
                    index++;
                else cancel();
            }
        }, 0L, 1L);

        cache.put(player.getName(), task);
    }

}

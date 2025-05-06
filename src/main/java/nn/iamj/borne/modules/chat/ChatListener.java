package nn.iamj.borne.modules.chat;

import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.addons.luckperms.LuckPermsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(final AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;

        final Player player = event.getPlayer();
        final String message = event.getMessage();

        event.setCancelled(true);

        final String finalMessage = ChatColor.stripColor(new Text(message).getRaw());
        final String prefix = LuckPermsAPI.getUser(player.getName()).getCachedData().getMetaData().getPrefix();

        if (finalMessage.startsWith("!")) {
            final String originalMessage = finalMessage.substring(1);

            for (final Player viewer : Bukkit.getOnlinePlayers())
                viewer.sendMessage(new Text("&x&5&5&c&7&b&dⒼ &8:: &7" + prefix + player.getName() + "&8 » &f" + originalMessage).getRaw());
        } else {
            for (final Player viewer : Bukkit.getOnlinePlayers().stream().filter(o -> {
                return o.getLocation().distance(player.getLocation()) <= 100;
            }).toList())
                viewer.sendMessage(new Text("&x&e&2&7&a&e&dⓁ &8:: &7" + prefix + player.getName() + "&8 » &f" + finalMessage).getRaw());
        }
    }

}

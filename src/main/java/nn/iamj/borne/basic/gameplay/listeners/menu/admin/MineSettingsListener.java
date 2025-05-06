package nn.iamj.borne.basic.gameplay.listeners.menu.admin;

import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.menus.admin.control.MineSettingsMenu;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.collection.ExpiringMap;
import nn.iamj.borne.modules.util.component.Component;

import java.util.Map;

public final class MineSettingsListener implements Listener {

    @Getter
    private static final ExpiringMap<String, Mine> changeLabelCache = new ExpiringMap<>(180000);

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        if (!changeLabelCache.containsKey(player.getName()))
            return;

        event.setCancelled(true);

        final Profile profile = Profile.asName(player.getName());

        if (profile == null)
            return;

        final Mine mine = changeLabelCache.get(player.getName());

        if (mine == null) return;

        final Mine originalMine = Borne.getBorne().getMineManager().getMine(mine.getId());

        if (originalMine == null) {
            profile.sendText(Component.text(Component.Type.ERROR, "Шахта, которую Вы пытались изменить, уже удалена."));
            return;
        }

        originalMine.setLabel(event.getMessage());

        Scheduler.handleRate(() -> {
            (new MineSettingsMenu(event.getPlayer(), originalMine)).openMenu();
            player.playSound(player.getLocation(),
                    Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0F, 1.0F);
        }, 10L);
    }

}

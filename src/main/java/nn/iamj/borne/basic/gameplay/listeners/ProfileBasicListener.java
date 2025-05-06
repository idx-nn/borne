package nn.iamj.borne.basic.gameplay.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import nn.iamj.borne.modules.api.events.profile.ProfileLoadEvent;
import nn.iamj.borne.modules.api.events.profile.ProfileSaveEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.addons.messenger.Messenger;

public final class ProfileBasicListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void onLoad(final ProfileLoadEvent event) {
        if (event.isCancelled()) return;

        final Profile profile = event.getProfile();
        final Player player = profile.asBukkit();

        if (player == null)
            return;

        player.setMaxHealth(19 + profile.getLevel().getLevel());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSave(final ProfileSaveEvent event) {
        if (event.isCancelled() || event.isOnlyCache()) return;

        final Profile profile = event.getProfile();

        if (!profile.isOnline()) return;

        Messenger.sendMessage(profile.asBukkit(), "§eСохраняем профиль..");
        Scheduler.asyncHandleRate(() -> Messenger.sendMessage(profile.asBukkit(),
                "§aПрофиль сохранен.."), 40L);
    }

}

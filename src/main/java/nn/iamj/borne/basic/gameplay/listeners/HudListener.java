package nn.iamj.borne.basic.gameplay.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import nn.iamj.borne.modules.api.events.profile.hud.ProfileHudUpdateEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.collection.ExpiringSet;

public final class HudListener implements Listener {

    private final ExpiringSet<String> cache = new ExpiringSet<>(500);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUpdate(final ProfileHudUpdateEvent event) {
        if (event.isCancelled()) return;

        final Profile profile = event.getProfile();

        if (cache.contains(profile.getName()))
            event.setCancelled(true);
        else cache.add(profile.getName());
    }

}

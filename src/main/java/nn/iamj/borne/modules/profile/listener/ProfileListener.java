package nn.iamj.borne.modules.profile.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.impl.ProfileManager;
import nn.iamj.borne.modules.api.events.profile.ProfileLoadEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.event.EventUtils;

public final class ProfileListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(final AsyncPlayerPreLoginEvent event) {
        final String nickname = event.getName();

        if (nickname.equals("System") || nickname.length() < 3 || nickname.length() > 32)
            event.setKickMessage("Произошла ошибка в создании профиля, напишите администрации.");
    }

    @SuppressWarnings("all")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {
        event.setJoinMessage(null);

        final Player player = event.getPlayer();

        final ProfileManager manager = Borne.getBorne().getProfileManager();

        final Profile profile = manager.isProfileExists(player.getName()) ?
                manager.getProfile(player.getName()) :
                manager.createProfile(player);

        if (profile == null) {
            player.kickPlayer("Произошла ошибка в создании профиля, напишите администрации.");
            return;
        }

        EventUtils.callEvent(new ProfileLoadEvent(profile, player, event.getPlayer().hasPlayedBefore()));
    }

    @SuppressWarnings("all")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDeath(final PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @SuppressWarnings("all")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDisconnect(final PlayerQuitEvent event) {
        event.setQuitMessage(null);

        final Player player = event.getPlayer();

        final ProfileManager manager = Borne.getBorne().getProfileManager();

        final Profile profile = manager.getProfile(player.getName());

        if (profile == null) return;

        profile.save();
    }

}

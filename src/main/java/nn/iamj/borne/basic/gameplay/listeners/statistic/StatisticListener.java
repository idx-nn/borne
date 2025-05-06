package nn.iamj.borne.basic.gameplay.listeners.statistic;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import nn.iamj.borne.modules.api.events.profile.action.ProfileBlockBreakEvent;
import nn.iamj.borne.modules.profile.Profile;

public final class StatisticListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onKill(final EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        final Entity killer = entity.getKiller();

        if (killer == null) return;

        Profile profile = Profile.asEntity(killer);
        if (killer instanceof Projectile projectile
                && projectile.getShooter() instanceof Player player) {
            profile = Profile.asName(player.getName());
        }

        if (profile == null)
            return;

        if (entity instanceof Player) profile.getStatistic().addKills(1);
        else if (entity instanceof Mob) profile.getStatistic().addMobKills(1);

        profile.save(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(final EntityDeathEvent event) {
        final Profile profile = Profile.asEntity(event.getEntity());

        if (profile == null) return;

        profile.getStatistic().addDies(1);

        profile.save(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(final ProfileBlockBreakEvent event) {
        final Profile profile = event.getProfile();

        profile.getStatistic().addBlockBreaks(1);

        profile.save(true);
    }

}

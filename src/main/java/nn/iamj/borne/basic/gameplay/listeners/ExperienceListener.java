package nn.iamj.borne.basic.gameplay.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import nn.iamj.borne.basic.providers.HudProvider;
import nn.iamj.borne.modules.api.events.profile.level.ProfileExperienceEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.addons.messenger.Messenger;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.concurrent.ThreadLocalRandom;

public final class ExperienceListener implements Listener {

    @EventHandler
    public void onKill(final EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();

        if (entity.getKiller() == null) return;

        final LivingEntity killer = entity.getKiller();

        if (!(killer instanceof Player player)) return;

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final Profile profile = Profile.asName(player.getName());

        final double experience = random.nextDouble(0.12, 0.36);

        profile.getLevel().addExperience(experience);
        profile.getWallet().addMoney(random.nextDouble(0.01, 0.04));

        HudProvider.updateHud(profile);

        profile.save(true);
    }

}

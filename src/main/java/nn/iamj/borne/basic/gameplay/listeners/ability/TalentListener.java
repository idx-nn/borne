package nn.iamj.borne.basic.gameplay.listeners.ability;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.talent.Talent;
import nn.iamj.borne.modules.talent.TalentType;
import nn.iamj.borne.modules.util.addons.messenger.Messenger;

public final class TalentListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHealth(final EntityRegainHealthEvent event) {
        if (event.isCancelled()) return;

        final Profile profile = Profile.asEntity(event.getEntity());

        if (profile == null) return;

        final Talent talent = Borne.getBorne().getTalentManager().getTalent(TalentType.REGEN);
        final int poisonModifier = profile.getStorage().getTalent(TalentType.REGEN);

        if (talent == null || poisonModifier == 0) return;

        final Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity living)) return;

        talent.handle(living, poisonModifier);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPoisonTick(final EntityDamageByEntityEvent event) {
        if (event.isCancelled() || event.getDamage() == 0.0D) return;

        final Profile profile = Profile.asEntity(event.getDamager());

        if (profile == null) return;

        final Talent talent = Borne.getBorne().getTalentManager().getTalent(TalentType.POISON);
        final int poisonModifier = profile.getStorage().getTalent(TalentType.POISON);

        if (talent == null || poisonModifier == 0) return;

        final Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity living)) return;

        final boolean result = talent.handle(living, poisonModifier);

        if (result) Messenger.sendMessage(profile.asBukkit(), "&7Способность &e\"Ядовитые руки\" &7активирована!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFireTick(final EntityDamageByEntityEvent event) {
        if (event.isCancelled() || event.getDamage() == 0.0D) return;

        final Profile profile = Profile.asEntity(event.getDamager());

        if (profile == null) return;

        final Talent talent = Borne.getBorne().getTalentManager().getTalent(TalentType.FIRE);
        final int poisonModifier = profile.getStorage().getTalent(TalentType.FIRE);

        if (talent == null || poisonModifier == 0) return;

        final Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity living)) return;

        final boolean result = talent.handle(living, poisonModifier);

        if (result) Messenger.sendMessage(profile.asBukkit(), "&7Способность &e\"Поджарка\" &7активирована!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onShinobiTick(final EntityDamageByEntityEvent event) {
        if (event.isCancelled() || event.getDamage() == 0.0D) return;

        final Profile profile = Profile.asEntity(event.getDamager());

        if (profile == null) return;

        final Talent talent = Borne.getBorne().getTalentManager().getTalent(TalentType.SHINOBI);
        final int poisonModifier = profile.getStorage().getTalent(TalentType.SHINOBI);

        if (talent == null || poisonModifier == 0) return;

        final Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity living)) return;

        final boolean result = talent.handle(living, poisonModifier);

        if (result) Messenger.sendMessage(profile.asBukkit(), "&7Способность &e\"Головокружение\" &7активирована!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVampireTick(final EntityDamageByEntityEvent event) {
        if (event.isCancelled() || event.getDamage() == 0.0D) return;

        final Profile profile = Profile.asEntity(event.getDamager());

        if (profile == null) return;

        final Talent talent = Borne.getBorne().getTalentManager().getTalent(TalentType.VAMPIRE);
        final int poisonModifier = profile.getStorage().getTalent(TalentType.VAMPIRE);

        if (talent == null || poisonModifier == 0) return;

        final Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity living)) return;

        final boolean result = talent.handle(living, poisonModifier);

        if (result) Messenger.sendMessage(profile.asBukkit(), "&7Способность &e\"Вампир\" &7активирована!");
    }

}

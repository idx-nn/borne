package nn.iamj.borne.basic.gameplay.listeners.ability;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.impl.addons.SkillManager;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;

public final class SkillListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTick(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        final Entity damager = event.getDamager();

        if (!(damager instanceof Player player)) return;

        final Profile profile = Profile.asName(player.getName());

        if (profile == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.ATTACK);
        final int skillModifier = profile.getStorage().getSkill(SkillType.ATTACK);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity entity = event.getEntity();
        final Profile profile = Profile.asEntity(entity);

        if (profile == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.DEFENCE);
        final int skillModifier = profile.getStorage().getSkill(SkillType.DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosionTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity entity = event.getEntity();
        final Profile profile = Profile.asEntity(entity);

        if (profile == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.EXPLOSION_DEFENCE);
        final int skillModifier = profile.getStorage().getSkill(SkillType.EXPLOSION_DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTemperatureTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity entity = event.getEntity();
        final Profile profile = Profile.asEntity(entity);

        if (profile == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.TEMPERATURE_DEFENCE);
        final int skillModifier = profile.getStorage().getSkill(SkillType.TEMPERATURE_DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPoisonTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity entity = event.getEntity();
        final Profile profile = Profile.asEntity(entity);

        if (profile == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.POISON_DEFENCE);
        final int skillModifier = profile.getStorage().getSkill(SkillType.POISON_DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

}

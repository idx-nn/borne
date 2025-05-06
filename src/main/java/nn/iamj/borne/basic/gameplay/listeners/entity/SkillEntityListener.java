package nn.iamj.borne.basic.gameplay.listeners.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.impl.addons.SkillManager;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;
import nn.iamj.borne.modules.util.entity.CustomEntity;

public final class SkillEntityListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTick(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        final Entity damager = event.getDamager();

        if (!(damager instanceof LivingEntity livingEntity)) return;

        final CustomEntity entity = Borne.getBorne().getEntityManager().getEntity(livingEntity.getUniqueId());

        if (entity == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.ATTACK);
        final int skillModifier = entity.getSettings().getSkill(SkillType.ATTACK);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity damager = event.getEntity();
        if (!(damager instanceof LivingEntity livingEntity)) return;

        final CustomEntity entity = Borne.getBorne().getEntityManager().getEntity(livingEntity.getUniqueId());

        if (entity == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.DEFENCE);
        final int skillModifier = entity.getSettings().getSkill(SkillType.DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosionTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity damager = event.getEntity();
        if (!(damager instanceof LivingEntity livingEntity)) return;

        final CustomEntity entity = Borne.getBorne().getEntityManager().getEntity(livingEntity.getUniqueId());

        if (entity == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.EXPLOSION_DEFENCE);
        final int skillModifier = entity.getSettings().getSkill(SkillType.EXPLOSION_DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTemperatureTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity damager = event.getEntity();
        if (!(damager instanceof LivingEntity livingEntity)) return;

        final CustomEntity entity = Borne.getBorne().getEntityManager().getEntity(livingEntity.getUniqueId());

        if (entity == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.TEMPERATURE_DEFENCE);
        final int skillModifier = entity.getSettings().getSkill(SkillType.TEMPERATURE_DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPoisonTick(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity damager = event.getEntity();
        if (!(damager instanceof LivingEntity livingEntity)) return;

        final CustomEntity entity = Borne.getBorne().getEntityManager().getEntity(livingEntity.getUniqueId());

        if (entity == null) return;

        final SkillManager skillManager = Borne.getBorne().getSkillManager();

        final Skill skill = skillManager.getSkill(SkillType.POISON_DEFENCE);
        final int skillModifier = entity.getSettings().getSkill(SkillType.POISON_DEFENCE);

        if (skill == null || skillModifier == 0) return;

        skill.handle(event, skillModifier);
    }

}

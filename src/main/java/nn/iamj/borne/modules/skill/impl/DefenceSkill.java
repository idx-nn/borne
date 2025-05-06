package nn.iamj.borne.modules.skill.impl;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;

public class DefenceSkill extends Skill {

    public DefenceSkill() {
        super(SkillType.DEFENCE);
    }

    @Override
    public void handle(Event event, int level) {
        if (!(event instanceof EntityDamageEvent damageEvent))
            return;

        if (damageEvent.getDamage() == 0.0D) return;

        if (damageEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK
            && damageEvent.getCause() != EntityDamageEvent.DamageCause.PROJECTILE
            && damageEvent.getCause() != EntityDamageEvent.DamageCause.WITHER
            && damageEvent.getCause() != EntityDamageEvent.DamageCause.FALL
            && damageEvent.getCause() != EntityDamageEvent.DamageCause.FALLING_BLOCK) return;

        damageEvent.setDamage(damageEvent.getDamage() * (1 - (0.078 * level)));
    }

}

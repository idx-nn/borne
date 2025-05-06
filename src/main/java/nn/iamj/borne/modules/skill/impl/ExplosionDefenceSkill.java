package nn.iamj.borne.modules.skill.impl;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;

public class ExplosionDefenceSkill extends Skill {

    public ExplosionDefenceSkill() {
        super(SkillType.EXPLOSION_DEFENCE);
    }

    @Override
    public void handle(Event event, int level) {
        if (!(event instanceof EntityDamageEvent damageEvent))
            return;

        if (damageEvent.getDamage() == 0.0D) return;

        if (damageEvent.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
                && damageEvent.getCause() != EntityDamageEvent.DamageCause.THORNS
                && damageEvent.getCause() != EntityDamageEvent.DamageCause.LIGHTNING
                && damageEvent.getCause() != EntityDamageEvent.DamageCause.MAGIC) return;

        damageEvent.setDamage(damageEvent.getDamage() * (1 - (0.136 * level)));
    }

}

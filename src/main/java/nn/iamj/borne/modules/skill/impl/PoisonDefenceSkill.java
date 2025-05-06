package nn.iamj.borne.modules.skill.impl;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;

public class PoisonDefenceSkill extends Skill {

    public PoisonDefenceSkill() {
        super(SkillType.POISON_DEFENCE);
    }

    @Override
    public void handle(Event event, int level) {
        if (!(event instanceof EntityDamageEvent damageEvent))
            return;

        if (damageEvent.getDamage() == 0.0D) return;

        if (damageEvent.getCause() != EntityDamageEvent.DamageCause.POISON
                && damageEvent.getCause() != EntityDamageEvent.DamageCause.DRAGON_BREATH
                && damageEvent.getCause() != EntityDamageEvent.DamageCause.MAGIC) return;

        damageEvent.setDamage(damageEvent.getDamage() * (1 - (0.220 * level)));
    }

}

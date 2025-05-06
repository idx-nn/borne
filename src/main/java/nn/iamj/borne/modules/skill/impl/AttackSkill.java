package nn.iamj.borne.modules.skill.impl;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;

public class AttackSkill extends Skill {

    public AttackSkill() {
        super(SkillType.ATTACK);
    }

    @Override
    public void handle(Event event, int level) {
        if (!(event instanceof EntityDamageByEntityEvent damageEvent))
            return;

        if (damageEvent.getDamage() == 0.0D) return;

        damageEvent.setDamage(damageEvent.getDamage() * (1.009 * level));
    }

}

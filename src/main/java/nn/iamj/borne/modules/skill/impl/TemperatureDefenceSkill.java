package nn.iamj.borne.modules.skill.impl;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;

public class TemperatureDefenceSkill extends Skill {

    public TemperatureDefenceSkill() {
        super(SkillType.TEMPERATURE_DEFENCE);
    }

    @Override
    public void handle(Event event, int level) {
        if (!(event instanceof EntityDamageEvent damageEvent))
            return;

        if (damageEvent.getDamage() == 0.0D) return;

        if (damageEvent.getCause() != EntityDamageEvent.DamageCause.FIRE
                && damageEvent.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) return;

        damageEvent.setDamage(damageEvent.getDamage() * (1 - (0.128 * level)));
    }

}

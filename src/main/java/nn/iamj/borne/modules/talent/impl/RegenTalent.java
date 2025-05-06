package nn.iamj.borne.modules.talent.impl;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import nn.iamj.borne.modules.talent.Talent;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.concurrent.ThreadLocalRandom;

public class RegenTalent extends Talent {

    public RegenTalent() {
        super(TalentType.REGEN);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean handle(final Entity entity, final int level) {
        if (level <= 0 || level >= 9 || !(entity instanceof LivingEntity living))
            return false;

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final double number = Math.abs(Math.sqrt(level) * 3.95 / (9 - level));

        if (random.nextDouble(0.0D, 100.0D) > number)
            return false;

        living.setHealth(Math.max(living.getMaxHealth(), living.getHealth() + 1));
        return true;
    }

}

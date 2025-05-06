package nn.iamj.borne.modules.talent.impl;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import nn.iamj.borne.modules.talent.Talent;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.concurrent.ThreadLocalRandom;

public class VampireTalent extends Talent {

    public VampireTalent() {
        super(TalentType.VAMPIRE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean handle(final Entity entity, final int level) {
        if (level <= 0 || level >= 14 || !(entity instanceof LivingEntity living))
            return false;

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final double number = Math.abs(Math.sqrt(level) * 2.45 / (14 - level));

        if (random.nextDouble(0.0D, 100.0D) > number)
            return false;

        living.setHealth(Math.max(living.getMaxHealth(), living.getHealth() + (level * 0.75)));

        return true;
    }

}

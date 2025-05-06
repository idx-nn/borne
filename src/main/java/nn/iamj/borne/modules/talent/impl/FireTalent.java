package nn.iamj.borne.modules.talent.impl;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import nn.iamj.borne.modules.talent.Talent;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.concurrent.ThreadLocalRandom;

public class FireTalent extends Talent {

    public FireTalent() {
        super(TalentType.FIRE);
    }

    @Override
    public boolean handle(final Entity entity, final int level) {
        if (level <= 0 || level >= 85 || !(entity instanceof LivingEntity living))
            return false;

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final double number = Math.abs(Math.sqrt(level) * 1.49 / (85 - level));

        if (random.nextDouble(0.0D, 100.0D) > number)
            return false;

        living.setFireTicks(level * 12);
        living.setVisualFire(true);

        return true;
    }

}

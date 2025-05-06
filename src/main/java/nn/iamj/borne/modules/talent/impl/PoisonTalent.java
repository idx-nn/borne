package nn.iamj.borne.modules.talent.impl;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import nn.iamj.borne.modules.talent.Talent;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.concurrent.ThreadLocalRandom;

public class PoisonTalent extends Talent {

    public PoisonTalent() {
        super(TalentType.POISON);
    }

    @Override
    public boolean handle(final Entity entity, final int level) {
        if (level <= 0 || level >= 63 || !(entity instanceof LivingEntity living))
            return false;

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final double number = Math.abs(Math.sqrt(level) * 1.55 / (63 - level));

        if (random.nextDouble(0.0D, 100.0D) > number)
            return false;

        living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, level * 55, 1, false, false, false));
        return true;
    }

}

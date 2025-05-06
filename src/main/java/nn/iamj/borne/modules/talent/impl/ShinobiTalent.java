package nn.iamj.borne.modules.talent.impl;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import nn.iamj.borne.modules.talent.Talent;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.concurrent.ThreadLocalRandom;

public class ShinobiTalent extends Talent {

    public ShinobiTalent() {
        super(TalentType.SHINOBI);
    }

    @Override
    public boolean handle(final Entity entity, final int level) {
        if (level <= 0 || level >= 59 || !(entity instanceof LivingEntity living))
            return false;

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final double number = Math.abs(Math.sqrt(level) * 2.45 / (59 - level));

        if (random.nextDouble(0.0D, 100.0D) > number)
            return false;

        living.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(level * 12, 2));
        living.addPotionEffect(PotionEffectType.SLOW.createEffect(level * 12, 2));

        return true;
    }

}

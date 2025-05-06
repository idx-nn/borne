package nn.iamj.borne.modules.boss.settings;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import nn.iamj.borne.modules.boss.Boss;

@Getter
public final class BossSettings {

    private final Boss boss;

    @Setter
    private double health;

    @Setter
    private int cooldownAbility;
    @Setter
    private int cooldownBerserk;

    private long lastHealth;

    public BossSettings(final Boss boss) {
        this.boss = boss;
    }

    @SuppressWarnings("deprecation")
    public void tick() {
        if (this.lastHealth + 5000 > System.currentTimeMillis()) return;

        if (this.boss.getUuid() == null) return;

        final Entity entity = Bukkit.getEntity(this.boss.getUuid());

        if (!(entity instanceof LivingEntity living)) return;

        living.setHealth(Math.min(living.getMaxHealth(), living.getHealth() + this.health));

        this.lastHealth = System.currentTimeMillis();
    }

}

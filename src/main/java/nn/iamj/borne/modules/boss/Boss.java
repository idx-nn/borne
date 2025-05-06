package nn.iamj.borne.modules.boss;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.api.events.boss.BossDeathEvent;
import nn.iamj.borne.modules.api.events.boss.BossSpawnEvent;
import nn.iamj.borne.modules.api.events.boss.BossTickEvent;
import nn.iamj.borne.modules.boss.ability.BossAbility;
import nn.iamj.borne.modules.boss.bar.BossBar;
import nn.iamj.borne.modules.boss.berserk.BossBerserk;
import nn.iamj.borne.modules.boss.settings.BossSettings;
import nn.iamj.borne.modules.util.entity.CustomEntity;
import nn.iamj.borne.modules.util.event.EventUtils;

import java.util.UUID;

@Getter
public final class Boss extends CustomEntity {

    private final BossType bossType;

    @Setter
    private BossAbility ability;
    @Setter
    private BossSettings bossSettings;
    @Setter
    private BossBerserk berserk;
    @Setter
    private BossBar bar;

    private boolean alive;

    private long lastAbility;
    private long lastBerserk;

    public Boss(final EntityType type, final BossType bossType) {
        super(type);

        this.bossType = bossType;
    }

    @Override
    public UUID make(final Location location) {
        EventUtils.callStaticEvent(new BossSpawnEvent(this));

        this.lastAbility = this.lastBerserk = System.currentTimeMillis();
        this.alive = true;

        return super.make(location);
    }

    public void tick() {
        if (!this.alive) return;

        EventUtils.callStaticEvent(new BossTickEvent(this));

        if (this.berserk != null) {
            this.berserk.tick();
            if (this.lastBerserk + this.bossSettings.getCooldownBerserk() < System.currentTimeMillis()) {
                this.lastBerserk = System.currentTimeMillis();
                this.berserk.swap();
            }
        }

        if (this.ability != null && this.lastAbility + this.bossSettings.getCooldownAbility() < System.currentTimeMillis()) {
            this.lastAbility = System.currentTimeMillis();
            this.ability.tick();
        }

        this.bossSettings.tick();

        this.bar.tick();
    }

    public void kill() {
        this.kill(null);
    }

    public void kill(final Entity entity) {
        if (this.getUuid() == null) return;

        this.alive = false;
        this.bar.end();

        Borne.getBorne().getEntityManager().killEntity(this.getUuid());

        EventUtils.callStaticEvent(new BossDeathEvent(this, entity));
    }

}

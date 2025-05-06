package nn.iamj.borne.modules.boss.ability;

import lombok.Getter;
import nn.iamj.borne.modules.boss.Boss;

@Getter
public abstract class BossAbility {

    private final Boss boss;

    public BossAbility(final Boss boss) {
        this.boss = boss;
    }

    public abstract void tick();

}

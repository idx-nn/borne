package nn.iamj.borne.modules.boss.berserk;

import lombok.Getter;
import nn.iamj.borne.modules.api.events.boss.BossBerserkEvent;
import nn.iamj.borne.modules.boss.Boss;
import nn.iamj.borne.modules.util.event.EventUtils;

@Getter
public abstract class BossBerserk {

    private final Boss boss;

    private boolean active;

    public BossBerserk(final Boss boss) {
        this.boss = boss;
    }

    public final void swap() {
        if (!this.active)
            this.doStart();
        else this.doStop();
    }

    public abstract void tick();

    public abstract void start();

    public abstract void stop();

    public final void doStart() {
        EventUtils.callStaticEvent(new BossBerserkEvent
                (this.boss, BossBerserkEvent.Type.START));
        this.active = true;

        this.start();
    }

    public final void doStop() {
        EventUtils.callStaticEvent(new BossBerserkEvent
                (this.boss, BossBerserkEvent.Type.END));
        this.active = false;

        this.stop();
    }

}

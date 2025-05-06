package nn.iamj.borne.modules.api.events.boss;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.boss.Boss;

@Getter
public final class BossBerserkEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Boss boss;
    private final Type type;

    public BossBerserkEvent(final Boss boss, final Type type) {
        super(true);

        this.boss = boss;
        this.type = type;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

    public enum Type {
        START,
        END
    }

}

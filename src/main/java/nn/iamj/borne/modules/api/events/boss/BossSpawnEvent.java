package nn.iamj.borne.modules.api.events.boss;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.boss.Boss;

@Getter
public final class BossSpawnEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Boss boss;

    public BossSpawnEvent(final Boss boss) {
        super(true);

        this.boss = boss;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}
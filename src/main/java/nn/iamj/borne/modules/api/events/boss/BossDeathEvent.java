package nn.iamj.borne.modules.api.events.boss;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.boss.Boss;

@Getter
public final class BossDeathEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Boss boss;
    private final Entity killer;

    public BossDeathEvent(final Boss boss, final Entity killer) {
        super(true);

        this.boss = boss;
        this.killer = killer;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

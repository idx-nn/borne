package nn.iamj.borne.modules.api.events.human;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.human.AbstractHuman;

@Getter
public final class HumanSpawnEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final AbstractHuman human;

    public HumanSpawnEvent(final AbstractHuman human) {
        super(true);

        this.human = human;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

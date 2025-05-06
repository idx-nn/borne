package nn.iamj.borne.modules.api.events.mine;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.mine.Mine;

@Getter
public final class MineCreateEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Mine mine;

    public MineCreateEvent(final Mine mine) {
        super(true);

        this.mine = mine;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}
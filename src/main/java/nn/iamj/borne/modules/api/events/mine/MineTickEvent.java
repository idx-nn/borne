package nn.iamj.borne.modules.api.events.mine;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.mine.Mine;

@Getter
public final class MineTickEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Mine mine;

    public MineTickEvent(final Mine mine) {
        super(true);

        this.mine = mine;
    }

    @Setter
    private boolean cancelled;

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

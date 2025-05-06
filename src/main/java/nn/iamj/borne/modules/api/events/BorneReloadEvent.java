package nn.iamj.borne.modules.api.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public final class BorneReloadEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public BorneReloadEvent() {
        super(true);
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

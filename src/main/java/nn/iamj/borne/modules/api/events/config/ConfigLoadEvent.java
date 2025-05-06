package nn.iamj.borne.modules.api.events.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public final class ConfigLoadEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final String path;

    @Setter
    private boolean cancelled;

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

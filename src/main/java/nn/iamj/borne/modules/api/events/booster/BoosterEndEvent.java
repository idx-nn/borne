package nn.iamj.borne.modules.api.events.booster;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.booster.Booster;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class BoosterEndEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final String author;
    private final Booster booster;

    @Setter
    private boolean cancelled;

    public BoosterEndEvent(final String author, final Booster booster) {
        super(true);

        this.author = author;
        this.booster = booster;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

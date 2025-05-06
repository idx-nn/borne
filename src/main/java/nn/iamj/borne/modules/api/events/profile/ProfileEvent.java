package nn.iamj.borne.modules.api.events.profile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.profile.Profile;

@Getter
public class ProfileEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Profile profile;

    @Setter
    private boolean cancelled;

    public ProfileEvent(final Profile profile) {
        super(true);

        this.profile = profile;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

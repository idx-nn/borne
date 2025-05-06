package nn.iamj.borne.modules.api.events.profile.hud;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.api.events.profile.ProfileEvent;
import nn.iamj.borne.modules.profile.Profile;

@Setter
@Getter
public final class ProfileHudUpdateEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private boolean cancelled;

    public ProfileHudUpdateEvent(final Profile profile) {
        super(profile);
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}


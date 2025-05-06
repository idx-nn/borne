package nn.iamj.borne.modules.api.events.profile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.profile.Profile;

@Getter
public final class ProfileSaveEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final boolean onlyCache;

    @Setter
    private boolean cancelled;

    public ProfileSaveEvent(final Profile profile, final boolean onlyCache) {
        super(profile);

        this.onlyCache = onlyCache;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

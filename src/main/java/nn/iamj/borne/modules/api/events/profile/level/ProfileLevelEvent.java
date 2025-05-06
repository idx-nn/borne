package nn.iamj.borne.modules.api.events.profile.level;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.api.events.profile.ProfileEvent;
import nn.iamj.borne.modules.profile.Profile;

@Getter
public final class ProfileLevelEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final EventType type;

    private final int newLevel;
    private final int oldLevel;

    @Setter
    private boolean cancelled;

    public ProfileLevelEvent(final Profile profile, final EventType type, final int newLevel, final int oldLevel) {
        super(profile);

        this.type = type;

        this.newLevel = newLevel;
        this.oldLevel = oldLevel;
    }

    public enum EventType {
        UP,
        DOWN
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

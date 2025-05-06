package nn.iamj.borne.modules.api.events.profile.level;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.api.events.profile.ProfileEvent;
import nn.iamj.borne.modules.profile.Profile;

@Getter
public final class ProfileExperienceEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final EventType type;

    private final double newExperience;
    private final double oldExperience;

    @Setter
    private boolean cancelled;

    public ProfileExperienceEvent(final Profile profile, final EventType type, final double newExperience, final double oldExperience) {
        super(profile);

        this.type = type;

        this.newExperience = newExperience;
        this.oldExperience = oldExperience;
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


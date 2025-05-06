package nn.iamj.borne.modules.api.events.human;

import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.human.Human;
import nn.iamj.borne.modules.profile.Profile;

@Getter
public final class HumanUsageEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Profile profile;
    private final Human human;
    private final UsageType usageType;

    @Setter
    private boolean cancelled;

    public HumanUsageEvent(final Profile profile, final Human human, final UsageType usageType) {
        super(true);

        this.profile = profile;
        this.human = human;
        this.usageType = usageType;
    }

    public enum UsageType {

        ATTACK,
        INTERACT,
        UNKNOWN;

        public static UsageType fromContainer(final EnumWrappers.EntityUseAction action) {
            try {
                return valueOf(action.name());
            } catch (Exception e) { return UNKNOWN; }
        }

    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

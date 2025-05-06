package nn.iamj.borne.modules.api.events.profile.wallet;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.api.events.profile.ProfileEvent;
import nn.iamj.borne.modules.profile.Profile;

@Getter
public final class ProfileWalletEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final WalletType walletType;
    private final OperationType operationType;
    private final EventPriority eventPriority;

    private final double number;

    @Setter
    private boolean cancelled;

    public ProfileWalletEvent(final Profile profile, final WalletType walletType, final OperationType operationType, final EventPriority eventPriority, final double number) {
        super(profile);

        this.walletType = walletType;
        this.operationType = operationType;
        this.eventPriority = eventPriority;

        this.number = number;
    }

    public enum WalletType {
        DONATED,
        MONEY
    }

    public enum OperationType {
        ADDED,
        SPECIAL,
        REMOVED
    }

    public enum EventPriority {
        PREVIEW,
        POST
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

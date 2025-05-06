package nn.iamj.borne.modules.api.events.profile;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.profile.Profile;

@Getter
public final class ProfileLoadEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final boolean playedBefore;

    public ProfileLoadEvent(final Profile profile, final Player player, final boolean playedBefore) {
        super(profile);

        this.player = player;
        this.playedBefore = playedBefore;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

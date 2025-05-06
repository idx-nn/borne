package nn.iamj.borne.modules.api.events.profile.action;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.modules.api.events.profile.ProfileEvent;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.profile.Profile;

@Setter
@Getter
public final class ProfileBlockBreakEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Mine mine;
    private final Block block;

    private boolean cancelled;

    public ProfileBlockBreakEvent(final Profile profile, final Mine mine, final Block block) {
        super(profile);

        this.mine = mine;
        this.block = block;
    }

    @Override @NotNull
    public HandlerList getHandlers() {
        return handlerList;
    }

}

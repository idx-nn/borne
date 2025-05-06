package nn.iamj.borne.modules.profile.level;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.api.events.profile.level.ProfileExperienceEvent;
import nn.iamj.borne.modules.api.events.profile.level.ProfileLevelEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.event.EventUtils;
import nn.iamj.borne.modules.util.math.BetterNumbers;

@Getter
public final class Level {

    private final Profile profile;

    @Setter
    private int level;
    @Setter
    private double experience;

    public Level(final Profile profile) {
        this.profile = profile;

        this.level = 1;
    }

    public void addLevel(final int level) {
        final boolean result = EventUtils.callEvent(new ProfileLevelEvent(profile, ProfileLevelEvent.EventType.UP, this.level + level, this.level));

        if (!result) return;

        this.level += level;
    }

    public boolean removeLevel(final int level) {
        if (this.level < level) return false;

        final boolean result = EventUtils.callEvent(new ProfileLevelEvent(profile, ProfileLevelEvent.EventType.DOWN, this.level - level, this.level));

        if (!result) return false;

        this.level -= level;
        return true;
    }

    public void addExperience(final double experience) {
        this.addExperience(experience, false);
    }

    public void addExperience(final double experience, final boolean force) {
        if (!force) {
            final boolean result = EventUtils.callEvent(new ProfileExperienceEvent(profile, ProfileExperienceEvent.EventType.UP, this.experience + experience, this.experience));

            if (!result) return;
        }

        this.experience += BetterNumbers.floor(4, experience);
    }

    public boolean removeExperience(final double experience) {
        if (this.experience < experience) return false;

        final boolean result = EventUtils.callEvent(new ProfileExperienceEvent(profile, ProfileExperienceEvent.EventType.DOWN, this.experience - experience, this.experience));

        if (!result) return false;

        this.experience -= BetterNumbers.floor(4, experience);
        return true;
    }

}

package nn.iamj.borne.modules.api.events.profile.talent;

import lombok.Getter;
import nn.iamj.borne.modules.api.events.profile.ProfileEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.talent.Talent;

@Getter
public final class ProfileTalentEvent extends ProfileEvent {

    private final Talent talent;

    public ProfileTalentEvent(final Profile profile, final Talent talent) {
        super(profile);

        this.talent = talent;
    }

}

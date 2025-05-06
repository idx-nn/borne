package nn.iamj.borne.modules.profile.system;

import lombok.Getter;
import nn.iamj.borne.modules.profile.Profile;

public final class SystemProfile extends Profile {

    @Getter
    private static final SystemProfile profile = new SystemProfile();

    public SystemProfile() {
        super("System");
    }

}

package nn.iamj.borne.modules.menu.executable;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;

@Getter
@Setter
public class ExecutableClick {

    private MenuSlot slot;

    public void onLeft(final Profile profile) {}
    public void onMiddle(final Profile profile) {
        this.onLeft(profile);
    }
    public void onRight(final Profile profile) {
        this.onLeft(profile);
    }
    public void onShiftLeft(final Profile profile) {
        this.onLeft(profile);
    }
    public void onShiftRight(final Profile profile) {
        this.onLeft(profile);
    }
    public void onDrop(final Profile profile) {
        this.onLeft(profile);
    }
    public void onControlDrop(final Profile profile) {
        this.onLeft(profile);
    }

}

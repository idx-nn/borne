package nn.iamj.borne.modules.human;

import nn.iamj.borne.modules.human.assets.skin.HumanSkin;
import nn.iamj.borne.modules.profile.Profile;

public abstract class Human extends AbstractHuman {

    public Human(final String name, final HumanSkin skin) {
        super(name);
        super.setSkin(skin);
    }

    public abstract void onAttack(final Profile profile);
    public abstract void onInteract(final Profile profile);

}

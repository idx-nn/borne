package nn.iamj.borne.modules.mine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class MineSettings {

    private boolean allowPvP;
    private double minRatio;

    private int minLevel;
    private int cooldown;
    private int priority;

}

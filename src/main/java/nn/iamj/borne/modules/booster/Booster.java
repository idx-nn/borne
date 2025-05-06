package nn.iamj.borne.modules.booster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class Booster implements Serializable {

    private double modifier;
    private int seconds;

}

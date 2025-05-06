package nn.iamj.borne.modules.talent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Entity;
import nn.iamj.borne.modules.ability.Ability;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Talent implements Ability {

    private final TalentType type;

    public abstract boolean handle(final Entity entity, final int level);

}

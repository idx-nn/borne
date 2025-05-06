package nn.iamj.borne.modules.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import nn.iamj.borne.modules.ability.Ability;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Skill implements Ability {

    private final SkillType type;

    public abstract void handle(final Event event, final int level);

}

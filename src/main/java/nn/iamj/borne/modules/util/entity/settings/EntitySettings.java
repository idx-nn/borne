package nn.iamj.borne.modules.util.entity.settings;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.skill.SkillType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class EntitySettings {

    private int health;
    private int speed;

    @Setter
    public Map<SkillType, Integer> skills = new ConcurrentHashMap<>();

    public void addSkill(final SkillType type) {
        this.skills.put(type, 1);
    }

    public void addSkill(final SkillType type, final int modifier) {
        this.skills.put(type, modifier);
    }

    public void removeSkill(final SkillType type) {
        this.skills.remove(type);
    }

    public int getSkill(final SkillType type) {
        return this.skills.getOrDefault(type, 0);
    }

}

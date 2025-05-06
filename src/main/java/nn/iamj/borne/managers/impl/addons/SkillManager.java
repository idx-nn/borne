package nn.iamj.borne.managers.impl.addons;

import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.skill.Skill;
import nn.iamj.borne.modules.skill.SkillType;
import nn.iamj.borne.modules.skill.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SkillManager implements Manager {

    private final Map<SkillType, Skill> skills = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {
        this.registerSkill(new AttackSkill());
        this.registerSkill(new DefenceSkill());
        this.registerSkill(new TemperatureDefenceSkill());
        this.registerSkill(new ExplosionDefenceSkill());
        this.registerSkill(new PoisonDefenceSkill());
    }

    @Override
    public void shutdown() {
        this.skills.clear();
    }

    public void registerSkill(final Skill skill) {
        this.skills.put(skill.getType(), skill);
    }

    public void unregisterSkill(final SkillType type) {
        this.skills.remove(type);
    }

    public Skill getSkill(final SkillType type) {
        return this.skills.get(type);
    }

}
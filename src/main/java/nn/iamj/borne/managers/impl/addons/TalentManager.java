package nn.iamj.borne.managers.impl.addons;

import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.talent.Talent;
import nn.iamj.borne.modules.talent.TalentType;
import nn.iamj.borne.modules.talent.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TalentManager implements Manager {

    private final Map<TalentType, Talent> talents = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {
        this.registerTalent(new PoisonTalent());
        this.registerTalent(new RegenTalent());
        this.registerTalent(new FireTalent());
        this.registerTalent(new ShinobiTalent());
        this.registerTalent(new VampireTalent());
    }

    @Override
    public void shutdown() {
        this.talents.clear();
    }

    public void registerTalent(final Talent talent) {
        this.talents.put(talent.getType(), talent);
    }

    public void unregisterTalent(final TalentType type) {
        this.talents.remove(type);
    }

    public Talent getTalent(final TalentType type) {
        return this.talents.get(type);
    }

}

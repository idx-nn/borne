package nn.iamj.borne.modules.profile.assets;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.booster.Booster;
import nn.iamj.borne.modules.profile.assets.enums.StorageWalletType;
import nn.iamj.borne.modules.skill.SkillType;
import nn.iamj.borne.modules.talent.TalentType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class Storage {

    private final List<Booster> boosters = new ArrayList<>();

    private final Map<StorageWalletType, Integer> wallets = new ConcurrentHashMap<>();
    private final Map<TalentType, Integer> talents = new ConcurrentHashMap<>();
    private final Map<SkillType, Integer> skills = new ConcurrentHashMap<>();

    public void addBooster(final Booster booster) {
        this.boosters.add(booster);
    }

    public void removeBooster(final Booster booster) {
        this.boosters.remove(booster);
    }

    public int getWallet(final StorageWalletType type) {
        if (type == null) return 0;

        return this.wallets.getOrDefault(type, 0);
    }

    public void addWallet(final StorageWalletType type, final int count) {
        if (type == null) return;

        this.setWallet(type, getWallet(type) + count);
    }

    public boolean removeWallet(final StorageWalletType type, final int count) {
        if (type == null) return false;

        if (this.getWallet(type) < count)
            return false;

        this.setWallet(type, getWallet(type) - count);
        return true;
    }

    public void setWallet(final StorageWalletType type, final int count) {
        if (type == null) return;

        this.wallets.put(type, count);
    }

    public void upgradeTalent(final TalentType type) {
        this.upgradeTalent(type, 1);
    }

    public void upgradeTalent(final TalentType type, final int number) {
        if (number < 0)
            return;

        this.talents.put(type, getTalent(type) + number);
    }

    public void downgradeTalent(final TalentType type, final int number) {
        if (number < 0)
            return;

        this.talents.put(type, getTalent(type) > number ? getTalent(type) - number : 0);
    }

    public boolean resetTalent(final TalentType type) {
        if (!this.talents.containsKey(type)) return false;

        this.talents.remove(type);
        return true;
    }

    public int getTalent(final TalentType type) {
        return this.talents.getOrDefault(type, 0);
    }

    public void upgradeSkill(final SkillType type) {
        this.upgradeSkill(type, 1);
    }

    public void upgradeSkill(final SkillType type, final int number) {
        if (number < 0)
            return;

        this.skills.put(type, getSkill(type) + number);
    }

    public void downgradeSkill(final SkillType type, final int number) {
        if (number < 0)
            return;

        this.skills.put(type, getSkill(type) > number ? getSkill(type) - number : 0);
    }

    public boolean resetSkill(final SkillType type) {
        if (!this.skills.containsKey(type)) return false;

        this.skills.remove(type);
        return true;
    }

    public int getSkill(final SkillType type) {
        return this.skills.getOrDefault(type, 0);
    }

}

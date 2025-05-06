package nn.iamj.borne.modules.profile.assets;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public final class Statistic {
    
    private int blockBreaks;

    private int dies;
    private int kills;
    private int mobKills;
    private int bossKills;

    private double allMoney;
    private double allMoneyNoBoosters;

    private double damage;
    private int punches;

    private double allFee;

    private boolean selfThanks;

    private Map<TalentType, Integer> activations = new ConcurrentHashMap<>();

    public void addAllMoney(final double money) {
        this.allMoney += money;
    }

    public void removeAllMoney(final double money) {
        this.allMoney -= money;
    }

    public void addAllMoneyNoBoosters(final double money) {
        this.allMoneyNoBoosters += money;
    }

    public void removeAllMoneyNoBoosters(final double money) {
        this.allMoneyNoBoosters -= money;
    }

    public void addFee(final double fee) {
        this.allFee += fee;
    }

    public void removeFee(final double fee) {
        this.allFee -= fee;
    }

    public void addBlockBreaks(final int number) {
        this.blockBreaks += number;
    }

    public void removeBlockBreaks(final int number) {
        this.blockBreaks = Math.max(this.blockBreaks - number, 0);
    }

    public void addDies(final int number) {
        this.dies += number;
    }

    public void removeDies(final int number) {
        this.dies = Math.max(this.dies - number, 0);
    }

    public void addKills(final int number) {
        this.kills += number;
    }

    public void removeKills(final int number) {
        this.kills = Math.max(this.dies - number, 0);
    }

    public void addMobKills(final int number) {
        this.mobKills += number;
    }

    public void removeMobKills(final int number) {
        this.mobKills = Math.max(this.dies - number, 0);
    }

    public void addBossKills(final int number) {
        this.bossKills += number;
    }

    public void removeBossKills(final int number) {
        this.bossKills = Math.max(this.dies - number, 0);
    }

    public void addDamage(final double number) {
        this.damage += number;
    }

    public void removeDamage(final double number) {
        this.damage = Math.max(this.damage - number, 0);
    }

    public void addPunches(final int number) {
        this.punches += number;
    }

    public void removePunches(final int number) {
        this.punches = Math.max(this.punches - number, 0);
    }

    public void updateTalentActivations(final TalentType type) {
        this.updateTalentActivations(type, 1);
    }

    public void updateTalentActivations(final TalentType type, final int number) {
        if (number < 0)
            return;

        this.activations.put(type, getTalentActivations(type) + number);
    }

    public void downgradeTalentActivations(final TalentType type) {
        this.downgradeTalentActivations(type, 1);
    }

    public void downgradeTalentActivations(final TalentType type, final int number) {
        if (number < 0)
            return;

        this.activations.put(type, getTalentActivations(type) > number ? getTalentActivations(type) - number : 0);
    }

    public int getTalentActivations(final TalentType type) {
        return this.activations.getOrDefault(type, 0);
    }

}

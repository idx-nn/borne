package nn.iamj.borne.modules.profile.wallet;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.api.events.profile.wallet.ProfileWalletEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.event.EventUtils;
import nn.iamj.borne.modules.util.math.BetterNumbers;

@Getter
public final class Wallet {

    private final Profile profile;

    private double money;
    private double donated;

    @Setter
    private double devolve;

    public Wallet(final Profile profile) {
        this.profile = profile;
    }

    public void setDonated(final double donated) {
        final boolean result = EventUtils.callEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.DONATED, ProfileWalletEvent.OperationType.SPECIAL, ProfileWalletEvent.EventPriority.PREVIEW, devolve));

        if (!result) return;

        this.donated = BetterNumbers.floor(4, donated);

        EventUtils.callStaticEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.DONATED, ProfileWalletEvent.OperationType.SPECIAL, ProfileWalletEvent.EventPriority.POST, devolve));
    }

    public void addDonated(final double donated) {
        final boolean result = EventUtils.callEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.DONATED, ProfileWalletEvent.OperationType.ADDED, ProfileWalletEvent.EventPriority.PREVIEW, devolve));

        if (!result) return;

        this.donated += BetterNumbers.floor(4, donated);

        EventUtils.callEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.DONATED, ProfileWalletEvent.OperationType.ADDED, ProfileWalletEvent.EventPriority.POST, devolve));
    }

    public boolean removeDonated(final double donated) {
        if (this.donated < donated) return false;

        final boolean result = EventUtils.callEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.DONATED, ProfileWalletEvent.OperationType.REMOVED, ProfileWalletEvent.EventPriority.PREVIEW, devolve));

        if (!result) return false;

        this.donated -= BetterNumbers.floor(4, donated);

        EventUtils.callStaticEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.DONATED, ProfileWalletEvent.OperationType.REMOVED, ProfileWalletEvent.EventPriority.POST, devolve));
        return true;
    }

    public void addDevolve(final double devolve) {
        this.devolve += BetterNumbers.floor(4, devolve);
    }

    public boolean removeDevolve(final double devolve) {
        if (this.devolve < devolve) return false;

        this.devolve -= BetterNumbers.floor(4, devolve);
        return true;
    }

    public void setMoney(final double money) {
        final boolean result = EventUtils.callEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.MONEY, ProfileWalletEvent.OperationType.SPECIAL, ProfileWalletEvent.EventPriority.PREVIEW, money));

        if (!result) return;

        this.money = BetterNumbers.floor(4, money);

        EventUtils.callStaticEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.MONEY, ProfileWalletEvent.OperationType.SPECIAL, ProfileWalletEvent.EventPriority.POST, money));
    }

    public void addMoney(final double money) {
        final boolean result = EventUtils.callEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.MONEY, ProfileWalletEvent.OperationType.ADDED, ProfileWalletEvent.EventPriority.PREVIEW, money));

        if (!result) return;

        this.money += BetterNumbers.floor(4, money);

        EventUtils.callStaticEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.MONEY, ProfileWalletEvent.OperationType.ADDED, ProfileWalletEvent.EventPriority.POST, money));
    }

    public boolean removeMoney(final double money) {
        if (this.money < money) return false;

        final boolean result = EventUtils.callEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.MONEY, ProfileWalletEvent.OperationType.REMOVED, ProfileWalletEvent.EventPriority.PREVIEW, money));

        if (!result) return false;

        this.money -= BetterNumbers.floor(4, money);

        EventUtils.callStaticEvent(new ProfileWalletEvent(profile, ProfileWalletEvent.WalletType.MONEY, ProfileWalletEvent.OperationType.REMOVED, ProfileWalletEvent.EventPriority.POST, money));
        return true;
    }

}

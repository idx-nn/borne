package nn.iamj.borne.modules.commerce.wallet;

import nn.iamj.borne.modules.profile.assets.enums.StorageWalletType;

import java.util.Locale;

public enum CommerceWallet {

    DONATED,
    MONEY,

    ENERGY_CRYSTALS,

    COMMON_BOSS,
    RARE_BOSS,
    EPIC_BOSS,
    LEGENDARY_BOSS;

    public StorageWalletType convert() {
        try {
            return StorageWalletType.valueOf(this.name().toUpperCase(Locale.ROOT));
        } catch (final Exception exception) {
            return null;
        }
    }

}

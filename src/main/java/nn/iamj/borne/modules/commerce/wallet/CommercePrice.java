package nn.iamj.borne.modules.commerce.wallet;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class CommercePrice {

    private Map<CommerceWallet, Double> priceList = new ConcurrentHashMap<>();

    public void addPrice(final @NotNull CommerceWallet wallet, final double price) {
        this.priceList.put(wallet, price);
    }

    public double getPrice(final @NotNull CommerceWallet wallet) {
        return this.priceList.get(wallet);
    }

    public void removePrice(final @NotNull CommerceWallet wallet) {
        this.priceList.remove(wallet);
    }

}

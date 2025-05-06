package nn.iamj.borne.modules.commerce.unit;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.commerce.wallet.CommercePrice;
import nn.iamj.borne.modules.commerce.wallet.CommerceWallet;
import nn.iamj.borne.modules.profile.Profile;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter @Setter
public class CommerceUnit {

    private CommercePrice price;

    private int inflation;
    private int difference;
    private int ratio;

    private ItemStack display;

    private CommerceExecutable executable;
    private List<ItemStack> stackList = new ArrayList<>();
    private List<String> commandList = new ArrayList<>();

    public abstract static class CommerceExecutable {
        public abstract void onBuy(final Profile profile);
    }

    public CommerceUnit() {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("config.yml");

        if (configuration == null) return;

        this.inflation = configuration.getInt("COMMERCE.DEFAULT.INFLATION", 0);
        this.difference = configuration.getInt("COMMERCE.DEFAULT.DIFFERENCE", 12);
        this.ratio = configuration.getInt("COMMERCE.DEFAULT.RATIO", 50);
    }

    public ItemStack getDisplay() {
        if (this.display != null)
            return this.display;

        return this.stackList.isEmpty() ?
                new ItemStack(Material.AIR) :
                this.stackList.get(0);
    }

    public void addItem(final @NotNull ItemStack stack) {
        this.stackList.add(stack);
    }

    public void removeItem(final @NotNull ItemStack stack) {
        this.stackList.remove(stack);
    }

    public void setRunnable(final @NotNull CommerceExecutable executable) {
        this.executable = executable;
    }

    public boolean hasExecutable() {
        return this.executable != null;
    }

    public void addCommand(final @NotNull String command) {
        this.commandList.add(command);
    }

    public void removeCommand(final @NotNull String command) {
        this.commandList.remove(command);
    }

    public void tick() {
        final double price = this.getPrice().getPrice(CommerceWallet.MONEY);

        if (price == 0.0D) return;

        final int currentRatio = ThreadLocalRandom.current().nextInt(100);

        if (currentRatio <= this.ratio) {
            this.inflation -= this.difference;
            this.ratio = Math.min(100, this.ratio + 10);
        } else {
            this.inflation += this.difference;
            this.ratio = Math.max(0, this.ratio - 10);
        }
    }

}

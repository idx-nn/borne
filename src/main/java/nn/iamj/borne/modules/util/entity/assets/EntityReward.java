package nn.iamj.borne.modules.util.entity.assets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public final class EntityReward {

    private int money;
    private List<ItemStack> drops;

    public EntityReward() {
        this.drops = new ArrayList<>();
    }

    public void addItem(final ItemStack stack) {
        this.drops.add(stack);
    }

    public void removeItem(final ItemStack stack) {
        this.drops.remove(stack);
    }

}

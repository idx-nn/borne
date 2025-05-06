package nn.iamj.borne.modules.menu.slot;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.util.builders.ItemBuilder;

public class MenuSlot extends ItemBuilder {

    @Getter @Setter
    private ExecutableClick executableClick;
    private boolean disableInteract;
    @Getter @Setter
    private int position;
    @Getter @Setter
    private boolean backed;

    public MenuSlot(final ItemStack item) {
        super(item);
    }

    public MenuSlot(final Material material) {
        super(material);
    }

    @Deprecated
    public MenuSlot() {
        super();
    }

    public boolean hasExecutable() {
        return executableClick != null;
    }

    public void disableInteract(final boolean interact) {
        this.disableInteract = interact;
    }

    public boolean isInteractDisabled() {
        return this.disableInteract;
    }

}

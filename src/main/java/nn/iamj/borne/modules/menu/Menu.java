package nn.iamj.borne.modules.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import nn.iamj.borne.modules.menu.executable.ExecutableClose;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.server.printing.Coloriser;
import nn.iamj.borne.modules.util.logger.LoggerProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Menu {

    @Getter
    private final String id;
    @Getter
    private final String title;
    @Getter
    private Inventory inventory;

    @Getter
    private final Map<Integer, MenuSlot> slots;
    @Getter @Setter
    private ExecutableClose executableClose;

    @Getter @Setter
    private boolean interactDisabled;
    @Setter
    private boolean animationState;

    @SuppressWarnings("deprecation")
    public Menu(final String id, final String title, final int rows) {
        this.id = id;
        this.title = title;

        this.inventory = Bukkit.createInventory(null, rows * 9, Coloriser.colorify(title));

        this.slots = new ConcurrentHashMap<>();
        this.interactDisabled = true;
    }

    @SuppressWarnings("deprecation")
    public Menu(final String id, final InventoryHolder holder, final String title, final InventoryType type) {
        this.id = id;
        this.title = title;

        this.inventory = Bukkit.createInventory(holder, type, Coloriser.colorify(title));

        this.slots = new ConcurrentHashMap<>();
        this.interactDisabled = true;
    }

    @Deprecated
    public void setInventory(final Inventory inventory) {
        this.inventory = inventory;
        this.slots.clear();
    }

    public MenuSlot getSlot(final int position) {
        return this.slots.get(position);
    }

    public int getSlotPosition(final MenuSlot slot) {
        for (final MenuSlot slots : this.slots.values())
            if (slots.equals(slot))
                return slots.getPosition();

        return -1;
    }

    public Menu setSlot(final int position, final MenuSlot slot) {
        if (slot == null) {
            return this;
        }

        try {
            slot.setPosition(position);

            this.slots.put(position, slot);
            this.inventory.setItem(position, slot.getItem());

            return this;
        } catch (ArrayIndexOutOfBoundsException exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
            return this;
        }
    }

    public Menu setSlot(final MenuSlot slot) {
        return this.setSlot(slot.getPosition(), slot);
    }

    public boolean hasSlot(final int position) {
        return this.slots.containsKey(position);
    }

    public Menu removeSlot(final int position) {
        this.slots.remove(position);
        this.inventory.clear(position);

        return this;
    }

    public Menu refresh() {
        this.inventory.clear();

        this.slots.forEach((position, slot) ->
                this.inventory.setItem(position, slot.getItem()));
        this.inventory.getViewers().forEach(entity ->
                ((Player) entity).updateInventory());

        return this;
    }

    public Menu clean() {
        this.inventory.clear();
        this.slots.clear();

        return this;
    }

    public Menu refreshSlot(final int slot) {
        if (!slots.containsKey(slot))
            this.inventory.clear(slot);
        else inventory.setItem(slot, this.slots.get(slot).getItem());

        this.inventory.getViewers().forEach(entity ->
                ((Player) entity).updateInventory());

        return this;
    }

    public boolean hasExecutableClose() {
        return this.executableClose != null;
    }

    public boolean inAnimation() {
        return this.animationState;
    }

}


package nn.iamj.borne.modules.menu.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.util.collection.ExpiringSet;

public final class MenuListener implements Listener {

    private static final ExpiringSet<String> cooldown = new ExpiringSet<>(250);

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClick(final InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.NOTHING || event.isCancelled()) return;

        final Inventory clickedInventory = event.getClickedInventory();
        final Menu menu = Borne.getBorne().getMenuManager().getMenu(clickedInventory);

        if (menu == null) return;

        if (menu.isInteractDisabled() || menu.inAnimation()) {
            event.setCancelled(true);
        }

        final Profile profile = Borne.getBorne().getProfileManager().getProfile(event.getWhoClicked().getName());

        if (profile == null) return;

        if (cooldown.contains(profile.getName())) {
            event.setCancelled(true);
            return;
        }

        cooldown.add(profile.getName());

        final MenuSlot slot = menu.getSlot(event.getSlot());

        if (slot == null) return;

        if (!event.isCancelled() && slot.isInteractDisabled() && clickedInventory.equals(event.getView().getTopInventory()))
            event.setCancelled(true);

        if (slot.hasExecutable()) {
            final ExecutableClick click = slot.getExecutableClick();

            switch (event.getClick()) {
                case LEFT -> click.onLeft(profile);
                case RIGHT -> click.onRight(profile);
                case MIDDLE -> click.onMiddle(profile);

                case SHIFT_LEFT -> click.onShiftLeft(profile);
                case SHIFT_RIGHT -> click.onShiftRight(profile);

                case DROP -> click.onDrop(profile);
                case CONTROL_DROP -> click.onControlDrop(profile);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(final InventoryInteractEvent event) {
        if (event.isCancelled()) return;

        final Inventory clicked = event.getView().getTopInventory();
        final Menu menu = Borne.getBorne().getMenuManager().getMenu(clicked);

        if (menu != null) {
            if (menu.isInteractDisabled() || menu.inAnimation())
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(final InventoryCloseEvent event) {
        final Inventory closed = event.getInventory();
        final Menu menu = Borne.getBorne().getMenuManager().getMenu(closed);

        final Profile profile = Borne.getBorne().getProfileManager().getProfile(event.getPlayer().getName());

        if (profile == null) return;

        if (menu != null) {
            if (menu.hasExecutableClose())
                menu.getExecutableClose().run(profile);
        }
    }

}

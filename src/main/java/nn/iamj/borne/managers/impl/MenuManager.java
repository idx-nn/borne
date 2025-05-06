package nn.iamj.borne.managers.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MenuManager implements Manager {

    private final Map<String, ConcurrentHashMap<String, Menu>> guis;
    private final Map<Inventory, Menu> inv_guis;

    public MenuManager() {
        this.inv_guis = new ConcurrentHashMap<>();
        this.guis = new ConcurrentHashMap<>();
    }

    public Menu createMenu(final String id, final Profile profile, final Text title, final int rows) {
        return this.createMenu(id, profile.getName(), title.getRaw(), rows);
    }

    public Menu createMenu(final String id, final Profile profile, final String title, final int rows) {
        return this.createMenu(id, profile.getName(), title, rows);
    }

    public Menu createMenu(final String id, final String profile, final Text title, final int rows) {
        return this.createMenu(id, profile, title.getRaw(), rows);
    }

    public Menu createMenu(final String id, final String profile, final String title, final int rows) {
        final Menu menu = new Menu(id, title, rows);

        this.registerMenu(id, profile, menu);

        return menu;
    }

    public Menu createMenu(final String id, final Profile profile, final Text title, final InventoryType type) {
        return this.createMenu(id, profile.getName(), title.getRaw(), type);
    }

    public Menu createMenu(final String id, final String profile, final Text title, final InventoryType type) {
        return this.createMenu(id, profile, title.getRaw(), type);
    }

    public Menu createMenu(final String id, final Profile profile, final String title, final InventoryType type) {
        return this.createMenu(id, profile.getName(), title, type);
    }

    public Menu createMenu(final String id, final String profile, final String title, final InventoryType type) {
        final Menu menu = new Menu(id, null, title, type);

        this.registerMenu(id, profile, menu);

        return menu;
    }

    public void registerMenu(final String id, final String profile, final Menu menu) {
        if (!this.guis.containsKey(profile)) {
            this.guis.put(profile, new ConcurrentHashMap<>());
        }

        if (id != null && !id.isEmpty()) {
            this.guis.get(profile).put(menu.getId(), menu);
            this.inv_guis.put(menu.getInventory(), menu);
        }
    }

    public Menu getMenu(final Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        return this.inv_guis.get(inventory);
    }

    public Menu getMenu(final Profile profile, final String id) {
        return this.getMenu(profile.getName(), id);
    }

    public Menu getMenu(final String profile, final String id) {
        if (!this.guis.containsKey(profile)) {
            this.guis.put(profile, new ConcurrentHashMap<>());
        }

        return this.guis.get(profile).get(id);
    }

    public Collection<Menu> getMenus(final Profile profile) {
        return this.getMenus(profile.getName());
    }

    public Collection<Menu> getMenus(final String profile) {
        if (!this.guis.containsKey(profile)) {
            this.guis.put(profile, new ConcurrentHashMap<>());
        }

        return this.guis.get(profile).values();
    }

    public Menu getCurrentMenu(final Profile profile) {
        return this.getCurrentMenu(profile.getName());
    }

    public Menu getCurrentMenu(final String profile) {
        final Player player = Bukkit.getPlayerExact(profile);

        if (player == null) {
            return null;
        }

        for (final Menu menu : this.getMenus(profile))
            if (menu.getInventory().getViewers().contains(player))
                return menu;

        return null;
    }

    public String getCurrentMenuID(final Profile profile) {
        return this.getCurrentMenuID(profile.getName());
    }

    public String getCurrentMenuID(final String profile) {
        final Menu menu = this.getCurrentMenu(profile);

        return menu != null ? menu.getId() : null;
    }

    public void removeMenus(final Profile profile) {
        this.guis.remove(profile.getName());
    }

    public void removeMenus(final String profile) {
        this.guis.remove(profile);
    }

    @Override
    public void preload() {}

    @Override
    public void initialize() {}

    @Override
    public void shutdown() {
        this.guis.clear();
        this.inv_guis.clear();
    }

}

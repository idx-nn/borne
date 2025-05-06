package nn.iamj.borne.basic.gameplay.listeners;

import lombok.Getter;
import nn.iamj.borne.modules.api.events.profile.ProfileLoadEvent;
import nn.iamj.borne.modules.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public final class BasicListener implements Listener {

    @Getter
    private static Location spawnLocation;

    public BasicListener() {
        spawnLocation = new Location(Bukkit.getWorld("spawn"), 0.5, 65.0, 0.5, 90, 0);
    }

    @EventHandler
    public void onSpawn(final PlayerJoinEvent event) {
        event.getPlayer().teleportAsync(spawnLocation);
    }

    @EventHandler
    public void onLoad(final ProfileLoadEvent event) {
        if (event.isPlayedBefore()) return;

        final Profile profile = event.getProfile();
        final Player player = event.getPlayer();

        player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
        player.getInventory().addItem(new ItemStack(Material.WOODEN_PICKAXE));
        player.getInventory().addItem(new ItemStack(Material.WOODEN_AXE));
        player.getInventory().addItem(new ItemStack(Material.WOODEN_SHOVEL));
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
        if (event.isCancelled()) return;

        final Player player = event.getPlayer();

        if (player.hasPermission("borne.admin")) return;

        final ItemStack stack = event.getItemDrop().getItemStack();

        if (stack.getType() == Material.AIR) return;

        if (stack.getType().name().contains("PICKAXE")
            || stack.getType().name().contains("AXE")
            || stack.getType().name().contains("SWORD")
            || stack.getType().name().contains("SHOVEL"))
            event.setCancelled(true);
    }

}

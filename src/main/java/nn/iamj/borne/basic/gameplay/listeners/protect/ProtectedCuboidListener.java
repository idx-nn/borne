package nn.iamj.borne.basic.gameplay.listeners.protect;

import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.protect.ProtectedCuboid;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public final class ProtectedCuboidListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onChange(final EntityChangeBlockEvent event) {
        if (event.isCancelled()) return;

        final Block block = event.getBlock();

        final ProtectedCuboid cuboid = Borne.getBorne().getProtectedCuboidManager().getCuboid(block.getLocation());

        if (cuboid == null) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBurn(final BlockBurnEvent event) {
        if (event.isCancelled()) return;

        final Block block = event.getBlock();

        final ProtectedCuboid cuboid = Borne.getBorne().getProtectedCuboidManager().getCuboid(block.getLocation());

        if (cuboid == null) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onExtend(final BlockPistonExtendEvent event) {
        if (event.isCancelled()) return;

        final List<Block> blocks = event.getBlocks();
        blocks.forEach(block -> {
            final ProtectedCuboid cuboid = Borne.getBorne().getProtectedCuboidManager().getCuboid(block.getLocation());

            if (cuboid == null) return;

            if (!event.isCancelled())
                event.setCancelled(true);
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRetract(final BlockPistonRetractEvent event) {
        if (event.isCancelled()) return;

        final List<Block> blocks = event.getBlocks();
        blocks.forEach(block -> {
            final ProtectedCuboid cuboid = Borne.getBorne().getProtectedCuboidManager().getCuboid(block.getLocation());

            if (cuboid == null) return;

            if (!event.isCancelled())
                event.setCancelled(true);
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity entity = event.getEntity();
        final ProtectedCuboid cuboid = Borne.getBorne().getProtectedCuboidManager().getCuboid(entity.getLocation());

        if (cuboid == null) return;

        event.setDamage(0.0D);
        event.setCancelled(true);
    }


}

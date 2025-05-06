package nn.iamj.borne.basic.gameplay.listeners;

import nn.iamj.borne.modules.api.events.profile.action.ProfileBlockBreakEvent;
import nn.iamj.borne.modules.profile.Profile;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import nn.iamj.borne.modules.util.inventory.InventoryUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBreak(final BlockBreakEvent event) {
        if (event.isCancelled())
            return;

        event.setDropItems(false);

        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();

        final Collection<ItemStack> stack = block.getDrops(hand);

        if (stack.isEmpty() || player.getGameMode() == GameMode.CREATIVE)
            return;

        InventoryUtils.addItems(event.getPlayer(), stack);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onProfileBreak(final ProfileBlockBreakEvent event) {
        if (event.isCancelled()) return;

        final Profile profile = event.getProfile();

        if (profile.getStatistic().getBlockBreaks() % 5 != 0) return;

        final double random = ThreadLocalRandom.current().nextDouble(100.0D);
        if (random < 0.38)
            event.getProfile().getLevel().addExperience(ThreadLocalRandom.current()
                    .nextDouble(0.02, 0.12));
    }

}

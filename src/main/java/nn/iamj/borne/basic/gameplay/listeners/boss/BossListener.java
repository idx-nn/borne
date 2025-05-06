package nn.iamj.borne.basic.gameplay.listeners.boss;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.boss.Boss;

import java.util.UUID;

public final class BossListener implements Listener {

    @EventHandler
    public void onBossKill(final EntityDeathEvent event) {
        final UUID uuid = event.getEntity().getUniqueId();
        final Boss boss = Borne.getBorne().getBossManager().getEntity(uuid);

        if (boss == null) return;

        boss.kill(event.getEntity().getKiller());
    }

}

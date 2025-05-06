package nn.iamj.borne.managers.impl.addons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.boss.Boss;
import nn.iamj.borne.modules.server.scheduler.Scheduler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class BossManager implements Manager {

    private final Map<UUID, Boss> bossList = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {
        Scheduler.handleRate(() ->
                this.bossList.values().forEach(Boss::tick), 12L, 12L);
    }

    @Override
    public void shutdown() {
        this.bossList.keySet().forEach((uuid) -> {
            final Entity entity = Bukkit.getEntity(uuid);

            if (entity == null) return;

            entity.remove();
        });

        this.bossList.clear();
    }

    public void spawnBoss(final Boss entity, final Location location) {
        final UUID uuid = entity.make(location);

        this.bossList.put(uuid, entity);
        Borne.getBorne().getEntityManager()
                .registerEntity(uuid, entity);
    }

    public Boss getEntity(final UUID uuid) {
        return this.bossList.get(uuid);
    }

    public void killEntity(final UUID uuid) {
        final Entity entity = Bukkit.getEntity(uuid);

        if (entity instanceof LivingEntity living) {
            living.setHealth(0);
        }

        this.bossList.remove(uuid);
        Borne.getBorne().getEntityManager().removeEntity(uuid);
    }

}


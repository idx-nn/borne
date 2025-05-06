package nn.iamj.borne.managers.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.util.entity.CustomEntity;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityManager implements Manager {

    private final Map<UUID, CustomEntity> entityList = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {}

    @Override
    public void shutdown() {
        this.entityList.keySet().forEach((uuid) -> {
            final Entity entity = Bukkit.getEntity(uuid);

            if (entity == null) return;

            entity.remove();
        });

        this.entityList.clear();
    }

    public void spawnEntity(final CustomEntity entity, final Location location) {
        this.entityList.put(entity.make(location), entity);
    }

    public void registerEntity(final UUID uuid, final CustomEntity entity) {
        this.entityList.put(uuid, entity);
    }

    public CustomEntity getEntity(final UUID uuid) {
        return this.entityList.get(uuid);
    }

    public void killEntity(final UUID uuid) {
        final Entity entity = Bukkit.getEntity(uuid);

        if (entity instanceof LivingEntity living) {
            living.setHealth(0);
        }

        this.entityList.remove(uuid);
    }

    public void removeEntity(final UUID uuid) {
        this.entityList.remove(uuid);
    }

}

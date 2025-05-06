package nn.iamj.borne.managers.impl.addons;

import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.protect.ProtectedCuboid;
import org.bukkit.Location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProtectedCuboidManager implements Manager {

    private final Map<String, ProtectedCuboid> cuboids = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {}

    @Override
    public void shutdown() {
        this.cuboids.clear();
    }

    public void add(final String id, final ProtectedCuboid cuboid) {
        this.cuboids.put(id, cuboid);
    }

    public ProtectedCuboid getCuboid(final String id) {
        return this.cuboids.get(id);
    }

    public ProtectedCuboid getCuboid(final Location location) {
        for (final ProtectedCuboid cuboid : this.cuboids.values())
            if (cuboid.isIn(location))
                return cuboid;

        return null;
    }

    public void remove(final String id) {
        this.cuboids.remove(id);
    }

}

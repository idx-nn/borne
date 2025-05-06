package nn.iamj.borne.modules.util.blocks;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public final class Sphere {

    private Sphere() {}

    public static List<Location> generate(final Location center, final int radius, final boolean hollow) {
        final List<Location> circleBlocks = new ArrayList<>();

        int bx = center.getBlockX();
        int by = center.getBlockY();
        int bz = center.getBlockZ();

        for (int x = bx - radius; x <= bx + radius; ++x) {
            for (int y = by - radius; y <= by + radius; ++y) {
                for (int z = bz - radius; z <= bz + radius; ++z) {
                    double distance = (bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y);
                    if (distance < (double)(radius * radius) && (!hollow || distance >= (double)((radius - 1) * (radius - 1)))) {
                        circleBlocks.add(new Location(center.getWorld(), x, y, z));
                    }
                }
            }
        }

        return circleBlocks;
    }

}

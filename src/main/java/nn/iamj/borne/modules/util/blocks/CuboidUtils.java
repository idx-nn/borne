package nn.iamj.borne.modules.util.blocks;

import org.bukkit.Material;

public final class CuboidUtils {

    private CuboidUtils() {}

    public static double getFillRatio(final Cuboid cuboid) {
        final int allBlocks = cuboid.getSize();

        if (allBlocks == 0) return 100.0D;

        final long storedBlocks = cuboid.blockList().stream().filter(block -> block.getType() != Material.AIR).count();

        if (storedBlocks == 0 || allBlocks == storedBlocks) return 100.0D;

        return (double) storedBlocks / (double) allBlocks * 100.0D;
    }

}

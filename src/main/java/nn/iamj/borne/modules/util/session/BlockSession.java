package nn.iamj.borne.modules.util.session;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class BlockSession {

    public static BlockSession createSession(final World world) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null.");
        }

        return new BlockSession(world);
    }

    private final net.minecraft.server.v1_16_R3.World craftWorld;
    private final ConcurrentHashMap<BlockPosition, IBlockData> handler;

    private BlockSession(final World world) {
        this.craftWorld = ((CraftWorld) world).getHandle();

        this.handler = new ConcurrentHashMap<>();
    }

    public void write(final Material material, final Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }

        if (location.getBlockY() < 0) return;

        this.handler.put(new BlockPosition(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        ), CraftMagicNumbers.getBlock(material).getBlockData());
    }

    public void write(final Material material, final int x, final int y, final int z) {
        if (y < 0) return;

        this.handler.put(new BlockPosition(x, y, z), CraftMagicNumbers.getBlock(material).getBlockData());
    }

    public void write(final Block block) {
        if (block == null) {
            throw new IllegalArgumentException("Block cannot be null.");
        }

        final Location location = block.getLocation();

        if (location.getBlockY() < 0) return;

        this.handler.put(new BlockPosition(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        ), CraftMagicNumbers.getBlock(block.getType()).getBlockData());
    }

    public void fireAndForget() {
        final IChunkProvider chunkProvider = craftWorld.getChunkProvider();

        final Set<Chunk> chunks = new HashSet<>();
        for (Map.Entry<BlockPosition, IBlockData> entry : this.handler.entrySet()) {
            final Chunk chunk = chunkProvider.getChunkAt(entry.getKey().getX() / 16, entry.getKey().getZ() / 16, true);

            if (chunk == null) continue;

            chunks.add(chunk);

            chunk.setType(entry.getKey(), entry.getValue(), false);
        }

        if (chunks.isEmpty()) return;

        final LightEngine engine = chunkProvider.getLightEngine();

        this.handler.keySet().forEach(chunkProvider.getLightEngine()::a);

        for (final Chunk chunk : chunks) {
            final PacketPlayOutUnloadChunk unload = new PacketPlayOutUnloadChunk(chunk.getPos().x, chunk.getPos().z);

            final PacketPlayOutMapChunk load = new PacketPlayOutMapChunk(chunk, 65535);
            final PacketPlayOutLightUpdate light = new PacketPlayOutLightUpdate(chunk.getPos(), engine, true);

            for (Player nearby : Bukkit.getOnlinePlayers()) {
                final EntityPlayer player = ((CraftPlayer) nearby).getHandle();

                final int dist = Bukkit.getViewDistance() + 1;
                final int chunkX = player.chunkX;
                final int chunkZ = player.chunkZ;

                if (chunk.getPos().x < chunkX - dist ||
                        chunk.getPos().x > chunkX + dist ||
                        chunk.getPos().z < chunkZ - dist ||
                        chunk.getPos().z > chunkZ + dist) continue;

                player.playerConnection.sendPacket(unload);
                player.playerConnection.sendPacket(load);
                player.playerConnection.sendPacket(light);
            }
        }

        handler.clear();
    }

}

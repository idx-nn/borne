package nn.iamj.borne.modules.human;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import nn.iamj.borne.modules.api.events.human.HumanSpawnEvent;
import nn.iamj.borne.modules.human.assets.skin.HumanSkin;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.scheduler.Scheduler;

import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class AbstractHuman {

    private final String name;
    private final UUID uniqueId;

    @Setter
    private HumanSkin skin;

    private World minecraftWorld;
    private EntityPlayer minecraftEntity;

    public AbstractHuman(final String name) {
        this.uniqueId = UUID.randomUUID();
        this.name = name;
    }

    public int getId() {
        if (this.minecraftEntity == null)
            return -1;
        return this.minecraftEntity.getBukkitEntity().getEntityId();
    }

    public void spawnHuman(final Location location, final boolean closeLook) {
        final MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        final WorldServer world = ((CraftWorld) location.getWorld()).getHandle();

        final GameProfile profile = new GameProfile(this.uniqueId, this.name);

        final PropertyMap propertyMap = profile.getProperties();
        final Property property = new Property("textures", this.skin.getHeadTexture(), this.skin.getBodyTexture());

        propertyMap.put("textures", property);

        this.minecraftEntity = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        this.minecraftEntity.setLocation(
                location.getX(),
                location.getY(),
                location.getZ(),

                location.getYaw(),
                location.getPitch()
        );

        this.minecraftWorld = this.minecraftEntity.getWorld();

        new HumanSpawnEvent(this).callEvent();

        if (!closeLook) Scheduler.asyncHandleRate(() -> {
            final Location entityLocation = this.minecraftEntity.getBukkitEntity().getLocation();
            Bukkit.getOnlinePlayers().stream()
                    .filter(Objects::nonNull)
                    .forEach(player -> {
                        final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                        if (player.getWorld().equals(entityLocation.getWorld()) &&
                            entityLocation.distanceSquared(player.getLocation()) < Math.pow(7, 2)) {

                            final Location entityVector = entityLocation.setDirection(player.getLocation().subtract(entityLocation).toVector());
                            float yaw = entityVector.getYaw();
                            float pitch = entityVector.getPitch();

                            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(this.minecraftEntity.getId(), (byte) ((yaw % 360.) * 256 / 360), (byte) ((pitch % 360.) * 256 / 360), false));
                            connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.minecraftEntity, (byte) ((yaw % 360.) * 256 / 360)));
                        } else if (entityLocation.getYaw() != 0 && entityLocation.getYaw() != 0) {
                            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(this.minecraftEntity.getId(), (byte) ((location.getYaw() % 360.) * 256 / 360), (byte) ((location.getPitch() % 360.) * 256 / 360), false));
                            connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.minecraftEntity, (byte) ((location.getYaw() % 360.) * 256 / 360)));
                        }
                    });
        }, 6L, 6L);
    }

    public void showHuman(final Player player) {
        Scheduler.handleRate(() -> {
            final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.minecraftEntity));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.minecraftEntity));

            final Scoreboard scoreboard = player.getScoreboard();
            final Team team = scoreboard.getTeam("hidden") == null ?
                    scoreboard.registerNewTeam("hidden") :
                    scoreboard.getTeam("hidden");

            if (team == null) return;

            team.addEntry(this.minecraftEntity.getName());
            team.addEntry(player.getName());

            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);

            Scheduler.handleRate(() -> connection.sendPacket(
                    new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.minecraftEntity)),
            38L);

            final DataWatcher watcher = this.minecraftEntity.getDataWatcher();
            final byte watcherByte = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
            watcher.set(DataWatcherRegistry.a.a(17), watcherByte);

            connection.sendPacket(new PacketPlayOutEntityMetadata(this.minecraftEntity.getId(), watcher, true));
        }, 18L);
    }

    public abstract void onAttack(final Profile profile);
    public abstract void onInteract(final Profile profile);

}

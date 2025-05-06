package nn.iamj.borne.modules.wrapper;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class PacketWrapper {

    protected PacketContainer handle;

    protected PacketWrapper(PacketContainer handle) {
        if (handle == null)
            throw new IllegalArgumentException("Packet handle cannot be null.");
        this.handle = handle;
    }

    public void sendPacket(final Player receiver) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver,
                    getHandle());
        } catch (Exception ignored) {}
    }

    public void broadcastPacket() {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
    }

    public void receivePacket(final Player sender) {
        try {
            ProtocolLibrary.getProtocolManager().receiveClientPacket(sender,
                    getHandle());
        } catch (Exception ignored) {}
    }

}

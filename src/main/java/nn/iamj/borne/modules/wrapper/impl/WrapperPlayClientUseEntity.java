package nn.iamj.borne.modules.wrapper.impl;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.Getter;
import org.bukkit.util.Vector;
import nn.iamj.borne.modules.wrapper.PacketWrapper;

@Getter
public final class WrapperPlayClientUseEntity extends PacketWrapper {

    private final int targetId;
    private final EnumWrappers.EntityUseAction usageType;
    private Vector targetVector;

    public WrapperPlayClientUseEntity(final PacketContainer packet) {
        super(packet);

        this.targetId = packet.getIntegers().read(0);
        this.usageType = packet.getEntityUseActions().read(0);

        if (this.usageType != EnumWrappers.EntityUseAction.INTERACT_AT)
            return;

        this.targetVector = packet.getVectors().read(0);
    }

}

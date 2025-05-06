package nn.iamj.borne.modules.wrapper.impl;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import nn.iamj.borne.modules.wrapper.PacketWrapper;

@Getter
public final class WrapperPlayClientTabComplete extends PacketWrapper {

    private final String sequence;

    public WrapperPlayClientTabComplete(final PacketContainer packet) {
        super(packet);

        this.sequence = packet.getStrings().read(0);
    }

}

package nn.iamj.borne.basic.protocol.crashes;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.wrapper.impl.WrapperPlayClientTabComplete;

public final class TabPacketListener extends PacketAdapter {

    public TabPacketListener() {
        super(Borne.getBorne().getPlugin(), ListenerPriority.HIGHEST, PacketType.Play.Client.TAB_COMPLETE);
    }

    @Override
    public void onPacketReceiving(final PacketEvent event) {
        if (event.isPlayerTemporary()) return;

        final WrapperPlayClientTabComplete complete = new WrapperPlayClientTabComplete(event.getPacket());
        final String sequence = complete.getSequence();

        if (sequence.contains("@a[nbt=")
                || sequence.contains("while")
                || sequence.contains("targetoffset")
                || sequence.contains("for(")
                || sequence.contains("*.")
                || sequence.contains("^(.")) {
            event.setCancelled(true);
        }
    }

}

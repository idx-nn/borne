package nn.iamj.borne.modules.human.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.api.events.human.HumanUsageEvent;
import nn.iamj.borne.modules.human.Human;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.wrapper.impl.WrapperPlayClientUseEntity;

public final class HumanPacketListener extends PacketAdapter {

    public HumanPacketListener() {
        super(Borne.getBorne().getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY);
    }

    @Override
    public void onPacketReceiving(final PacketEvent event) {
        if (event.isPlayerTemporary() || event.getPlayer() == null) return;

        final WrapperPlayClientUseEntity wrapper = new WrapperPlayClientUseEntity(event.getPacket());

        final Profile profile = Borne.getBorne().getProfileManager().getProfile(event.getPlayer().getName());
        final Human human = Borne.getBorne().getHumanManager().getHuman(wrapper.getTargetId());

        if (human == null) return;

        final EnumWrappers.EntityUseAction action = wrapper.getUsageType();

        final HumanUsageEvent usageEvent = new HumanUsageEvent(profile, human, HumanUsageEvent.UsageType.fromContainer(action));

        if (!usageEvent.callEvent()) return;

        switch (action) {
            case ATTACK -> human.onAttack(profile);
            case INTERACT -> human.onInteract(profile);
        }
    }

}

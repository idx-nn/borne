package nn.iamj.borne.managers.impl;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.Manager;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class ListenerManager implements Manager {

    private PluginManager pluginManager;
    private ProtocolManager protocolManager;

    private final List<Listener> bukkitListeners = new ArrayList<>();
    private final List<PacketListener> protocolListeners = new ArrayList<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {
        this.pluginManager = Borne.getBorne().getPlugin().getServer().getPluginManager();
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void shutdown() {
        this.bukkitListeners.clear();
        this.protocolListeners.clear();
    }

    public void registerListener(final Listener listener) {
        this.pluginManager.registerEvents(listener, Borne.getBorne().getPlugin());

        this.bukkitListeners.add(listener);
    }

    public void unregisterListener(final Listener listener) {
        this.bukkitListeners.remove(listener);
    }

    public void registerListener(final PacketListener listener) {
        this.protocolManager.addPacketListener(listener);

        this.protocolListeners.add(listener);
    }

    public void unregisterListener(final PacketListener listener) {
        this.protocolManager.removePacketListener(listener);

        this.protocolListeners.remove(listener);
    }

    public int registeredListeners() {
        return this.protocolListeners.size() + this.bukkitListeners.size();
    }

}

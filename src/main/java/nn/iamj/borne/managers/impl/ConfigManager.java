package nn.iamj.borne.managers.impl;

import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.api.events.config.ConfigLoadEvent;
import nn.iamj.borne.modules.api.events.config.ConfigReloadEvent;
import nn.iamj.borne.modules.api.events.config.ConfigSaveEvent;
import nn.iamj.borne.modules.util.logger.LoggerProvider;
import nn.iamj.borne.plugin.BornePlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigManager implements Manager {

    private final Map<String, YamlConfiguration> configList = new ConcurrentHashMap<>();

    @Override @SuppressWarnings("all")
    public void preload() {
        final File directory = Borne.getBorne().getPlugin().getDataFolder();
        if (!directory.exists())
            directory.mkdirs();

        this.loadFiles("config.yml", "lang.yml", "mines.yml", "economy.yml");
    }

    @Override
    public void initialize() {}

    public void loadFiles(final Iterable<String> paths) {
        if (paths == null) return;

        for (final String path : paths)
            this.loadFile(path);
    }

    public void loadFiles(final String... paths) {
        if (paths == null) return;

        for (final String path : paths)
            this.loadFile(path);
    }

    public void loadFile(final String path) {
        if (path == null) return;

        final ConfigLoadEvent event = new ConfigLoadEvent(path);

        if (!event.callEvent()) return;

        final BornePlugin plugin = Borne.getBorne().getPlugin();
        final File file = new File(plugin.getDataFolder(), path);

        if (!file.exists()) plugin.saveResource(path, false);

        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        this.configList.put(path, configuration);
    }

    public YamlConfiguration getFile(final String path) {
        if (path == null) return null;

        return this.configList.get(path);
    }

    public void reload() {
        this.configList.forEach((string, yaml) -> {
            this.reloadFile(string);
        });
    }

    public void reloadFiles(final Iterable<String> paths) {
        if (paths == null) return;

        for (final String path : paths)
            this.reloadFile(path);
    }

    public void reloadFiles(final String... paths) {
        if (paths == null) return;

        for (final String path : paths)
            this.reloadFile(path);
    }

    public void reloadFile(final String path) {
        if (path == null || !this.configList.containsKey(path)) return;

        final ConfigReloadEvent event = new ConfigReloadEvent(path);

        if (!event.callEvent()) return;

        final BornePlugin plugin = Borne.getBorne().getPlugin();
        final File file = new File(plugin.getDataFolder(), path);

        if (!file.exists()) plugin.saveResource(path, false);

        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        this.configList.put(path, configuration);
    }

    public void saveFile(final String path, final YamlConfiguration configuration) {
        if (path == null || !this.configList.containsKey(path)) return;

        final ConfigSaveEvent event = new ConfigSaveEvent(path);

        if (!event.callEvent() || configuration == null) return;

        final BornePlugin plugin = Borne.getBorne().getPlugin();
        final File file = new File(plugin.getDataFolder(), path);

        try {
            configuration.save(file);
        } catch (final IOException exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
        }
    }

    @Override
    public void shutdown() {
        this.configList.clear();
    }

}

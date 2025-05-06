package nn.iamj.borne.managers.impl.addons;

import lombok.Getter;
import nn.iamj.borne.modules.util.arrays.impl.LocationPlusParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.managers.impl.ConfigManager;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.mine.builder.MineBuilder;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.arrays.impl.LocationParser;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class MineManager implements Manager {

    private final Map<String, Mine> mines = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {
        final ConfigManager manager = Borne.getBorne().getConfigManager();

        if (manager == null) return;

        final YamlConfiguration mainConfiguration = manager.getFile("config.yml");
        final YamlConfiguration configuration = manager.getFile("mines.yml");

        if (configuration == null) return;

        final ConfigurationSection section = configuration.getConfigurationSection("MINES");

        if (section == null) return;

        for (final String key : section.getKeys(false)) {
            final String label = configuration.getString("MINES." + key + ".LABEL", "");

            final String spawnPoint = configuration.getString("MINES." + key + ".LOCATIONS.SPAWN");
            final String hologramPoint = configuration.getString("MINES." + key + ".LOCATIONS.HOLOGRAM");

            final String areaFirstPoint = configuration.getString("MINES." + key + ".LOCATIONS.AREA.FIRST");
            final String areaSecondPoint = configuration.getString("MINES." + key + ".LOCATIONS.AREA.SECOND");

            final String mineFirstPoint = configuration.getString("MINES." + key + ".LOCATIONS.MINE.FIRST");
            final String mineSecondPoint = configuration.getString("MINES." + key + ".LOCATIONS.MINE.SECOND");

            final Location spawn = LocationPlusParser.parse(spawnPoint);
            final Location hologram = LocationParser.parse(hologramPoint);

            final Location areaFirst = LocationParser.parse(areaFirstPoint);
            final Location areaSecond = LocationParser.parse(areaSecondPoint);

            final Location mineFirst = LocationParser.parse(mineFirstPoint);
            final Location mineSecond = LocationParser.parse(mineSecondPoint);

            if (Objects.isNull(spawn)
                    || Objects.isNull(areaFirst)
                    || Objects.isNull(areaSecond)
                    || Objects.isNull(mineFirst)
                    || Objects.isNull(mineSecond)
                    || Objects.isNull(hologram))
                continue;

            final MineBuilder builder = MineBuilder.createBuilder();

            builder.setId(key);
            builder.setLabel(label);

            builder.setSpawnPoint(spawn);
            builder.setHologramPoint(hologram);

            builder.setAreaFirstPoint(areaFirst);
            builder.setAreaSecondPoint(areaSecond);

            builder.setMineFirstPoint(mineFirst);
            builder.setMineSecondPoint(mineSecond);

            final List<String> materials = configuration.getStringList("MINES." + key + ".MATERIALS");

            if (materials.isEmpty())
                continue;

            final Map<Material, Integer> map = new ConcurrentHashMap<>();

            materials.forEach(string -> {
                final String[] boxes = string.split(":");

                if (boxes.length < 1) return;

                final String material = boxes[0].toUpperCase(Locale.ROOT);

                if (!BetterNumbers.isInteger(boxes[1])) return;

                final int chance = Integer.parseInt(boxes[1]);

                try {
                    map.put(Material.valueOf(material), chance);
                } catch (final Exception ignored) {}
            });

            builder.setMaterials(map);

            final boolean allowPvP = configuration.getBoolean("MINES." + key + ".SETTINGS.ALLOW-PVP", false);
            final int minLevel = configuration.getInt("MINES." + key + ".SETTINGS.MIN-LEVEL", 1);
            final double minRatio = configuration.getDouble("MINES." + key + ".SETTINGS.MIN-RATIO", 35);
            final int cooldown = configuration.getInt("MINES." + key + ".SETTINGS.COOLDOWN", 0);
            final int priority = configuration.getInt("MINES." + key + ".SETTINGS.PRIORITY", 0);

            builder.setRatio(minRatio);
            builder.setCooldown(cooldown);
            builder.setAllowPvP(allowPvP);
            builder.setMinLevel(minLevel);
            builder.setPriority(priority);

            final Mine mine = builder.createMine();

            if (mine == null) continue;

            this.registerMine(mine);

            mine.regenerate();
        }

        Scheduler.asyncHandleRate(() -> this.mines.values().forEach(Mine::tick), 0, mainConfiguration.getInt("MINES.TICK-RATE", 120));
    }

    @Override
    public void shutdown() {
        this.mines.values().forEach(Mine::destroy);
        this.mines.clear();
    }

    public void saveMine(final Mine mine) {
        final ConfigManager manager = Borne.getBorne().getConfigManager();

        if (manager == null) return;

        final YamlConfiguration configuration = manager.getFile("mines.yml");

        configuration.set("MINES." + mine.getId().toUpperCase(), null);

        configuration.createSection("MINES." + mine.getId().toUpperCase());

        configuration.set("MINES." + mine.getId().toUpperCase() + ".LABEL", mine.getLabel());

        configuration.createSection("MINES." + mine.getId().toUpperCase() + ".LOCATIONS");

        configuration.set("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.SPAWN", LocationPlusParser.deparse(mine.getSpawnLocation()));
        configuration.set("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.HOLOGRAM", LocationParser.deparse(mine.getHologram().getLocation()));

        configuration.createSection("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.AREA");

        configuration.set("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.AREA.FIRST", LocationParser.deparse(mine.getArea().getFirstPoint()));
        configuration.set("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.AREA.SECOND", LocationParser.deparse(mine.getArea().getSecondPoint()));

        configuration.createSection("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.MINE");

        configuration.set("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.MINE.FIRST", LocationParser.deparse(mine.getMineArea().getFirstPoint()));
        configuration.set("MINES." + mine.getId().toUpperCase() + ".LOCATIONS.MINE.SECOND", LocationParser.deparse(mine.getMineArea().getSecondPoint()));

        configuration.createSection("MINES." + mine.getId().toUpperCase() + ".SETTINGS");

        configuration.set("MINES." + mine.getId().toUpperCase() + ".SETTINGS.ALLOW-PVP", mine.getSettings().isAllowPvP());
        configuration.set("MINES." + mine.getId().toUpperCase() + ".SETTINGS.MIN-LEVEL", mine.getSettings().getMinLevel());
        configuration.set("MINES." + mine.getId().toUpperCase() + ".SETTINGS.MIN-RATIO", mine.getSettings().getMinRatio());
        configuration.set("MINES." + mine.getId().toUpperCase() + ".SETTINGS.COOLDOWN", mine.getSettings().getCooldown());
        configuration.set("MINES." + mine.getId().toUpperCase() + ".SETTINGS.PRIORITY", mine.getSettings().getPriority());

        final List<String> strings = new ArrayList<>();

        for (final Map.Entry<Material, Integer> entry : mine.getMaterials().entrySet())
            strings.add(entry.getKey().name().toUpperCase(Locale.ROOT) + ":" + entry.getValue());

        configuration.set("MINES." + mine.getId().toUpperCase() + ".MATERIALS", strings);

        manager.saveFile("mines.yml", configuration);
        manager.reloadFile("mines.yml");

        mine.regenerate();
        mine.tick();
    }

    public void registerMine(final Mine mine) {
        this.mines.put(mine.getId(), mine);
    }

    public Mine getMine(final String id) {
        return this.mines.get(id);
    }

    public void unregisterMine(final String id) {
        this.mines.remove(id);
    }

    public List<Mine> getMines(final Location location) {
        return this.mines.values().stream()
                .filter(mine ->
                        mine.getArea().isIn(location))
                .toList();
    }

    public Mine getMine(final Location location) {
        return this.mines.values().stream()
                .filter(mine ->
                        mine.getArea().isIn(location))
                .findFirst()
                .orElse(null);
    }

}

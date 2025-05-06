package nn.iamj.borne.modules.storage;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.impl.ConfigManager;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class PricesStorage {
    
    private static final Map<Material, Double> prices = new ConcurrentHashMap<>();
    
    public static void loadPrices() {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("economy.yml");
        
        if (configuration == null) return;
        
        final ConfigurationSection section = configuration.getConfigurationSection("PRICES");
        
        if (section == null) return;
        
        prices.clear();
        
        for (final String key : section.getKeys(false)) {
            try {
                final Material material = Material.valueOf(key.toUpperCase(Locale.ROOT));
                final double price = configuration.getDouble("PRICES." + key, 0.0D);

                if (price == 0.0D) continue;
                
                prices.put(material, price);
            } catch (Exception ignored) { } 
        }
    }

    public static void addPrice(final Material material, final double price)  {
        prices.put(material, price);
    }
    
    public static double getPrice(final Material material) {
        return prices.getOrDefault(material, 0.0D);
    }
    
    public static void savePrices() {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("economy.yml");

        if (configuration == null) return;
        
        configuration.set("PRICES", null);
        
        configuration.createSection("PRICES");

        for (final Map.Entry<Material, Double> entry : prices.entrySet()) {
            if (entry.getValue() == 0.0D) continue;

            configuration.set("PRICES." + entry.getKey().name().toUpperCase(Locale.ROOT), entry.getValue());
        }

        final ConfigManager configManager = Borne.getBorne().getConfigManager();

        configManager.saveFile("economy.yml", configuration);
        configManager.reloadFile("economy.yml");
    }
    
}

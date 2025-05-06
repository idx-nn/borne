package nn.iamj.borne.modules.util.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import nn.iamj.borne.modules.util.entity.assets.EntityEquipment;
import nn.iamj.borne.modules.util.entity.assets.EntityReward;
import nn.iamj.borne.modules.util.entity.settings.EntitySettings;

import java.util.UUID;

@Getter
public class CustomEntity {

    private final EntityType type;

    @Setter
    private EntityEquipment equipment;
    @Setter
    private EntityReward reward;

    @Setter
    private String displayName;
    @Setter
    private EntitySettings settings;

    @Setter
    private UUID uuid;

    public CustomEntity(final EntityType type) {
        this.type = type;

        this.equipment = new EntityEquipment();
        this.reward = null;

        this.displayName = null;
        this.settings = new EntitySettings();
    }

    @SuppressWarnings("deprecation")
    public UUID make(final Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }

        final World world = location.getWorld();

        if (world == null) {
            throw new IllegalArgumentException("World cannot be null.");
        }

        final Entity rqEntity = world.spawnEntity(location, type);

        if (!(rqEntity instanceof LivingEntity entity)) {
            return rqEntity.getUniqueId();
        }

        entity.setAI(true);
        entity.setFireTicks(0);

        entity.setCustomName(this.displayName);
        entity.setCustomNameVisible(this.displayName != null);

        if (this.settings.getHealth() != 0) {
            entity.setMaxHealth(this.settings.getHealth());
            entity.setHealth(this.settings.getHealth());
        }

        if (this.settings.getSpeed() != 0) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, this.settings.getSpeed(), false, false, false));
        }

        if (equipment != null) {
            if (entity.getEquipment() == null) {
                return entity.getUniqueId();
            }

            entity.getEquipment().setHelmet(this.equipment.getHelmet());
            entity.getEquipment().setChestplate(this.equipment.getChestplate());
            entity.getEquipment().setLeggings(this.equipment.getLeggings());
            entity.getEquipment().setBoots(this.equipment.getBoots());

            entity.getEquipment().setItemInMainHand(this.equipment.getMainWeapon());
            entity.getEquipment().setItemInOffHand(this.equipment.getOffWeapon());

            entity.getEquipment().setHelmetDropChance(this.equipment.getSettings().getHelmetChance());
            entity.getEquipment().setChestplateDropChance(this.equipment.getSettings().getChestplateChance());
            entity.getEquipment().setLeggingsDropChance(this.equipment.getSettings().getLegginsChance());
            entity.getEquipment().setBootsDropChance(this.equipment.getSettings().getBootsChance());

            entity.getEquipment().setItemInMainHandDropChance(this.equipment.getSettings().getMainHandChance());
            entity.getEquipment().setItemInOffHandDropChance(this.equipment.getSettings().getOffHandChance());
        }

        this.uuid = entity.getUniqueId();

        return this.uuid;
    }

}


package nn.iamj.borne.modules.util.entity.assets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public final class EntityEquipment {

    private EquipmentSettings settings = new EquipmentSettings();

    private ItemStack mainWeapon = new ItemStack(Material.AIR);
    private ItemStack offWeapon = new ItemStack(Material.AIR);

    private ItemStack helmet = new ItemStack(Material.AIR);
    private ItemStack chestplate = new ItemStack(Material.AIR);
    private ItemStack leggings = new ItemStack(Material.AIR);
    private ItemStack boots = new ItemStack(Material.AIR);

    public ItemStack[] getArmor() {
        return new ItemStack[]{helmet, chestplate, leggings, boots};
    }

    @Getter
    public static class EquipmentSettings {

        private float helmetChance = 0;
        private float chestplateChance = 0;
        private float legginsChance = 0;
        private float bootsChance = 0;
        private float mainHandChance = 0;
        private float offHandChance = 0;
        private float inventoryDropChance = 0;

        public EquipmentSettings setHelmetChance(final float helmetChance) {
            this.helmetChance = helmetChance;

            return this;
        }

        public EquipmentSettings setChestplateChance(final float chestplateChance) {
            this.chestplateChance = chestplateChance;

            return this;
        }

        public EquipmentSettings setLegginsChance(final float legginsChance) {
            this.legginsChance = legginsChance;

            return this;
        }

        public EquipmentSettings setBootsChance(final float bootsChance) {
            this.bootsChance = bootsChance;

            return this;
        }

        public EquipmentSettings setMainHandChance(final float mainHandChance) {
            this.mainHandChance = mainHandChance;

            return this;
        }

        public EquipmentSettings setOffHandChance(final float offHandChance) {
            this.offHandChance = offHandChance;

            return this;
        }

        public EquipmentSettings setInventoryDropChance(final float inventoryDropChance) {
            this.inventoryDropChance = inventoryDropChance;

            return this;
        }
    }

}

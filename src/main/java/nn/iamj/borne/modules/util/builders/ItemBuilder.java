package nn.iamj.borne.modules.util.builders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import nn.iamj.borne.modules.server.printing.Coloriser;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.logger.LoggerProvider;

import java.lang.reflect.Field;
import java.util.*;

@Getter
public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(final ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(final Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder() {
        this.item = new ItemStack(Material.AIR);
    }


    public ItemBuilder setItem(final ItemStack item) {
        this.item = item;

        return this;
    }

    public ItemBuilder setItem(final Material material) {
        this.item = new ItemStack(material);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setDisplay(final Text text) {
        if (text == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        meta.setDisplayName(text.getRaw());
        this.item.setItemMeta(meta);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setDisplay(final String text) {
        if (text == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        meta.setDisplayName(Coloriser.colorify(text));
        this.item.setItemMeta(meta);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setLore(final List<Text> lore) {
        if (lore == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> loreList = new ArrayList<>();
        for (final Text input : lore) {
            loreList.add(input.getRaw());
        }

        meta.setLore(loreList);
        this.item.setItemMeta(meta);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setLore(final Collection<String> lore) {
        if (lore == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> loreList = new ArrayList<>();
        for (final String input : lore) {
            loreList.add(new Text(input).getRaw());
        }

        meta.setLore(loreList);
        this.item.setItemMeta(meta);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setLore(final Text... lore) {
        if (lore == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> loreList = new ArrayList<>();
        for (final Text input : lore) {
            loreList.add(input.getRaw());
        }

        meta.setLore(loreList);
        this.item.setItemMeta(meta);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setLore(final String... lore) {
        if (lore == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> loreList = new ArrayList<>();
        for (final String input : lore) {
            loreList.add(new Text(input).getRaw());
        }

        meta.setLore(loreList);
        this.item.setItemMeta(meta);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder addLore(final List<Text> loreLine) {
        if (loreLine == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> currentLore = meta.getLore();

        if (currentLore == null) return this;

        currentLore.addAll(loreLine.stream().map(Text::getRaw).toList());

        return this.setLore(currentLore);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder addLore(final Collection<String> loreLine) {
        if (loreLine == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> currentLore = meta.getLore();

        if (currentLore == null) return this;

        currentLore.addAll(loreLine);

        return this.setLore(currentLore);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder addLore(final Text... loreLine) {
        if (loreLine == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> currentLore = meta.getLore();

        if (currentLore == null) return this;

        currentLore.addAll(Arrays.stream(loreLine).map(Text::getRaw).toList());

        return this.setLore(currentLore);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder addLore(final String... loreLine) {
        if (loreLine == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> currentLore = meta.getLore();

        if (currentLore == null) return this;

        currentLore.addAll(Arrays.asList(loreLine));

        return this.setLore(currentLore);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder addLore(final Text loreLine) {
        if (loreLine == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> currentLore = meta.getLore();

        if (currentLore == null) return this;

        currentLore.add(loreLine.getRaw());

        return this.setLore(currentLore);
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder addLore(final String loreLine) {
        if (loreLine == null) return this;

        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final List<String> currentLore = meta.getLore();

        if (currentLore == null) return this;

        currentLore.add(loreLine);

        return this.setLore(currentLore);
    }

    public ItemBuilder setAmount(final int amount) {
        this.item.setAmount(amount);

        return this;
    }

    public ItemBuilder removeAttributes(final boolean removeAttributes) {
        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        if (removeAttributes) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }
        else {
            meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.removeItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.removeItemFlags(ItemFlag.HIDE_DYE);
            meta.removeItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        this.item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setEnchanted(final boolean enchanted) {
        final ItemMeta meta = item.getItemMeta();

        if (meta == null) return this;

        if (enchanted)
            this.item.addUnsafeEnchantment(Enchantment.LURE, 1);
        else item.removeEnchantment(Enchantment.LURE);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        this.item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setHeadTexture(final OfflinePlayer player) {
        if (this.item.getType() != Material.PLAYER_HEAD) return this;

        final SkullMeta meta = (SkullMeta) this.item.getItemMeta();

        if (meta == null) return this;

        meta.setOwningPlayer(player);

        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setHeadTexture(final String value) {
        final ItemMeta meta = this.item.getItemMeta();

        if (meta == null) return this;

        final GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));

        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);

            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
        }

        this.item.setItemMeta(meta);

        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setDurability(short durability) {
        if (durability < 0) {
            this.item.setDurability(this.item.getType().getMaxDurability());

            return this;
        }

        this.item.setDurability(durability);

        return this;
    }

    public ItemBuilder addPotionEffect(final PotionEffect effect) {
        if (this.item.getType() == Material.POTION
                || this.item.getType() == Material.LINGERING_POTION
                || this.item.getType() == Material.SPLASH_POTION) {
            final PotionMeta meta = (PotionMeta) this.item.getItemMeta();

            if (meta == null) return this;

            meta.addCustomEffect(effect, true);

            this.item.setItemMeta(meta);
        }

        return this;
    }

    public ItemBuilder setColor(final Color color) {
        if (this.item.getType() == Material.POTION
                || this.item.getType() == Material.LINGERING_POTION
                || this.item.getType() == Material.SPLASH_POTION) {
            final PotionMeta meta = (PotionMeta) this.item.getItemMeta();

            if (meta == null) return this;

            meta.setColor(color);

            this.item.setItemMeta(meta);

            return this;
        }

        if (this.item.getType() == Material.LEATHER_BOOTS
                || this.item.getType() == Material.LEATHER_LEGGINGS
                || this.item.getType() == Material.LEATHER_CHESTPLATE
                || this.item.getType() == Material.LEATHER_HELMET) {
            final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();

            if (meta == null) return this;

            meta.setColor(color);

            this.item.setItemMeta(meta);

            return this;
        }

        return this;
    }

    public ItemBuilder setNBTBooleanTag(final String tag, final boolean inc) {
        final NBTItem item = new NBTItem(this.item);

        item.setBoolean(tag, inc);

        this.item = item.getItem();

        return this;
    }

    public ItemBuilder setNBTStringTag(final String tag, final String key) {
        final NBTItem item = new NBTItem(this.item);

        item.setString(tag, key);

        this.item = item.getItem();

        return this;
    }

    public ItemBuilder setNBTIntegerTag(final String tag, final int inc) {
        final NBTItem item = new NBTItem(this.item);

        item.setInteger(tag, inc);

        this.item = item.getItem();

        return this;
    }

    public ItemBuilder setNBTLongTag(final String tag, final long inc) {
        final NBTItem item = new NBTItem(this.item);

        item.setLong(tag, inc);

        this.item = item.getItem();

        return this;
    }

    public ItemBuilder setNBTDoubleTag(final String tag, final double inc) {
        final NBTItem item = new NBTItem(this.item);

        item.setDouble(tag, inc);

        this.item = item.getItem();

        return this;
    }

    public ItemBuilder setNBTFloatTag(final String tag, final float inc) {
        final NBTItem item = new NBTItem(this.item);

        item.setFloat(tag, inc);

        this.item = item.getItem();

        return this;
    }

    public ItemBuilder addEnchant(final Enchantment enchantment, final int level) {
        this.item.addUnsafeEnchantment(enchantment, level);

        return this;
    }

}

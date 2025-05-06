package nn.iamj.borne.basic.menus.skills;

import nn.iamj.borne.modules.profile.assets.enums.StorageWalletType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.skill.SkillType;
import nn.iamj.borne.modules.skill.utils.SkillDisplayUtils;
import nn.iamj.borne.modules.util.arrays.BetterArrays;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.ArrayList;
import java.util.List;

public class SkillsMenu extends Menu {

    private final Profile profile;

    public SkillsMenu(final Profile profile) {
        super("skills", new Text("&8Навыки").getRaw(), 6);

        this.setInteractDisabled(true);

        this.profile = profile;

        Borne.getBorne().getMenuManager().registerMenu(this.getId(), profile.getName(), this);
    }

    public void openMenu() {
        if (this.profile == null) return;

        final Player player = this.profile.asBukkit();

        if (player == null) return;

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);

        fill.setDisplay(new Text("&8*"));

        for (int i = 0; i < 9; i++)
            this.setSlot(i, fill);
        final int size = this.getInventory().getSize();
        for (int i = size - 9; i < this.getInventory().getSize(); i++)
            this.setSlot(i, fill);

        // -------------------------------

        buildPart(SkillType.ATTACK, 1);
        buildPart(SkillType.DEFENCE, 2);
        buildPart(SkillType.EXPLOSION_DEFENCE, 3);
        buildPart(SkillType.TEMPERATURE_DEFENCE, 4);

        // -------------------------------

        final MenuSlot exit = new MenuSlot(Material.IRON_BARS);

        exit.setDisplay(new Text("&x&1&1&f&f&1&1Выйти из меню.."));
        exit.setExecutableClick(new ExecutableClick()  {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();
                if (player == null)
                    return;

                player.closeInventory();
            }
        });

        setSlot(size - 5, exit);

        player.openInventory(this.getInventory());
    }

    private void buildPart(final SkillType type, final int row) {
        final MenuSlot preview = new MenuSlot(SkillDisplayUtils.getIcon(type));

        preview.setDisplay(new Text("&7Навык: &e" + SkillDisplayUtils.getDisplay(type)));
        preview.removeAttributes(true);

        final int slot = row * 9 + 1;

        this.setSlot(slot, preview);

        // -------------------------------------------------

        if (this.profile == null) return;

        final int modifier = this.profile.getStorage().getSkill(type);

        for (int i = 1; i <= modifier; i++)
            this.setSlot(i + slot + 1, buildSoldSlot(type, i));
        if (modifier < 5)
            this.setSlot(slot + 2 + modifier, buildSlot(type, modifier + 1));
        for (int i = modifier + 2; i <= 5; i++)
            this.setSlot(i + slot + 1, buildClosedSlot(type, i));
    }

    private MenuSlot buildSoldSlot(final SkillType type, final int level) {
        final MenuSlot slot = new MenuSlot(Material.GREEN_STAINED_GLASS_PANE);

        slot.setDisplay(new Text("&x&1&1&f&f&1&1Уровень " + level + " (Прокачено)"));

        final List<Text> lore = new ArrayList<>();

        lore.add(new Text());
        lore.add(new Text(" &x&1&1&f&f&1&1При прокачке навыка: "));

        switch (type) {
            case ATTACK -> lore.add(new Text(" &x&1&1&f&f&1&1- &7Урон по сущностям: &e+" + (BetterNumbers.floor(3, 4.3 * level)) + "%  "));
            case DEFENCE -> lore.add(new Text(" &x&1&1&f&f&1&1- &7Защита от физических атак: &e+" + (BetterNumbers.floor(3,7.8 * level) + "%  ")));
            case EXPLOSION_DEFENCE -> lore.add(new Text(" &x&1&1&f&f&1&1- &7Защита от взрывов: &e+" + (BetterNumbers.floor(3,13.6 * level) + "%  ")));
            case TEMPERATURE_DEFENCE -> lore.add(new Text(" &x&1&1&f&f&1&1- &7Защита от огня и холода: &e+" + (BetterNumbers.floor(3,12.8 * level) + "%  ")));
            case POISON_DEFENCE -> lore.add(new Text(" &x&1&1&f&f&1&1- &7Защита от ядов: &e+" + (BetterNumbers.floor(3,22.0 * level) + "%  ")));
        }

        lore.add(new Text());

        slot.setLore(lore);

        return slot;
    }

    private MenuSlot buildSlot(final SkillType type, final int level) {
        final MenuSlot slot = new MenuSlot(Material.ORANGE_STAINED_GLASS_PANE);

        slot.setDisplay(new Text(Component.ORANGE + "Уровень " + level));

        final List<Text> lore = new ArrayList<>();

        lore.add(new Text());
        lore.add(new Text(Component.ORANGE + " При прокачке навыка: "));

        switch (type) {
            case ATTACK -> lore.add(new Text(Component.ORANGE + " - &7Урон по сущностям: &e+" + (BetterNumbers.floor(3, 4.3 * level)) + "%  "));
            case DEFENCE -> lore.add(new Text(Component.ORANGE + " - &7Защита от физических атак: &e+" + (BetterNumbers.floor(3,7.8 * level) + "%  ")));
            case EXPLOSION_DEFENCE -> lore.add(new Text(Component.ORANGE + " - &7Защита от взрывов: &e+" + (BetterNumbers.floor(3,13.6 * level) + "%  ")));
            case TEMPERATURE_DEFENCE -> lore.add(new Text(Component.ORANGE + " - &7Защита от огня и холода: &e+" + (BetterNumbers.floor(3,12.8 * level) + "%  ")));
            case POISON_DEFENCE -> lore.add(new Text(Component.ORANGE + " - &7Защита от ядов: &e+" + (BetterNumbers.floor(3,22.0 * level) + "%  ")));
        }

        lore.add(new Text());
        lore.add(new Text(Component.ORANGE + " Для прокачки требуется: "));

        final int skillsSum = BetterArrays.getSumInt(profile.getStorage().getSkills().values());

        if (skillsSum < 4) lore.add(new Text("&7 - &71x Обычная монета босса"));
        else if (skillsSum < 8) lore.add(new Text("&7 - &91x Редкая монета босса"));
        else if (skillsSum < 12) lore.add(new Text("&7 - &51x Эпическая монета босса"));
        else lore.add(new Text("&7 - &e1x Легендарная монета босса"));

        final double sum = SkillDisplayUtils.getSellRatio(type) * level * 980;
        lore.add(new Text("&7 - &e" + BetterNumbers.floor(2, sum) + " 혤"));

        lore.add(new Text());
        lore.add(new Text("&8» " + Component.ORANGE + "Нажмите для прокачки навыка."));

        slot.setLore(lore);

        slot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final int skillsSum = BetterArrays.getSumInt(profile.getStorage().getSkills().values());

                if (profile.getWallet().getMoney() < sum) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас нету денег, для повышения навыка."));
                    return;
                }

                if (skillsSum < 4) {
                    if (profile.getStorage().getWallet(StorageWalletType.COMMON_BOSS) < 1) {
                        profile.sendText(Component.text(Component.Type.ERROR, "У Вас нету монеты босса, для повышения навыка."));
                        return;
                    } else profile.getStorage().removeWallet(StorageWalletType.COMMON_BOSS, 1);
                }
                else if (skillsSum < 8) {
                    if (profile.getStorage().getWallet(StorageWalletType.RARE_BOSS) < 1) {
                        profile.sendText(Component.text(Component.Type.ERROR, "У Вас нету монеты босса, для повышения навыка."));
                        return;
                    } else profile.getStorage().removeWallet(StorageWalletType.RARE_BOSS, 1);
                }
                else if (skillsSum < 12) {
                    if (profile.getStorage().getWallet(StorageWalletType.EPIC_BOSS) < 1) {
                        profile.sendText(Component.text(Component.Type.ERROR, "У Вас нету монеты босса, для повышения навыка."));
                        return;
                    } else profile.getStorage().removeWallet(StorageWalletType.EPIC_BOSS, 1);
                }
                else if (profile.getStorage().getWallet(StorageWalletType.LEGENDARY_BOSS) < 1) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас нету монеты босса, для повышения навыка."));
                    return;
                } else profile.getStorage().removeWallet(StorageWalletType.LEGENDARY_BOSS, 1);

                profile.getWallet().removeMoney(sum);
                profile.getStorage().upgradeSkill(type);

                profile.save(true);

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно прокачали навык!"));

                (new SkillsMenu(profile)).openMenu();
            }
        });

        return slot;
    }

    private MenuSlot buildClosedSlot(final SkillType type, final int level) {
        final MenuSlot slot = new MenuSlot(Material.RED_STAINED_GLASS_PANE);

        slot.setDisplay(new Text("&x&f&f&1&1&1&1Уровень " + level));

        final List<Text> lore = new ArrayList<>();

        lore.add(new Text());
        lore.add(new Text(" &x&f&f&1&1&1&1При прокачке навыка: "));

        switch (type) {
            case ATTACK -> lore.add(new Text(" &x&f&f&1&1&1&1- &7Урон по сущностям: &e+" + (BetterNumbers.floor(3, 4.3 * level)) + "%  "));
            case DEFENCE -> lore.add(new Text(" &x&f&f&1&1&1&1- &7Защита от физических атак: &e+" + (BetterNumbers.floor(3,7.8 * level) + "%  ")));
            case EXPLOSION_DEFENCE -> lore.add(new Text(" &x&f&f&1&1&1&1- &7Защита от взрывов: &e+" + (BetterNumbers.floor(3,13.6 * level) + "%  ")));
            case TEMPERATURE_DEFENCE -> lore.add(new Text(" &x&f&f&1&1&1&1- &7Защита от огня и холода: &e+" + (BetterNumbers.floor(3,12.8 * level) + "%  ")));
            case POISON_DEFENCE -> lore.add(new Text(" &x&f&f&1&1&1&1- &7Защита от ядов: &e+" + (BetterNumbers.floor(3,22.0 * level) + "%  ")));
        }

        lore.add(new Text());

        slot.setLore(lore);

        return slot;
    }

}

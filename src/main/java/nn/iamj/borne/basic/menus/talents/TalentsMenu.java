package nn.iamj.borne.basic.menus.talents;

import nn.iamj.borne.modules.profile.assets.enums.StorageWalletType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.skill.SkillType;
import nn.iamj.borne.modules.skill.utils.SkillDisplayUtils;
import nn.iamj.borne.modules.talent.TalentType;
import nn.iamj.borne.modules.talent.utils.TalentDisplayUtils;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TalentsMenu extends Menu {

    private final Profile profile;

    public TalentsMenu(final Profile profile) {
        super("talents", new Text("&8Способности").getRaw(), 6);

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

        buildPart(TalentType.REGEN, 1);
        buildPart(TalentType.FIRE, 2);
        buildPart(TalentType.POISON, 3);
        buildPart(TalentType.SHINOBI, 4);

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

    private void buildPart(final TalentType type, final int row) {
        final MenuSlot preview = new MenuSlot(TalentDisplayUtils.getIcon(type));

        preview.setDisplay(new Text("&7Способность: &e" + TalentDisplayUtils.getDisplay(type)));

        final List<String> description = TalentDisplayUtils.getDescription(type);

        if (description == null)
            return;

        final LinkedList<String> originalLore = new LinkedList<>(description);

        originalLore.addFirst("");
        originalLore.addLast("");

        final List<Text> lore = originalLore.stream()
                .map(string -> new Text(" &7" + string + "  "))
                .toList();

        preview.setLore(lore);

        preview.removeAttributes(true);

        final int slot = row * 9 + 1;

        this.setSlot(slot, preview);

        // -------------------------------------------------

        if (this.profile == null) return;

        final int modifier = this.profile.getStorage().getTalent(type);

        for (int i = 1; i <= modifier; i++)
            this.setSlot(i + slot + 1, buildSoldSlot(type, i));
        if (modifier < 5)
            this.setSlot(slot + 2 + modifier, buildSlot(type, modifier + 1));
        for (int i = modifier + 2; i <= 5; i++)
            this.setSlot(i + slot + 1, buildClosedSlot(type, i));
    }

    private MenuSlot buildSoldSlot(final TalentType type, final int level) {
        final MenuSlot slot = new MenuSlot(Material.GREEN_STAINED_GLASS_PANE);

        slot.setDisplay(new Text("&x&1&1&f&f&1&1Уровень " + level + " (Прокачено)"));

        final List<Text> lore = new ArrayList<>();

        lore.add(new Text());
        lore.add(new Text(" &x&1&1&f&f&1&1При прокачке способности: "));

        switch (type) {
            case REGEN -> lore.add(new Text(" &x&1&1&f&f&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3, Math.abs(Math.sqrt(level) * 3.95 / (9 - level)))) + "%  "));
            case FIRE -> {
                lore.add(new Text(" &x&1&1&f&f&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 1.49 / (85 - level))) + "%  ")));
                lore.add(new Text(" &x&1&1&f&f&1&1- &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 12.0D * level / 20.0D)) + " сек. "));
            }
            case POISON -> {
                lore.add(new Text(" &x&1&1&f&f&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 1.55 / (63 - level))) + "%  ")));
                lore.add(new Text(" &x&1&1&f&f&1&1- &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 55.0D * level / 20.0D)) + " сек. "));
            }
            case SHINOBI -> {
                lore.add(new Text(" &x&1&1&f&f&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 2.45 / (59 - level))) + "%  ")));
                lore.add(new Text(" &x&1&1&f&f&1&1- &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 12.0D * level / 20.0D)) + " сек. "));
            }
            case VAMPIRE -> {
                lore.add(new Text(" &x&1&1&f&f&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.sqrt(level) * 2.45 / (14 - level)) + "%  ")));
                lore.add(new Text(" &x&1&1&f&f&1&1- &7За раз макс.: &e" + (BetterNumbers.floor(2, (0.75 * level/2))) + " сердец "));
            }
        }

        lore.add(new Text());

        slot.setLore(lore);

        return slot;
    }

    private MenuSlot buildSlot(final TalentType type, final int level) {
        final MenuSlot slot = new MenuSlot(Material.ORANGE_STAINED_GLASS_PANE);

        slot.setDisplay(new Text(Component.ORANGE + "Уровень " + level));

        final List<Text> lore = new ArrayList<>();

        lore.add(new Text());
        lore.add(new Text(Component.ORANGE + " При прокачке способности: "));

        switch (type) {
            case REGEN -> lore.add(new Text(Component.ORANGE +" - &7Шанс на активацию: &e" + (BetterNumbers.floor(3, Math.abs(Math.sqrt(level) * 3.95 / (9 - level)))) + "%  "));
            case FIRE -> {
                lore.add(new Text(Component.ORANGE + " - &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 1.49 / (85 - level))) + "%  ")));
                lore.add(new Text(Component.ORANGE + " - &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 12.0D * level / 20.0D)) + " сек. "));
            }
            case POISON -> {
                lore.add(new Text(Component.ORANGE + " - &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 1.55 / (63 - level))) + "%  ")));
                lore.add(new Text(Component.ORANGE + " - &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 55.0D * level / 20.0D)) + " сек. "));
            }
            case SHINOBI -> {
                lore.add(new Text(Component.ORANGE + " - &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 2.45 / (59 - level))) + "%  ")));
                lore.add(new Text(Component.ORANGE + " - &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 12.0D * level / 20.0D)) + " сек. "));
            }
            case VAMPIRE -> {
                lore.add(new Text(Component.ORANGE + " - &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.sqrt(level) * 2.45 / (14 - level)) + "%  ")));
                lore.add(new Text(Component.ORANGE + " - &7За раз макс.: &e" + (BetterNumbers.floor(2, (0.75 * level/2))) + " сердец "));
            }
        }

        lore.add(new Text());
        lore.add(new Text(Component.ORANGE + " Для прокачки требуется: "));
        lore.add(new Text("&7 - &d10x Кристаллов энергии"));
        final double expSum = TalentDisplayUtils.getSellRatio(type) * level * 60;
        lore.add(new Text("&7 - &a" + BetterNumbers.floor(2, expSum) + " &f혡"));
        final double sum = TalentDisplayUtils.getSellRatio(type) * level * 2560;
        lore.add(new Text("&7 - &e" + BetterNumbers.floor(2, sum) + " &f혤"));

        lore.add(new Text());
        lore.add(new Text("&8» " + Component.ORANGE + "Нажмите для прокачки навыка."));

        slot.setLore(lore);

        slot.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                if (profile.getStorage().getWallet(StorageWalletType.ENERGY_CRYSTALS) < 10
                    || profile.getWallet().getMoney() < sum
                    || profile.getLevel().getExperience() < expSum) {
                    profile.sendText(Component.text(Component.Type.ERROR, "Вам не хватает ресурсов, для того чтобы прокачать эту способность."));
                    return;
                }

                profile.getStorage().removeWallet(StorageWalletType.ENERGY_CRYSTALS, 10);
                profile.getWallet().removeMoney(sum);
                profile.getLevel().removeExperience(expSum);

                profile.getStorage().upgradeTalent(type);

                profile.save(true);

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно прокачали эту способность."));

                (new TalentsMenu(profile)).openMenu();
            }
        });

        return slot;
    }

    private MenuSlot buildClosedSlot(final TalentType type, final int level) {
        final MenuSlot slot = new MenuSlot(Material.RED_STAINED_GLASS_PANE);

        slot.setDisplay(new Text("&x&f&f&1&1&1&1Уровень " + level));

        final List<Text> lore = new ArrayList<>();

        lore.add(new Text());
        lore.add(new Text(" &x&f&f&1&1&1&1При прокачке способности: "));

        switch (type) {
            case REGEN -> lore.add(new Text(" &x&f&f&1&1&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3, Math.abs(Math.sqrt(level) * 3.95 / (9 - level)))) + "%  "));
            case FIRE -> {
                lore.add(new Text(" &x&f&f&1&1&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 1.49 / (85 - level))) + "%  ")));
                lore.add(new Text(" &x&f&f&1&1&1&1- &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 12.0D * level / 20.0D)) + " сек. "));
            }
            case POISON -> {
                lore.add(new Text(" &x&f&f&1&1&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 1.55 / (63 - level))) + "%  ")));
                lore.add(new Text(" &x&f&f&1&1&1&1- &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 55.0D * level / 20.0D)) + " сек. "));
            }
            case SHINOBI -> {
                lore.add(new Text(" &x&f&f&1&1&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.abs(Math.sqrt(level) * 2.45 / (59 - level))) + "%  ")));
                lore.add(new Text(" &x&f&f&1&1&1&1- &7Длительность эффекта: &e" + (BetterNumbers.floor(2, 12.0D * level / 20.0D)) + " сек. "));
            }
            case VAMPIRE -> {
                lore.add(new Text(" &x&f&f&1&1&1&1- &7Шанс на активацию: &e" + (BetterNumbers.floor(3,Math.sqrt(level) * 2.45 / (14 - level)) + "%  ")));
                lore.add(new Text(" &x&f&f&1&1&1&1- &7За раз макс.: &e" + (BetterNumbers.floor(2, (0.75 * level/2))) + " сердец "));
            }
        }

        lore.add(new Text());

        slot.setLore(lore);

        return slot;
    }

}

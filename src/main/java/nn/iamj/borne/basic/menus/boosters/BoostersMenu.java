package nn.iamj.borne.basic.menus.boosters;

import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.booster.Booster;
import nn.iamj.borne.modules.booster.BoosterStorage;
import nn.iamj.borne.modules.commerce.menu.CommerceMenu;
import nn.iamj.borne.modules.menu.Menu;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.collection.pair.Pair;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.logger.LoggerProvider;
import nn.iamj.borne.modules.util.math.FormatTimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class BoostersMenu extends Menu {

    private final Profile profile;
    private int page;

    public BoostersMenu(final @NotNull Profile profile) {
        super("boosters", new Text("&8Бустеры").getRaw(), 6);

        this.profile = profile;
        this.page = 1;

        Borne.getBorne().getMenuManager().registerMenu(this.getId(), profile.getName(), this);
    }

    public void open() {
        if (this.profile == null) return;

        this.clean();

        final MenuSlot fill = new MenuSlot(Material.GRAY_STAINED_GLASS_PANE);
        for (int i = 0; i < 9; i++)
            this.setSlot(i, fill);
        for (int i = 45; i < 54; i++)
            this.setSlot(i, fill);

        final List<Booster> boosterList = this.profile.getStorage().getBoosters();
        final int pages = (int) (Math.ceil(boosterList.size() / 36.0D));
        final int startIndex = ((page - 1) * 36);

        final Player player = profile.asBukkit();

        if (player == null) return;

        if (boosterList.isEmpty()) {
            new CommerceMenu(player, Borne.getBorne().getCommerceManager().getCommerce("boosters")).open();
            return;
        }

        for (int i = startIndex; i < boosterList.size(); i++) {
            try {
                if (this.getInventory().firstEmpty() == -1)
                    continue;

                final Booster booster = boosterList.get(i);

                final MenuSlot bslot = new MenuSlot(Material.PLAYER_HEAD);
                bslot.setHeadTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjliZjg4NWY1MTM3YTliZDhjZTQzYTkxYzVkMGI1ZGU5YjMyNGEzN2YxNGUxNWVlY2IzYmJjZmIxNjJhOWViIn19fQ==");
                bslot.setDisplay(new Text("&6Глобальный бустер"));
                bslot.setLore(
                        new Text(),
                        new Text(" &7При активации умножает доход всем до "),
                        new Text(" &7тех пор, пока время бустера не истечет. "),
                        new Text(),
                        new Text(" &fМножитель: &ex" + booster.getModifier() + "  "),
                        new Text(" &fВремя действия: &e" + FormatTimeUtils.formatTimeNoSecondsADV(booster.getSeconds()) + " "),
                        new Text(),
                        new Text("&8» &eНажмите для активации..")
                );

                bslot.setExecutableClick(new ExecutableClick() {
                    @Override
                    public void onLeft(final Profile profile) {
                        final Player player = profile.asBukkit();

                        if (player == null) return;

                        final BoosterStorage storage = BoosterStorage.getInstance();

                        if (storage == null) return;

                        final Pair<String, Booster> boosterPair = storage.getActiveBooster();

                        if (boosterPair != null) {
                            profile.sendText(Component.text(Component.Type.ERROR, "К сожалению, сейчас работает другой бустер."));
                            return;
                        }

                        storage.activeBooster(profile.getName(), booster);

                        profile.getStorage().removeBooster(booster);
                        profile.save(true);

                        player.closeInventory();
                    }
                });

                this.setSlot(this.getInventory().firstEmpty(), bslot);
            } catch (final ArrayIndexOutOfBoundsException exception) {
                LoggerProvider.getInstance().error(exception);
            }
        }

        final MenuSlot exit = new MenuSlot(Material.IRON_BARS);
        exit.setDisplay(new Text(Component.RED + "Выйти"));
        exit.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();

                if (player == null) return;

                player.closeInventory();
            }
        });

        this.setSlot(49, exit);

        final MenuSlot shop = new MenuSlot(Material.PLAYER_HEAD);
        shop.setDisplay(new Text(Component.ORANGE + "Магазин бустеров.."));
        shop.setHeadTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc2MmExNWIwNDY5MmEyZTRiM2ZiMzY2M2JkNGI3ODQzNGRjZTE3MzJiOGViMWM3YTlmN2MwZmJmNmYifX19");
        shop.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final Profile profile) {
                final Player player = profile.asBukkit();

                if (player == null) return;

                new CommerceMenu(player, Borne.getBorne().getCommerceManager().getCommerce("boosters")).open();
            }
        });
        this.setSlot(51, shop);

        if (pages > 1 && pages != page) {
            final MenuSlot next = new MenuSlot(Material.ARROW);

            next.setDisplay(new Text("&eСледующая страница &8»"));
            next.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    page++;
                    open();
                }
            });

            this.setSlot(53, next);
        }

        if (pages > 1 && page != 1) {
            final MenuSlot prev = new MenuSlot(Material.ARROW);

            prev.setDisplay(new Text("&8« &eПредыдущая страница"));
            prev.setExecutableClick(new ExecutableClick() {
                @Override
                public void onLeft(final Profile profile) {
                    page--;
                    open();
                }
            });

            this.setSlot(45, prev);
        }

        player.openInventory(this.getInventory());
    }

}

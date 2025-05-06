package nn.iamj.borne.basic.commerce;

import nn.iamj.borne.modules.booster.Booster;
import nn.iamj.borne.modules.commerce.Commerce;
import nn.iamj.borne.modules.commerce.page.CommercePage;
import nn.iamj.borne.modules.commerce.unit.CommerceSlot;
import nn.iamj.borne.modules.commerce.unit.CommerceUnit;
import nn.iamj.borne.modules.commerce.wallet.CommercePrice;
import nn.iamj.borne.modules.commerce.wallet.CommerceWallet;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.builders.ItemBuilder;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.math.FormatTimeUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class BoostersCommerce implements Commerce {

    private final List<CommercePage> pages = new LinkedList<>();

    public BoostersCommerce() {
        final CommercePage page = new CommercePage();

        page.addUnit(booster(20, 1.2, 5 * 60));
        page.addUnit(booster(21, 1.2, 20 * 60));
        page.addUnit(booster(22, 1.5, 10 * 60));
        page.addUnit(booster(23, 1.5, 30 * 60));
        page.addUnit(booster(24, 1.8, 15 * 60));
        page.addUnit(booster(29, 1.8, 35 * 60));
        page.addUnit(booster(30, 2.2, 20 * 60));
        page.addUnit(booster(31, 2.2, 40 * 60));
        page.addUnit(booster(32, 2.5, 30 * 60));
        page.addUnit(booster(33, 2.5, 60 * 60));

        this.addPage(page);
    }

    private CommerceSlot booster(final int slotm, final double modifier, final int sec) {
        final CommerceSlot slot = new CommerceSlot();

        final ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);

        builder.setHeadTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjliZjg4NWY1MTM3YTliZDhjZTQzYTkxYzVkMGI1ZGU5YjMyNGEzN2YxNGUxNWVlY2IzYmJjZmIxNjJhOWViIn19fQ==");
        builder.setDisplay(new Text("&6Глобальный бустер"));
        builder.setLore(
                new Text(),
                new Text(" &7При активации умножает доход всем до "),
                new Text(" &7тех пор, пока время бустера не истечет. "),
                new Text(),
                new Text(" &fМножитель: &ex" + modifier + "  "),
                new Text(" &fВремя действия: &e" + FormatTimeUtils.formatTimeNoSecondsADV(sec) + " "),
                new Text()
        );

        slot.setDisplay(builder.getItem());

        final CommercePrice commercePrice = new CommercePrice();

        commercePrice.addPrice(CommerceWallet.DONATED, (int) (4 * (modifier * 6) * ((int) (sec / 300.0D))));

        slot.setPrice(commercePrice);
        slot.setSlot(slotm);
        slot.setRunnable(new CommerceUnit.CommerceExecutable() {
            @Override
            public void onBuy(Profile profile) {
                final Profile wakeuped = profile.wakeup();

                if (wakeuped == null) return;

                wakeuped.getStorage().addBooster(new Booster(modifier, sec));
                wakeuped.save(true);

                wakeuped.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно купили этот товар.. Приходите еще!"));
            }
        });

        return slot;
    }

    @Override
    public void addPage(CommercePage page) {
        this.pages.add(page);
    }

    @Override
    public CommercePage getPage(int i) {
        return this.pages.get(i - 1);
    }

    @Override
    public void removePage(CommercePage page) {
        this.pages.remove(page);
    }

    @Override
    public List<CommercePage> getPages() {
        return this.pages;
    }

    @Override
    public void clearPages() {
        this.pages.clear();
    }

}

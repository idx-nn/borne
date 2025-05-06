package nn.iamj.borne.modules.boss.bar;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.boss.Boss;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.UUID;

public final class BossBar {

    @Getter
    private final Boss boss;
    private final org.bukkit.boss.BossBar bossBar;

    public BossBar(final Boss boss) {
        this.boss = boss;
        this.bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);

        this.bossBar.setVisible(false);
        this.bossBar.setProgress(0.0F);
    }

    public void setTitle(final Text text) {
        this.bossBar.setTitle(text.getRaw());
    }

    public void setStyle(final BarStyle style) {
        this.bossBar.setStyle(style);
    }

    public void setColor(final BarColor color) {
        this.bossBar.setColor(color);
    }

    @SuppressWarnings("deprecation")
    public void tick() {
        if (!this.bossBar.isVisible())
            this.bossBar.setVisible(true);
        this.bossBar.removeAll();

        final UUID uuid = this.boss.getUuid();

        if (uuid == null) return;

        final Entity entity = Bukkit.getEntity(uuid);

        if (!(entity instanceof LivingEntity living)) return;

        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null) return;

        this.setTitle(new Text(configuration.getString("BOSS.HUD", "")
                .replace("{BOSS_NAME}", this.boss.getDisplayName())
                .replace("{BOSS_HEALTH}", BetterNumbers.floor(1, living.getHealth()) + "")
                .replace("{BOSS_MAX_HEALTH}", BetterNumbers.floor(1, living.getMaxHealth()) + "")));
        this.bossBar.setProgress(living.getHealth() / Math.max(living.getMaxHealth(), 1.0D));

        living.getWorld().getNearbyEntities(living.getLocation(),
                        50.0D, 50.0D, 50.0D).stream()
                .filter(fentity -> fentity.getType() == EntityType.PLAYER)
                .map(fentity -> (Player) fentity)
                .forEach(this.bossBar::addPlayer);
    }

    public void end() {
        this.bossBar.removeAll();

        this.bossBar.setVisible(false);
        this.bossBar.setProgress(0.0F);
    }

}

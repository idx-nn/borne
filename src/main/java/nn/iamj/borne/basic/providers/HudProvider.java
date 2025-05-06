package nn.iamj.borne.basic.providers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.api.events.profile.ProfileLoadEvent;
import nn.iamj.borne.modules.api.events.profile.hud.ProfileHudUpdateEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.collection.pair.Pair;
import nn.iamj.borne.modules.util.event.EventUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class HudProvider implements Listener {

    private static final Map<String, Pair<BossBar, BukkitTask>> hudList = new ConcurrentHashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLoad(final ProfileLoadEvent event) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null) {
            throw new IllegalStateException("Configuration cannot be null.");
        }

        final Profile profile = event.getProfile();

        if (hudList.containsKey(event.getPlayer().getName())) return;

        final String hudTitle = new Text(configuration.getString("HUD", "")
                .replace("{LEVEL}", profile.getLevel().getLevel() + "")
                .replace("{EXP}", String.format("%.2f", profile.getLevel().getExperience()))
                .replace("{DONATED}", String.format("%.2f", profile.getWallet().getDonated()))
                .replace("{MONEY}", String.format("%.2f", profile.getWallet().getMoney()))).getRaw();
        final BossBar bar = Bukkit.createBossBar(hudTitle, BarColor.WHITE, BarStyle.SOLID);

        bar.setVisible(true);
        bar.setProgress(1.0f);

        bar.addPlayer(event.getPlayer());

        final BukkitTask task = Scheduler.asyncHandleRate(() ->
                updateHud(profile.wakeup()),
        300L, 300L);

        hudList.put(event.getProfile().getName(), new Pair<>(bar, task));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDisconnect(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (!hudList.containsKey(player.getName())) return;

        final Pair<BossBar, BukkitTask> pair = hudList.get(player.getName());

        final BossBar bar = pair.getKey();
        final BukkitTask task = pair.getValue();

        bar.setVisible(false);
        bar.removeAll();

        task.cancel();

        hudList.remove(player.getName());
    }

    public static void updateHud(final Profile originProfile) {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null || originProfile == null)
            return;

        final Pair<BossBar, BukkitTask> pair = hudList.get(originProfile.getName());

        if (pair == null)
            return;

        final BossBar originBar = pair.getKey();

        if (originBar == null)
            return;

        final boolean result = EventUtils.callEvent(new ProfileHudUpdateEvent(originProfile));

        if (!result)
            return;

        originBar.setTitle(new Text(configuration.getString("HUD", "")
                .replace("{LEVEL}", originProfile.getLevel().getLevel() + "")
                .replace("{EXP}", String.format("%.2f", originProfile.getLevel().getExperience()))
                .replace("{DONATED}", String.format("%.2f", originProfile.getWallet().getDonated()))
                .replace("{MONEY}", String.format("%.2f", originProfile.getWallet().getMoney()))).getRaw());
    }

}

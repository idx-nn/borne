package nn.iamj.borne.modules.booster.listeners;

import lombok.Getter;
import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.commands.game.boosters.ThanksCommand;
import nn.iamj.borne.modules.api.events.booster.BoosterActiveEvent;
import nn.iamj.borne.modules.api.events.booster.BoosterEndEvent;
import nn.iamj.borne.modules.api.events.profile.ProfileLoadEvent;
import nn.iamj.borne.modules.api.events.profile.hud.ProfileHudUpdateEvent;
import nn.iamj.borne.modules.booster.Booster;
import nn.iamj.borne.modules.booster.BoosterStorage;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.addons.messenger.Messenger;
import nn.iamj.borne.modules.util.collection.pair.Pair;
import nn.iamj.borne.modules.util.event.EventUtils;
import nn.iamj.borne.modules.util.math.FormatTimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BoosterListener implements Listener {

    @Getter
    private static final Map<String, Pair<BossBar, BukkitTask>> boosterList = new ConcurrentHashMap<>();
    private boolean active;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLoad(final ProfileLoadEvent event) {
        final BoosterStorage storage = BoosterStorage.getInstance();

        if (!this.active && storage != null && storage.getStartAt() != 0L)
            this.active = true;

        if (!this.active) return;

        this.handlePlayer(event.getProfile());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDisconnect(final PlayerQuitEvent event) {
        this.inspectPlayer(event.getPlayer());
    }

    private void handlePlayer(final Profile profile) {
        if (profile == null) return;

        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null) {
            throw new IllegalStateException("Configuration cannot be null.");
        }

        if (boosterList.containsKey(profile.getName())) return;

        final BoosterStorage storage = BoosterStorage.getInstance();

        if (storage == null) return;

        final Pair<String, Booster> active = storage.getActiveBooster();

        if (active == null) return;

        final int seconds_keep = (int)((storage.getStartAt() + (active.getValue().getSeconds() * 1000) - System.currentTimeMillis()) / 1000L);
        final String hudTitle = new Text(configuration.getString("BOOSTER", "")
                .replace("{AUTHOR}", active.getKey())
                .replace("{MODIFIER}", active.getValue().getModifier() + "")
                .replace("{SECONDS_KEEP}", FormatTimeUtils.formatTimeNoSecondsADV(seconds_keep))
        ).getRaw();
        final BossBar bar = Bukkit.createBossBar(hudTitle, BarColor.YELLOW, BarStyle.SOLID);

        bar.setVisible(true);
        bar.setProgress((double) seconds_keep / active.getValue().getSeconds());

        final Player player = profile.asBukkit();

        if (player == null) return;

        bar.addPlayer(player);

        final BukkitTask task = Scheduler.asyncHandleRate(() ->
                        updateHud(profile.wakeup()),
                18L, 18L);

        boosterList.put(profile.getName(), new Pair<>(bar, task));
    }

    private void updateHud(final Profile originProfile) {
        if (originProfile == null) return;

        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("lang.yml");

        if (configuration == null)
            return;

        final Pair<BossBar, BukkitTask> pair = boosterList.get(originProfile.getName());

        if (pair == null)
            return;

        final BossBar originBar = pair.getKey();

        if (originBar == null)
            return;

        final BoosterStorage storage = BoosterStorage.getInstance();

        if (storage == null) return;

        final Pair<String, Booster> active = storage.getActiveBooster();

        if (active == null) return;

        final int seconds_keep = (int)((storage.getStartAt() + (active.getValue().getSeconds() * 1000) - System.currentTimeMillis()) / 1000L);
        final String hudTitle = new Text(configuration.getString("BOOSTER", "")
                .replace("{AUTHOR}", active.getKey())
                .replace("{MODIFIER}", active.getValue().getModifier() + "")
                .replace("{SECONDS_KEEP}", FormatTimeUtils.formatTimeNoSecondsADV(seconds_keep))
        ).getRaw();

        originBar.setTitle(hudTitle);
        originBar.setProgress((double) seconds_keep / active.getValue().getSeconds());
    }

    private void inspectPlayer(final @NotNull Player player) {
        if (!boosterList.containsKey(player.getName())) return;

        final Pair<BossBar, BukkitTask> pair = boosterList.get(player.getName());

        final BossBar bar = pair.getKey();
        final BukkitTask task = pair.getValue();

        bar.setVisible(false);
        bar.removeAll();

        task.cancel();

        boosterList.remove(player.getName());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onActive(final BoosterActiveEvent event) {
        this.active = true;

        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.handlePlayer(Profile.asName(player.getName()));

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 2.0f, 2);
            Messenger.sendMessage(player, "&eНачал работать бустер x" + event.getBooster().getModifier());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEnd(final BoosterEndEvent event) {
        this.active = false;

        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.inspectPlayer(player);

            Messenger.sendMessage(player, "&eБустер прекратил свою работу..");
        }

        ThanksCommand.clearCooldown();

        boosterList.clear();
    }

}

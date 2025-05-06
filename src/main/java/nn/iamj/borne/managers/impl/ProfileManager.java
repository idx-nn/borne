package nn.iamj.borne.managers.impl;

import com.google.gson.Gson;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.api.events.profile.ProfileSaveEvent;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.profile.assets.Settings;
import nn.iamj.borne.modules.profile.assets.Statistic;
import nn.iamj.borne.modules.profile.assets.Storage;
import nn.iamj.borne.modules.profile.level.Level;
import nn.iamj.borne.modules.profile.wallet.Wallet;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.event.EventUtils;
import nn.iamj.borne.modules.util.gson.GsonProvider;
import nn.iamj.borne.modules.util.logger.LoggerProvider;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class ProfileManager implements Manager {

    private final Map<String, Profile> profileList = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {
        Scheduler.asyncHandleRate(() ->
            this.profileList.values().forEach(profile -> {
                if (profile.isOnline())
                    return;
                this.save(profile, false, true);
                this.profileList.remove(profile.getName());
            }),
        20L * 60L * 60L, 20L * 60L * 60L);

        Bukkit.getOnlinePlayers().stream()
                .filter(Objects::nonNull)
                .forEach((player) -> {
                    if (isProfileExists(player.getName()))
                        getProfile(player.getName());
                    else createProfile(player);
                });
    }

    @Override
    public void shutdown() {
        this.profileList.values().stream()
                .filter(Profile::isOnline)
                .forEach(profile ->
                    save(profile, false, false));

        this.profileList.clear();
    }

    public boolean isProfileExists(final String name) {
        try {
            final ResultSet set = Borne.getBorne().getDataBase().fetchData("SELECT * FROM `profiles` WHERE `NICKNAME` = ?", name);

            if (set == null) return false;

            return set.next();
        } catch (Exception exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
            return true;
        }
    }

    public Profile createProfile(final Player player) {
        final Profile profile = new Profile(player);

        Borne.getBorne().getDataBase().executeUpdate("INSERT INTO `profiles` (" +
                "`UNIQUEID`," +
                "`NICKNAME`," +
                "`LEVEL`," +
                "`EXPERIENCE`," +
                "`MONEY`," +
                "`DONATED`," +
                "`DEVOLVE`," +
                "`STORAGE`," +
                "`SETTINGS`," +
                "`STATISTIC`" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",

                profile.getUuid().toString(),
                profile.getName(),

                profile.getLevel().getLevel(),
                profile.getLevel().getExperience(),

                profile.getWallet().getMoney(),
                profile.getWallet().getDonated(),
                profile.getWallet().getDevolve(),

                GsonProvider.getGson().toJson(profile.getStorage()),
                GsonProvider.getGson().toJson(profile.getSettings()),
                GsonProvider.getGson().toJson(profile.getStatistic())
        );

        this.profileList.put(player.getName(), profile);

        return profile;
    }

    public Profile getProfile(final String name) {
        if (this.profileList.containsKey(name)) {
            return profileList.get(name);
        }

        try {
            final ResultSet set = Borne.getBorne().getDataBase().fetchData("SELECT * FROM `profiles` WHERE `NICKNAME` = ?", name);
            if (set != null && set.next()) {
                final Profile profile = new Profile(name);

                final UUID uniqueId = UUID.fromString(set.getString("UNIQUEID"));

                final Level level = new Level(profile);
                level.setLevel(set.getInt("LEVEL"));
                level.setExperience(set.getDouble("EXPERIENCE"));

                final Wallet wallet = new Wallet(profile);
                wallet.setDevolve(set.getDouble("DEVOLVE"));
                wallet.setDonated(set.getDouble("DONATED"));
                wallet.setMoney(set.getDouble("MONEY"));

                final Gson gson = GsonProvider.getGson();

                final Storage storage = gson.fromJson(set.getString("STORAGE"), Storage.class);
                final Settings settings = gson.fromJson(set.getString("SETTINGS"), Settings.class);
                final Statistic statistic = gson.fromJson(set.getString("STATISTIC"), Statistic.class);

                profile.setUuid(uniqueId);

                profile.setLevel(level);
                profile.setWallet(wallet);

                profile.setStorage(storage);
                profile.setSettings(settings);
                profile.setStatistic(statistic);

                return profile;
            }

            return null;
        } catch (Exception exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
            return null;
        }
    }

    public void save(final Profile profile, final boolean onlyCache, final boolean async) {
        if (profile == null || profile.isSystemProvider()) return;

        final boolean result = EventUtils.callEvent(new ProfileSaveEvent(profile, onlyCache));

        if (!result) return;

        this.profileList.put(profile.getName(), profile);

        if (onlyCache) return;

        final Runnable runnable = () ->
            Borne.getBorne().getDataBase().executeUpdate("UPDATE `profiles` " +
                        "SET `LEVEL` = ?, `EXPERIENCE` = ?, " +
                        "`MONEY` = ?, `DEVOLVE` = ?, `DONATED` = ?," +
                        "`STORAGE` = ?, `SETTINGS` = ?, `STATISTIC` = ? " +
                        "WHERE `NICKNAME` = ? LIMIT 1",

                        profile.getLevel().getLevel(),
                        profile.getLevel().getExperience(),

                        profile.getWallet().getMoney(),
                        profile.getWallet().getDevolve(),
                        profile.getWallet().getDonated(),

                        GsonProvider.getGson().toJson(profile.getStorage()),
                        GsonProvider.getGson().toJson(profile.getSettings()),
                        GsonProvider.getGson().toJson(profile.getStatistic()),

                        profile.getName()
                    );

        if (async)
            Scheduler.asyncHandle(runnable);
        else runnable.run();
    }

}

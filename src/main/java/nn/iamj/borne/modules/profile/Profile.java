package nn.iamj.borne.modules.profile;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.profile.assets.Settings;
import nn.iamj.borne.modules.profile.assets.Statistic;
import nn.iamj.borne.modules.profile.assets.Storage;
import nn.iamj.borne.modules.profile.level.Level;
import nn.iamj.borne.modules.profile.system.SystemProfile;
import nn.iamj.borne.modules.profile.wallet.Wallet;
import nn.iamj.borne.modules.server.printing.Text;
import nn.iamj.borne.modules.server.printing.Title;

import java.util.List;
import java.util.UUID;

public class Profile {

    @Getter @Setter
    private UUID uuid;
    private final String nickname;

    @Getter @Setter
    private Level level;
    @Getter @Setter
    private Wallet wallet;

    @Getter @Setter
    private Statistic statistic;
    @Getter @Setter
    private Settings settings;
    @Getter @Setter
    private Storage storage;

    public Profile(final Player player) {
        this.uuid = player.getUniqueId();
        this.nickname = player.getName();

        this.level = new Level(this);
        this.wallet = new Wallet(this);

        this.statistic = new Statistic();
        this.settings = new Settings();
        this.storage = new Storage();
    }

    public Profile(final String nickname) {
        this.uuid = UUID.randomUUID();
        this.nickname = nickname;

        this.level = new Level(this);
        this.wallet = new Wallet(this);

        this.statistic = new Statistic();
        this.settings = new Settings();
        this.storage = new Storage();
    }

    public Profile(final UUID uuid, final String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;

        this.level = new Level(this);
        this.wallet = new Wallet(this);

        this.statistic = new Statistic();
        this.settings = new Settings();
        this.storage = new Storage();
    }

    public static Profile asName(final String name) {
        return Borne.getBorne().getProfileManager().getProfile(name);
    }

    public static Profile asSender(final CommandSender sender) {
        return sender instanceof Player ? asName(sender.getName()) : SystemProfile.getProfile();
    }

    public static Profile asEntity(final Entity entity) {
        return entity instanceof Player ? asName(entity.getName()) : null;
    }

    public String getName() {
        return this.nickname;
    }

    public OfflinePlayer asOfflineBukkit() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    public Player asBukkit() {
        return Bukkit.getPlayerExact(this.nickname);
    }

    public boolean isSystemProvider() {
        return this instanceof SystemProfile;
    }

    public boolean isOnline() {
        return asBukkit() != null;
    }

    public int getPing() {
        if (isSystemProvider() || !isOnline())
            return 0;
        return asBukkit().getPing();
    }

    @SuppressWarnings("deprecation")
    public void sendText(@NotNull final Text text) {
        if (isSystemProvider()) {
            Bukkit.getConsoleSender().sendMessage(text.getRaw());
            return;
        }
        if (isOnline()) {
            if (!text.hasComponent()) asBukkit().sendMessage(text.getRaw());
            else asBukkit().spigot().sendMessage(text.getComponent());
        }
    }

    @SuppressWarnings("deprecation")
    public void sendText(final Text... texts) {
        if (isSystemProvider()) {
            for (Text text : texts)
                Bukkit.getConsoleSender().sendMessage(text.getRaw());
            return;
        }
        if (isOnline()) {
            for (Text text : texts) {
                if (!text.hasComponent()) asBukkit().sendMessage(text.getRaw());
                else asBukkit().spigot().sendMessage(text.getComponent());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void sendText(final List<Text> texts) {
        if (isSystemProvider()) {
            for (Text text : texts)
                Bukkit.getConsoleSender().sendMessage(text.getRaw());
            return;
        }
        if (isOnline()) {
            for (Text text : texts) {
                if (!text.hasComponent()) asBukkit().sendMessage(text.getRaw());
                else asBukkit().spigot().sendMessage(text.getComponent());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void sendActionBar(final Text text) {
        if (isSystemProvider()) return;
        if (isOnline()) asBukkit().spigot().sendMessage(ChatMessageType.ACTION_BAR, text.getComponent());
    }

    @SuppressWarnings("deprecation")
    public void sendTitle(final Title title) {
        if (isSystemProvider()) return;
        if (isOnline())
            asBukkit().sendTitle(title.getTitle().getRaw(), title.getSubtitle().getRaw(), title.getFadeIn(), title.getDuration(), title.getFadeOut());
    }

    public Profile wakeup() {
        return Profile.asName(this.nickname);
    }

    public void save() {
        Borne.getBorne().getProfileManager().save(this, false, true);
    }

    public void save(final boolean onlyCache) {
        Borne.getBorne().getProfileManager().save(this, onlyCache, true);
    }

    public void save(final boolean onlyCache, final boolean async) {
        Borne.getBorne().getProfileManager().save(this, onlyCache, async);
    }

}

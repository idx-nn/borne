package nn.iamj.borne.modules.booster;

import lombok.Getter;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.api.events.booster.BoosterActiveEvent;
import nn.iamj.borne.modules.api.events.booster.BoosterEndEvent;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.collection.pair.Pair;
import nn.iamj.borne.modules.util.event.EventUtils;
import nn.iamj.borne.modules.util.logger.LoggerProvider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;

@Getter
public final class BoosterStorage implements Serializable {

    private static final File file = new File(Borne.getBorne().getPlugin().getDataFolder(), "booster.storage");

    @Getter
    private static BoosterStorage instance;

    private Pair<String, Booster> activeBooster;
    private long startAt;

    private transient BukkitTask task;

    public void activeBooster(final String author, final Booster booster) {
        final BoosterActiveEvent event = new BoosterActiveEvent(author, booster);

        final boolean success = EventUtils.callEvent(event);

        if (!success) return;

        this.activeBooster = new Pair<>(author, booster);
        this.startAt = System.currentTimeMillis();

        if (task != null && !task.isCancelled())
            task.cancel();

        runTask();
    }

    private void runTask() {
        task = Scheduler.asyncHandleRate(new BukkitRunnable() {
            @Override
            public void run() {
                if (activeBooster == null) {
                    this.cancel();

                    startAt = 0L;
                    return;
                }

                final long millis = activeBooster.getValue().getSeconds() * 1000L;
                final long now = System.currentTimeMillis();

                if (startAt + millis < now) {
                    resetBooster();

                    cancel();
                }
            }
        }, 12L, 12L);
    }

    public void resetBooster() {
        if (this.activeBooster == null)
            return;

        final String author = this.activeBooster.getKey();
        final Booster booster = this.activeBooster.getValue();

        final BoosterEndEvent event = new BoosterEndEvent(author, booster);

        final boolean success = EventUtils.callEvent(event);

        if (!success) return;

        this.activeBooster = null;
        this.startAt = 0L;

        if (task != null && !task.isCancelled())
            task.cancel();
    }

    public static BoosterStorage getInstance() {
        if (instance != null)
            return instance;

        try {
            if (!file.exists() && file.createNewFile()) {
                final FileOutputStream stream = new FileOutputStream(file);
                final ObjectOutputStream ostream = new ObjectOutputStream(stream);

                ostream.writeObject(new BoosterStorage());

                ostream.close();
                stream.close();
            }

            final FileInputStream stream = new FileInputStream(file);
            final ObjectInputStream ostream = new ObjectInputStream(stream);

            instance = (BoosterStorage) ostream.readObject();

            ostream.close();
            stream.close();

            if (instance != null && (instance.task == null || instance.task.isCancelled()) && instance.activeBooster != null)
                instance.runTask();

            return instance;
        } catch (final Exception exception) {
            LoggerProvider.getInstance().error(exception);
            return null;
        }
    }

    public static void save() {
        if (instance == null || !file.exists())
            return;

        try {
            final FileOutputStream stream = new FileOutputStream(file);
            final ObjectOutputStream ostream = new ObjectOutputStream(stream);

            ostream.writeObject(instance);

            ostream.close();
            stream.close();
        } catch (final Exception exception) {
            LoggerProvider.getInstance().error(exception);
        }
    }

}

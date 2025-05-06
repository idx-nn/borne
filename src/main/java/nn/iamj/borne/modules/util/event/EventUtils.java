package nn.iamj.borne.modules.util.event;

import org.bukkit.event.Event;
import nn.iamj.borne.modules.util.logger.LoggerProvider;

import java.util.concurrent.*;

public final class EventUtils {

    private static final ExecutorService service = Executors.newSingleThreadExecutor();

    private EventUtils() {}

    public static void callStaticEvent(final Event event) {
        if (service.isShutdown()) return;

        service.execute(event::callEvent);
    }

    public static boolean callEvent(final Event event) {
        /*try {
            final CompletableFuture<Boolean> result = new CompletableFuture<>();

            if (service.isShutdown()) return false;

            service.execute(() ->
                    result.complete(event.callEvent()));

            return result.get(8, TimeUnit.SECONDS);
        } catch (Exception exception) {
            return false;
        }*/
        callStaticEvent(event);

        return true;
    }

}

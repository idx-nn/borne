package nn.iamj.borne.modules.util.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public final class LoggerProvider {

    private static Logger logger;

    private LoggerProvider() {}

    public static void loadLogger() {
        logger = (Logger) LogManager.getRootLogger();
    }

    public static Logger getInstance() {
        return logger;
    }

}

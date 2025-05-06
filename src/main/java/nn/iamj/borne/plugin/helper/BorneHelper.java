package nn.iamj.borne.plugin.helper;

import nn.iamj.borne.Borne;
import nn.iamj.borne.managers.impl.ConfigManager;
import nn.iamj.borne.managers.impl.ProfileManager;
import nn.iamj.borne.managers.impl.addons.MineManager;
import nn.iamj.borne.modules.api.events.BorneReloadEvent;
import nn.iamj.borne.modules.util.event.EventUtils;
import nn.iamj.borne.plugin.loader.BorneLoader;

public final class BorneHelper {

    private BorneHelper() {}

    public static boolean sendReloadRequest(final boolean ignoreEvent) {
        final boolean result = EventUtils.callEvent(new BorneReloadEvent());

        if (!result && !ignoreEvent) return false;

        final BorneLoader borne = Borne.getBorne();

        final ConfigManager configManager = borne.getConfigManager();
        final ProfileManager profileManager = borne.getProfileManager();

        final MineManager mineManager = borne.getMineManager();

        configManager.reload();
        profileManager.getProfileList()
                .forEach((name, profile) -> profile.save(false));

        mineManager.shutdown();
        mineManager.initialize();

        return true;
    }

}

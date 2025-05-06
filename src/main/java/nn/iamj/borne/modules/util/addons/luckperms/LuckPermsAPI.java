package nn.iamj.borne.modules.util.addons.luckperms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import nn.iamj.borne.modules.util.logger.LoggerProvider;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public final class LuckPermsAPI {

    private LuckPermsAPI() {}

    public static User getUser(final String nickname) {
        final LuckPerms perms = LuckPermsProvider.get();

        return perms.getUserManager().getUser(nickname);
    }

    public static User getOfflineUser(final UUID uuid) {
        try {
            final LuckPerms perms = LuckPermsProvider.get();

            return perms.getUserManager().loadUser(uuid).get();
        } catch (InterruptedException | ExecutionException exception) {
            LoggerProvider.getInstance().error("Ops!", exception);

            return null;
        }
    }

}

package nn.iamj.borne.plugin;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import nn.iamj.borne.Borne;
import nn.iamj.borne.plugin.loader.BorneLoader;

public final class BornePlugin extends JavaPlugin {

    @Getter
    public static Borne borne;

    public BornePlugin() {
        borne = new BorneLoader(this);
    }

    @Override
    public void onLoad() {
        borne.preload();
    }

    @Override
    public void onEnable() {
        borne.initialize();
    }

    @Override
    public void onDisable() {
        borne.shutdown();
    }

}

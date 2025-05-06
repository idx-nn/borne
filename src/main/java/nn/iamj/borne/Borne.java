package nn.iamj.borne;

import nn.iamj.borne.plugin.BornePlugin;
import nn.iamj.borne.plugin.loader.BorneLoader;

public interface Borne {

    void preload();
    void initialize();
    void shutdown();

    BornePlugin getPlugin();
    static BorneLoader getBorne() {
        return BorneLoader.getLoader();
    }

}

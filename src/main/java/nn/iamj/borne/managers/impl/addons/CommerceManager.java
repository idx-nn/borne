package nn.iamj.borne.managers.impl.addons;

import nn.iamj.borne.basic.commerce.BoostersCommerce;
import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.commerce.Commerce;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CommerceManager implements Manager {

    private final Map<String, Commerce> commerceMap = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {
        this.registerCommerce("boosters", new BoostersCommerce());
    }

    @Override
    public void shutdown() {
        this.commerceMap.clear();
    }

    public void registerCommerce(final String id, final Commerce commerce) {
        this.commerceMap.put(id, commerce);
    }

    public Commerce getCommerce(final String id) {
        return this.commerceMap.get(id);
    }

}

package nn.iamj.borne.managers.impl;

import nn.iamj.borne.managers.Manager;
import nn.iamj.borne.modules.human.Human;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class HumanManager implements Manager {

    private final Map<String, Human> humanList = new ConcurrentHashMap<>();

    @Override
    public void preload() {}

    @Override
    public void initialize() {}

    @Override
    public void shutdown() {
        this.humanList.clear();
    }

    public void registerHuman(final String id, final Human human) {
        this.humanList.put(id, human);
    }

    public Human getHuman(final String id) {
        return this.humanList.get(id);
    }

    public Human getHuman(final int id) {
        return this.humanList.values().stream()
                .filter(human ->
                        human.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void unregisterHuman(final String id) {
        this.humanList.remove(id);
    }

}

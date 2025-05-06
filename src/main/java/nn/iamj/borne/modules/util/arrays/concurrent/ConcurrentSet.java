package nn.iamj.borne.modules.util.arrays.concurrent;

import io.netty.util.internal.PlatformDependent;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

public final class ConcurrentSet<E> extends AbstractSet<E> implements Serializable {

    private final ConcurrentMap<E, Boolean> map = PlatformDependent.newConcurrentHashMap();

    public int size() {
        return this.map.size();
    }

    @SuppressWarnings("all")
    public boolean contains(final Object object) {
        return this.map.containsKey(object);
    }

    public boolean add(final E object) {
        return this.map.putIfAbsent(object, Boolean.TRUE) == null;
    }

    public boolean remove(final Object object) {
        return this.map.remove(object) != null;
    }

    public void clear() {
        this.map.clear();
    }

    @NotNull
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

}

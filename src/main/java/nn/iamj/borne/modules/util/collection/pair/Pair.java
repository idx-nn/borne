package nn.iamj.borne.modules.util.collection.pair;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Pair<T, Y> implements Serializable {

    private T key;
    private Y value;

    public Pair(final T key, final Y value) {
        this.key = key;
        this.value = value;
    }

    public Pair(final Pair<T, Y> pair) {
        this.key = pair.getKey();
        this.value = pair.getValue();
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

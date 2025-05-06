package nn.iamj.borne.modules.util.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.Getter;

public final class GsonProvider {

    @Getter
    private static Gson gson;

    public static void loadGson() {
        gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                Expose expose = f.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }
            @Override
            public boolean shouldSkipClass(Class<?> c) {
                return false;
            }
        }).create();
    }

}

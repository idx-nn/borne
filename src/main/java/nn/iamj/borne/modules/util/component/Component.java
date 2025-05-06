package nn.iamj.borne.modules.util.component;

import nn.iamj.borne.modules.server.printing.Text;

public final class Component {

    private Component() {}

    public static final String GREEN = "&x&0&0&f&f&1&1";
    public static final String ORANGE = "&x&e&c&a&7&0&a";
    public static final String RED = "&x&c&f&0&0&0&0";

    public static Text text(final Type type, final String text) {
        return switch (type) {
            case SUCCESS -> new Text("혰 &8» " + GREEN + text);
            case WARNING -> new Text("혲 &8» " + ORANGE + text);
            case ERROR -> new Text("혱 &8» " + RED + text);
            case INFO -> new Text("혳 &8» &f" + text);
        };
    }

    public enum Type {
        SUCCESS,
        WARNING,
        ERROR,
        INFO
    }

}

package nn.iamj.borne.modules.server.printing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class Title {

    private Text title;
    private Text subtitle;

    private int fadeIn;
    private int duration;
    private int fadeOut;

    public Title(Text title, Text subtitle) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = 10;
        this.duration = 60;
        this.fadeOut = 10;
    }

    public Title(String title, String subtitle) {
        this.title = new Text(title);
        this.subtitle = new Text(subtitle);
        this.fadeIn = 10;
        this.duration = 60;
        this.fadeOut = 10;
    }

    public Title(String title, String subtitle, int fadeIn, int duration, int fadeOut) {
        this.title = new Text(title);
        this.subtitle = new Text(subtitle);
        this.fadeIn = fadeIn;
        this.duration = duration;
        this.fadeOut = fadeOut;
    }
}


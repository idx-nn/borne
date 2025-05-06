package nn.iamj.borne.modules.server.printing;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public final class Text implements Serializable {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    private final String text;
    private String hoverText;
    private String clickCommand;
    private String suggestCommand;
    private String clickURL;
    private String clickBuffer;
    private TextComponent component;
    private boolean colorize;

    public Text() {
        text = "";
    }

    public Text(String text) {
        if (text == null)
            text = "";

        this.text = ChatColor.translateAlternateColorCodes('&', text);
        this.colorize = true;
    }

    public Text(String text, boolean colorize) {
        if (text == null) text = "";
        if (colorize)
            text = ChatColor.translateAlternateColorCodes('&', text);
        this.text = text;
        this.colorize = colorize;
    }

    public static String strip(final String text) {
        return ChatColor.stripColor(new Text(text).getRaw());
    }

    public String getRaw() {
        return Coloriser.colorify(text);
    }

    public Text setText(String text) {
        return new Text(text);
    }

    public Text setHoverText(String hoverText) {
        this.hoverText = ChatColor.translateAlternateColorCodes('&', hoverText);
        createComponent();
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(this.hoverText).create()));
        return this;
    }

    public Text setHoverText(Text hoverText) {
        this.hoverText = ChatColor.translateAlternateColorCodes('&', hoverText.getRaw());
        createComponent();
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(this.hoverText).create()));
        return this;
    }

    public Text setSuggestCommand(String suggestCommand) {
        this.suggestCommand = suggestCommand;
        createComponent();
        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestCommand));
        return this;
    }

    public Text setSuggestCommand(Text suggestCommand) {
        this.suggestCommand = suggestCommand.getRaw();
        createComponent();
        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestCommand.getRaw()));
        return this;
    }

    public Text setClickCommand(String clickCommand) {
        this.clickCommand = clickCommand;
        createComponent();
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickCommand));
        return this;
    }

    public Text setClickCommand(Text clickCommand) {
        this.clickCommand = clickCommand.getRaw();
        createComponent();
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickCommand.getRaw()));
        return this;
    }

    public Text setClickURL(String clickURL) {
        this.clickURL = clickURL;
        createComponent();
        if (clickCommand == null) component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, clickURL));
        return this;
    }

    public Text setClickURL(Text clickURL) {
        this.clickURL = clickURL.getRaw();
        createComponent();
        if (clickCommand == null) component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, clickURL.getRaw()));
        return this;
    }

    public Text setClickBuffer(String clickBuffer) {
        this.clickBuffer = clickBuffer;
        createComponent();
        if (clickCommand == null) component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, clickBuffer));
        return this;
    }

    public Text setClickBuffer(Text clickBuffer) {
        this.clickBuffer = clickBuffer.getRaw();
        createComponent();
        if (clickCommand == null) component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, clickBuffer.getRaw()));
        return this;
    }

    public boolean hasComponent() {
        return component != null;
    }

    public TextComponent getComponent() {
        createComponent();
        return component;
    }

    private void createComponent() {
        if (component == null) {
            if (colorize) {
                component = new TextComponent();

                int i = 0;
                String lastColor = "";
                if (text.startsWith(" "))
                    component.addExtra(" ");

                for (String part : text.split(" ")) {
                    String colorized = !getColorCodes(part).isEmpty() ? lastColor + part : part;
                    TextComponent extra = new TextComponent((i == 0 ? "" : " ") + colorized);
                    Matcher matcher = pattern.matcher(part);

                    if (matcher.find()) {
                        String hex = part.substring(matcher.start(), matcher.end());
                        extra.setText(part.replace(hex, ""));
                        extra.setColor(ChatColor.of(hex));
                    }

                    lastColor = getColorCodes(colorized);

                    component.addExtra(extra);
                    i++;
                }
                if (!text.equals(" ") && text.endsWith(" ")) component.addExtra(" ");
            } else component = new TextComponent(text);
        }
    }

    @Override
    public String toString() {
        return text;
    }

    public static Text emptyText() {
        return new Text(" ");
    }

    private static String getColorCodes(String text) {
        char[] array = text.toCharArray();
        StringBuilder codes = new StringBuilder();
        for (int i = 0; i < array.length - 1; i++) {
            if ((array[i] == ChatColor.COLOR_CHAR || array[i] == '&') &&
                    "0123456789abcdefklmnorx".contains(String.valueOf(array[i + 1]).toLowerCase()))
                codes.append("§").append(array[i + 1]);
            else break;
        }
        return codes.toString();
    }
}

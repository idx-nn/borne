package nn.iamj.borne.modules.command.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.command.Command;
import nn.iamj.borne.modules.command.Executable;
import nn.iamj.borne.modules.command.intefaces.CommandCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onTab(final TabCompleteEvent event) {
        final String label = event.getBuffer().substring(1).split(" ")[0];
        final Executable executable = Borne.getBorne().getCommandManager().getCommand(label);

        if (executable == null) return;

        final Command command = (Command) executable;

        if (!(command instanceof CommandCompleter commandCompleter))
            return;

        final String[] ar = event.getBuffer().split(" ");

        final List<String> hintArgs = new ArrayList<>(List.of(Arrays.copyOfRange(ar, 1, ar.length)));

        if (hintArgs.isEmpty()) {
            event.setCompletions(Collections.emptyList());
            return;
        }

        if (!event.getBuffer().equals("/" + label + " ") && event.getBuffer().endsWith(" "))
            hintArgs.add("");

        final List<String> args = commandCompleter.onHint(event.getSender(), hintArgs);

        if (args == null || args.isEmpty())
            event.setCompletions(Collections.emptyList());
        else event.setCompletions(args);
    }

}

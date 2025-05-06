package nn.iamj.borne.modules.util.arrays.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import nn.iamj.borne.modules.util.arrays.BetterParser;
import nn.iamj.borne.modules.util.math.BetterNumbers;

import java.util.List;

public final class LocationParser {

    private LocationParser() {}

    public static String deparse(final Location location) {
        return "world:" + location.getWorld().getName() + " " +
                "x:" + location.getX() + " " +
                "y:" + location.getY() + " "  +
                "z:" + location.getZ();
    }

    public static Location parse(final String string) {
        if (string == null || string.isEmpty())
            return null;

        final BetterParser parser = BetterParser.parse(List.of(string.split(" ")));

        if (!parser.isExists("world")
                || !parser.isExists("x")
                || !parser.isExists("y")
                || !parser.isExists("z"))
            return null;

        final String world = parser.getValue("world");

        final String pointX = parser.getValue("x");
        final String pointY = parser.getValue("y");
        final String pointZ = parser.getValue("z");

        if (!BetterNumbers.isDouble(pointX)
                || !BetterNumbers.isDouble(pointY)
                || !BetterNumbers.isDouble(pointZ))
            return null;

        final World bukkitWorld = Bukkit.getWorld(world);

        if (bukkitWorld == null)
            return null;

        return new Location(bukkitWorld, Double.parseDouble(pointX), Double.parseDouble(pointY), Double.parseDouble(pointZ));
    }

}

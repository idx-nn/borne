package nn.iamj.borne.modules.util.math;

public final class BetterNumbers {

    private BetterNumbers() {}

    @SuppressWarnings("all")
    public static double floor(final int f, final double d) {
        return Double.parseDouble(String.format("%." + f + "f", d));
    }

    public static boolean isDouble(final String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(final String string) {
        try {
            Float.parseFloat(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(final String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

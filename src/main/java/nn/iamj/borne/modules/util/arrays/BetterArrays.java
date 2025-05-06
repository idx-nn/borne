package nn.iamj.borne.modules.util.arrays;

import org.bukkit.Material;

import java.util.*;
import java.util.function.Predicate;

public final class BetterArrays {

    private BetterArrays() {}

    public static <T> T randomElement(final Map<T, Integer> map) {
        double percent = 101 * Math.random();

        for (final Map.Entry<T, Integer> element : map.entrySet()) {
            percent -= element.getValue();

            if (percent < 0)
                return element.getKey();
        }

        final Optional<T> optional = map.keySet().stream().findFirst();

        return optional.orElse(null);

    }

    public static <T> List<T> arrayToList(final T[] array) {
        return Arrays.asList(array);
    }

    public static <T> List<T> copyOfRange(final List<T> list, final int start, final int end) {
        final List<T> copy = new ArrayList<>();

        for (int i = start; i < end; i++) {
            try {
                final T element = list.get(i);

                copy.add(element);
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }

        return copy;
    }

    public static <T> List<T> copyOf(final List<T> list, final int end) {
        return copyOfRange(list, 0, end);
    }

    public static <T> Collection<T> distinct(final Collection<T> data) {
        return new HashSet<>(data);
    }

    public static int getDuplicates(final Collection<?> data) {
        if (data.isEmpty()) return 0;

        return data.size() - distinct(data).size();
    }

    public static double getMaximumDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0.0D;

        double max = Double.MIN_VALUE;

        for (final double val : nums) if (val > max) max = val;

        return max;
    }

    public static int getMaximumInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        int max = Integer.MIN_VALUE;

        for (final int val : nums) if (val > max) max = val;

        return max;
    }

    public static long getMaximumLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        long max = Long.MIN_VALUE;

        for (final long val : nums) if (val > max) max = val;

        return max;
    }

    public static float getMaximumFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0.0F;

        float max = Float.MIN_VALUE;

        for (final float val : nums) if (val > max) max = val;

        return max;
    }

    public static double getMinimumDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0.0D;

        double min = Double.MAX_VALUE;

        for (final double val : nums) if (val < min) min = val;

        return min;
    }

    public static int getMinimumInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        int min = Integer.MAX_VALUE;

        for (final int val : nums) if (val < min) min = val;

        return min;
    }

    public static long getMinimumLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        long min = Long.MAX_VALUE;

        for (final long val : nums) if (val < min) min = val;

        return min;
    }

    public static float getMinimumFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0.0F;

        float min = Float.MAX_VALUE;

        for (final float val : nums) if (val < min) min = val;

        return min;
    }

    public static double getSumDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0D;

        double sum = 0D;

        for (final double num : nums) sum += num;

        return sum;
    }

    public static int getSumInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        int sum = 0;

        for (final int num : nums) sum += num;

        return sum;
    }

    public static long getSumLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        long sum = 0L;

        for (final long num : nums) sum += num;

        return sum;
    }

    public static float getSumFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0F;

        float sum = 0F;

        for (final float num : nums) sum += num;

        return sum;
    }

    public static double getAverageDouble(final Collection<Double> nums) {
        if (nums.isEmpty()) return 0D;

        return getSumDouble(nums) / nums.size();
    }

    public static int getAverageInt(final Collection<Integer> nums) {
        if (nums.isEmpty()) return 0;

        return getSumInt(nums) / nums.size();
    }

    public static long getAverageLong(final Collection<Long> nums) {
        if (nums.isEmpty()) return 0L;

        return getSumLong(nums) / nums.size();
    }

    public static float getAverageFloat(final Collection<Float> nums) {
        if (nums.isEmpty()) return 0F;

        return getSumFloat(nums) / nums.size();
    }

    public static double getAverageDouble(final Collection<Double> nums, final Predicate<Double> condition) {
        if (nums.isEmpty()) return 0D;

        double sum = 0D;

        for (final double num : nums) if (condition.test(num)) sum += num;

        return sum / nums.size();
    }

    public static int getAverageInt(final Collection<Integer> nums, final Predicate<Integer> condition) {
        if (nums.isEmpty()) return 0;

        int sum = 0;

        for (final int num : nums) if (condition.test(num)) sum += num;

        return sum / nums.size();
    }

    public static long getAverageLong(final Collection<Long> nums, final Predicate<Long> condition) {
        if (nums.isEmpty()) return 0L;

        long sum = 0L;

        for (final long num : nums) if (condition.test(num)) sum += num;

        return sum / nums.size();
    }

    public static float getAverageFloat(final Collection<Float> nums, final Predicate<Float> condition) {
        if (nums.isEmpty()) return 0F;

        float sum = 0F;

        for (final float num : nums) if (condition.test(num)) sum += num;

        return sum / nums.size();
    }

}

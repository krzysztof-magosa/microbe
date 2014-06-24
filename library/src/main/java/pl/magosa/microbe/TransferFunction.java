package pl.magosa.microbe;

/**
 * Abstract class represents transfer function.
 *
 * (c) 2014 Krzysztof Magosa
 */
public abstract class TransferFunction {
    abstract public double function(final double value);

    /**
     * Calculate derivative
     * @param value It's supposed to be output from function()
     * @return
     */
    abstract public double derivative(final double value);
    abstract public double getUpperLimit();
    abstract public double getLowerLimit();

    /**
     * Helper method to allow easy generation of arrays,
     * where values equal lower limit expect specified ones which are equal to upper limit.
     * It's especially helpful to generate arrays of desired output of network.
     *
     * Example (sigmoid):
     * makeArray(5, new int[] { 1, 2 });
     * { 0.0, 1.0, 1.0, 0.0, 0.0 }
     */
    public double[] makeArray(final int length, final int[] activeIndexes) {
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            result[i] = getLowerLimit();
        }

        for (int index : activeIndexes) {
            result[index] = getUpperLimit();
        }

        return result;
    }

    /**
     * Normalizes value originating from outside the network
     * to form suitable for network.
     *
     * You can have min>max, for example when you wants black pixel to be active, and white inactive.
     *
     * @param value Value to be normalized
     * @param min Value to be treated as minimum
     * @param max Value to be treated as maximum
     * @return Normalized value
     */
    public double normalize(final double value, final double min, final double max) {
        if (min > max) {
            return normalize(min - value, max, min);
        }

        return getLowerLimit() + ((value / (max - min))) * (getUpperLimit() - getLowerLimit());
    }
}

package pl.magosa.microbe;

/**
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
}

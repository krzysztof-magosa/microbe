package pl.magosa.microbe;

/**
 * Class represents hyperbolic tangent transfer function.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class TanhTransferFunction extends TransferFunction {
    public double function(final double value) {
        return Math.tanh(value);
    }

    public double derivative(final double value) {
        return 1.0 - Math.pow(value, 2);
    }

    public double getUpperLimit() {
        return 1.0;
    }

    public double getLowerLimit() {
        return -1.0;
    }
}

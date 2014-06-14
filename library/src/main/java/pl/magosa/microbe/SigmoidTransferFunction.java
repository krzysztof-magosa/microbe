package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class SigmoidTransferFunction implements TransferFunction {
    public double function(final double value) {
        return 1.0 / (1.0 + Math.exp(-1.0 * value));
    }

    public double derivative(final double value) {
        return value * (1.0 - value);
    }

    public double getUpperLimit() {
        return 1.0;
    }

    public double getLowerLimit() {
        return 0.0;
    }
}

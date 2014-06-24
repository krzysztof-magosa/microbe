package pl.magosa.microbe;

/**
 * Class represents step transfer function.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class StepTransferFunction extends TransferFunction {
    public double function(final double value) {
        return (value >= 0) ? 1.0 : 0.0;
    }

    public double derivative(final double value) {
        throw new RuntimeException("Not implemented.");
    }

    public double getUpperLimit() {
        throw new RuntimeException("Not implemented.");
    }

    public double getLowerLimit() {
        throw new RuntimeException("Not implemented.");
    }
}

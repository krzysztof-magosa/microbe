package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class StepTransferFunction implements TransferFunction {
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

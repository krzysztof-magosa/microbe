package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class LinearTransferFunction extends TransferFunction {
    public double function(final double value) {
        return value;
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

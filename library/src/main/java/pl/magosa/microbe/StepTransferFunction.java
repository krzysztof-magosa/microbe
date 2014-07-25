package pl.magosa.microbe;

/**
 * Class represents step transfer function.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class StepTransferFunction extends TransferFunction {
    private double threshold = 0;

    public void setThreshold(final double threshold) {
        this.threshold = threshold;
    }

    public double function(final double value) {
        return (value >= threshold) ? 1.0 : 0.0;
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

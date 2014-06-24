package pl.magosa.microbe;

/**
 * Class represents linear transfer function.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class LinearTransferFunction extends TransferFunction {
    public double function(final double value) {
        return value;
    }

    /**
     * @param value It's supposed to be output from function()
     * @return
     */
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

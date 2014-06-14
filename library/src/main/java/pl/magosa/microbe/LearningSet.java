package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class LearningSet {
    protected double[] input;
    protected double[] output;

    public LearningSet(final double[] input, final double[] output) {
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }
}

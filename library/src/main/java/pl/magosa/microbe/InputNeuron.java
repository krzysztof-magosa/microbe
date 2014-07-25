package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class InputNeuron extends Neuron {
    private double input;

    public void setInput(final double value) {
        input = value;
    }

    @Override
    public void activate() {
        sum = output = input;
    }
}

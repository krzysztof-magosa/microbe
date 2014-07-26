package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class BiasNeuron extends Neuron {
    @Override
    public void activate() {
        output = sum = 1;
    }

    @Override
    public void addInputConnection(final Connection connection) {
    }
}

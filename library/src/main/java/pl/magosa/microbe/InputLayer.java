package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class InputLayer extends Layer {
    public InputLayer(final int neuronsCount) {
        for (int i = 1; i <= neuronsCount; i++) {
            neurons.add(new InputNeuron());
        }
    }
}

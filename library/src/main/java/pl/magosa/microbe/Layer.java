package pl.magosa.microbe;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Class represents single layer in feed forward network.
 *
 * (c) 2014 Krzysztof Magosa
 */
public abstract class Layer {
    protected ArrayList<Neuron> neurons;

    public Layer() {
        neurons = new ArrayList<>();
    }

    /**
     * Returns neurons associated to this layer.
     */
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    /**
     * Activates all neurons associated to this layer.
     */
    public void run() {
        for (Neuron neuron : neurons) {
            neuron.activate();
        }
    }

    public void initBias() {
        neurons.add(new BiasNeuron());
    }

    public boolean hasBias() {
        for (Neuron neuron : neurons) {
            if (neuron instanceof BiasNeuron) {
                return true;
            }
        }

        return false;
    }
}

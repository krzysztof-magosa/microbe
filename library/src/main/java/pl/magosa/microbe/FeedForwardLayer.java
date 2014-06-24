package pl.magosa.microbe;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Class represents single layer in feed forward network.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class FeedForwardLayer {
    protected ArrayList<Neuron> neurons;
    protected FeedForwardLayer previousLayer;
    protected FeedForwardLayer nextLayer;

    public FeedForwardLayer() {
        neurons = new ArrayList<>();
    }

    /**
     * Creates neurons and associate it to this layer.
     *
     * @param count Number of neurons to be created
     * @param initFunction Lambda which initialise each neuron
     */
    public void createNeurons(final int count, Consumer<Neuron> initFunction) {
        for (int i = 1; i <= count; i++) {
            Neuron neuron = new Neuron();
            initFunction.accept(neuron);
            neurons.add(neuron);
        }
    }

    /**
     * Returns neurons associated to this layer.
     */
    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    /**
     * Returns next layer if any, null otherwise
     */
    public FeedForwardLayer getNextLayer() {
        return nextLayer;
    }

    /**
     * Used internally to set next layer
     * @param layer Next layer
     */
    protected void setNextLayer(final FeedForwardLayer layer) {
        nextLayer = layer;
    }

    /**
     * Returns previous layer if any, null otherwise
     */
    public FeedForwardLayer getPreviousLayer() {
        return previousLayer;
    }

    /**
     * Sets specified layer as previous layer for current layer.
     * Sets current layer as next layer for specified one.
     *
     * @param layer Previous layer
     */
    public void setPreviousLayer(final FeedForwardLayer layer) {
        previousLayer = layer;
        previousLayer.setNextLayer(this);
    }

    /**
     * Activates all neurons associated to this layer.
     */
    public void run() {
        for (Neuron neuron : neurons) {
            neuron.activate();
        }
    }

    /**
     * Checks whether there is next layer.
     *
     * @return true if there is next layer, false otherwise
     */
    public boolean hasNextLayer() {
        return (nextLayer != null);
    }

    /**
     * Forwards outputs of neurons associated to current layer into
     * inputs of neurons on next layer.
     */
    public void forward() {
        for (int i = 0; i < neurons.size(); i++) {
            double output = neurons.get(i).getOutput();

            for (Neuron nlNeuron : nextLayer.neurons) {
                nlNeuron.getInput(i).setValue(output);
            }
        }
    }
}


package pl.magosa.microbe;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class FeedForwardLayer {
    protected ArrayList<Neuron> neurons;
    protected FeedForwardLayer previousLayer;
    protected FeedForwardLayer nextLayer;

    public FeedForwardLayer() {
        neurons = new ArrayList<Neuron>();
    }

    public void createNeurons(final int count, Consumer<Neuron> initFunction) {
        for (int i = 1; i <= count; i++) {
            Neuron neuron = new Neuron();
            initFunction.accept(neuron);
            neurons.add(neuron);
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public FeedForwardLayer getNextLayer() {
        return nextLayer;
    }

    protected void setNextLayer(final FeedForwardLayer layer) {
        nextLayer = layer;
    }

    public FeedForwardLayer getPreviousLayer() {
        return previousLayer;
    }

    public void setPreviousLayer(final FeedForwardLayer layer) {
        previousLayer = layer;
        previousLayer.setNextLayer(this);
    }

    public void run() {
        for (Neuron neuron : neurons) {
            neuron.activate();
        }
    }

    public boolean hasNextLayer() {
        return (nextLayer != null);
    }

    public void forward() {
        for (int i = 0; i < neurons.size(); i++) {
            double output = neurons.get(i).getOutput();

            for (Neuron nlNeuron : nextLayer.neurons) {
                nlNeuron.getInput(i).setValue(output);
            }
        }
    }
}


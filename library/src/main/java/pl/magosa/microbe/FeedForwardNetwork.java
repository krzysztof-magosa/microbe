package pl.magosa.microbe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Class represents multi layer feed forward network.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class FeedForwardNetwork extends Network {
    protected ArrayList<Layer> layers;

    protected FeedForwardNetwork() {
        layers = new ArrayList<>();
    }

    protected void interconnectLayers(final Layer first, final Layer second) {
        for (Neuron flNeuron : first.getNeurons()) {
            for (Neuron slNeuron : second.getNeurons()) {
                Connection.interconnect(flNeuron, slNeuron);
            }
        }
    }

    protected void addLayer(final Layer layer) {
        layers.add(layer);

        if (layers.size() >= 2) {
            interconnectLayers(layers.get(layers.size() - 2), layer);
        }
    }

    /**
     * Sets input values of network
     * @param values
     */
    public void setValues(final double[] values) {
        final int size = layers.get(0).getNeurons().size() - (layers.get(0).hasBias() ? 1 : 0);

        if (values.length != size) {
            throw new RuntimeException("Incorrect number of input values.");
        }

        ArrayList<Neuron> neurons = layers.get(0).getNeurons();

        for (int index = 0; index < values.length; index++) {
            InputNeuron neuron = (InputNeuron)neurons.get(index);
            neuron.setInput(values[index]);
        }
    }

    /**
     * Activate neurons on each layer
     */
    public void run() {
        for (Layer layer : layers) {
            layer.run();
        }
    }

    /**
     * Returns array of outputs from last layer
     * @return
     */
    public double[] getOutput() {
        Layer layer = layers.get(layers.size() - 1);
        ArrayList<Neuron> neurons = layer.getNeurons();

        double[] output = new double[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            output[i] = neurons.get(i).getOutput();
        }

        return output;
    }

    /**
     * Returns list of layers associated to this network
     */
    public ArrayList<Layer> getLayers() {
        return layers;
    }

    /**
     * Returns hash map of neurons associated with layers of this network.
     * It's used internally to provide consistent interface for KnowledgeIO reader/writer.
     * @return Hash map of all neurons
     */
    protected HashMap<String, Neuron> getNeuronsMap() {
        HashMap<String, Neuron> map = new HashMap<>();

        for (int li = 0; li < layers.size(); li++) {
            for (int ni = 0; ni < layers.get(li).getNeurons().size(); ni++) {
                map.put(li + "/" + ni, layers.get(li).getNeurons().get(ni));
            }
        }

        return map;
    }

    public static Builder newInstance() {
        return new Builder();
    }

    public static class Builder {
        private boolean isBuilt;
        private LayerDefinition inputLayer;
        private ArrayList<LayerDefinition> hiddenLayers;
        private LayerDefinition outputLayer;
        private FeedForwardNetwork instance;

        private class LayerDefinition {
            private final int neuronsCount;
            private final TransferFunction function;
            private final boolean withBias;

            private LayerDefinition(int neuronsCount, TransferFunction function, boolean withBias) {
                this.neuronsCount = neuronsCount;
                this.function = function;
                this.withBias = withBias;
            }
        }

        private Builder() {
            hiddenLayers = new ArrayList<>();;
        }

        public Builder inputLayer(int inputs, boolean withBias) {
            inputLayer = new LayerDefinition(inputs, null, withBias);
            return this;
        }

        public Builder inputLayer(int inputs) {
            return inputLayer(inputs, true);
        }

        public Builder hiddenLayer(int neuronsCount, TransferFunction function, boolean withBias) {
            hiddenLayers.add(
                new LayerDefinition(
                    neuronsCount,
                    function,
                    withBias
                )
            );

            return this;
        }

        public Builder hiddenLayer(int neuronsCount, TransferFunction function) {
            return hiddenLayer(neuronsCount, function, true);
        }

        public Builder outputLayer(int neuronsCount, TransferFunction function) {
            outputLayer = new LayerDefinition(
                neuronsCount,
                function,
                false
            );

            return this;
        }

        private void createInputLayer(LayerDefinition definition) {
            Layer layer = new InputLayer(definition.neuronsCount);

            if (definition.withBias) {
                layer.initBias();
            }

            instance.addLayer(layer);
        }

        private void createLayer(LayerDefinition definition) {
            Layer layer = new StandardLayer(definition.neuronsCount, definition.function);

            if (definition.withBias) {
                layer.initBias();
            }

            instance.addLayer(layer);
        }

        private void validateState() {
            if (isBuilt) {
                throw new IllegalStateException("Network has been already built.");
            }

            if (inputLayer == null) {
                throw new IllegalStateException("Network must have input layer.");
            }

            if (hiddenLayers.isEmpty()) {
                throw new IllegalStateException("Network must have at least one hidden layer.");
            }

            if (outputLayer == null) {
                throw new IllegalStateException("Network must have output layer.");
            }
        }

        public FeedForwardNetwork build() {
            validateState();

            instance = new FeedForwardNetwork();

            createInputLayer(inputLayer);
            for (LayerDefinition definition : hiddenLayers) {
                createLayer(definition);
            }
            createLayer(outputLayer);

            isBuilt = true;

            return instance;
        }
    }
}

package pl.magosa.microbe;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represents knowledge of single neuron
 *
 * (c) 2014 Krzysztof Magosa
 */
public class NeuronKnowledge {
    protected HashMap<Integer, Double> inputConnections;

    public NeuronKnowledge() {
        inputConnections = new HashMap<>();
    }

    /**
     * Creates new instance of this class and transfer knowledge from specified neuron to it
     *
     * @param neuron Neuron from knowledge should be transferred
     * @return NeuronKnowledge object
     */
    public static NeuronKnowledge factory(final Neuron neuron) {
        NeuronKnowledge instance = new NeuronKnowledge();
        instance.transferFromNeuron(neuron);

        return instance;
    }

    /**
     * Transfers knowledge to specified neuron
     *
     * @param neuron Neuron to knowledge should be transferred
     */
    public void transferToNeuron(final Neuron neuron) {
        for (Map.Entry<Integer, Double> entry : inputConnections.entrySet()) {
            neuron.getInputConnections().get(entry.getKey()).setWeight(entry.getValue());
        }
    }

    /**
     * Transfers knowledge from neuron to this object
     *
     * @param neuron Neuron from knowledge should be transferred
     */
    public void transferFromNeuron(final Neuron neuron) {
        inputConnections.clear();
        for (int i = 0; i < neuron.getInputConnections().size(); i++) {
            inputConnections.put(i, neuron.getInputConnections().get(i).getWeight());
        }
    }
}

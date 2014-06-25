package pl.magosa.microbe;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represents knowledge of network.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class NetworkKnowledge {
    protected HashMap<String, NeuronKnowledge> data;

    public NetworkKnowledge() {
        data = new HashMap<>();
    }

    /**
     * Creates new instance of this class and transfer knowledge from network to it.
     * @param network Network from knowledge to be transferred.
     * @return Instance of NetworkKnowledge
     */
    public static NetworkKnowledge factory(final Network network) {
        NetworkKnowledge instance = new NetworkKnowledge();
        instance.transferFromNetwork(network);

        return instance;
    }

    /**
     * Transfers knowledge to specified network.
     * @param network Network to transfer knowledge into.
     */
    public void transferToNetwork(final Network network) {
        HashMap<String, Neuron> neuronsMap = network.getNeuronsMap();

        for (Map.Entry<String, NeuronKnowledge> entry : data.entrySet()) {
            if (neuronsMap.containsKey(entry.getKey())) {
                entry.getValue().transferToNeuron(neuronsMap.get(entry.getKey()));
            }
        }
    }

    /**
     * Transfers knowledge from specified network.
     * @param network Network to transfer knowledge from.
     */
    public void transferFromNetwork(final Network network) {
        data.clear();
        for (Map.Entry<String, Neuron> entry : network.getNeuronsMap().entrySet()) {
            data.put(entry.getKey(), NeuronKnowledge.factory(entry.getValue()));
        }
    }
}

package pl.magosa.microbe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class NetworkKnowledgeTest {
    @Test
    public void test() {
        // Initialize simple network
        FeedForwardNetwork network = new FeedForwardNetwork();
        FeedForwardLayer input = network.createInputLayer(2);
        FeedForwardLayer hidden = network.createLayer((FeedForwardLayer layer) -> {
            layer.createNeurons(2, (Neuron neuron) -> {
                neuron.createInputs(1);
            });
        });
        FeedForwardLayer output = network.createLayer((FeedForwardLayer layer) -> {
            layer.createNeurons(2, (Neuron neuron) -> {
                neuron.createInputs(1);
            });
        });

        // Set some initial parameters
        input.getNeurons().get(0).setThreshold(0.1);
        input.getNeurons().get(0).getInput(0).setWeight(0.11);
        input.getNeurons().get(1).setThreshold(0.2);
        input.getNeurons().get(1).getInput(0).setWeight(0.21);

        hidden.getNeurons().get(0).setThreshold(0.3);
        hidden.getNeurons().get(0).getInput(0).setWeight(0.31);
        hidden.getNeurons().get(1).setThreshold(0.4);
        hidden.getNeurons().get(1).getInput(0).setWeight(0.41);

        output.getNeurons().get(0).setThreshold(0.5);
        output.getNeurons().get(0).getInput(0).setWeight(0.51);
        output.getNeurons().get(1).setThreshold(0.6);
        output.getNeurons().get(1).getInput(0).setWeight(0.61);

        // Save knowledge
        NetworkKnowledge knowledge = NetworkKnowledge.factory(network);

        // Change parameters
        input.getNeurons().get(0).setThreshold(1.1);
        input.getNeurons().get(0).getInput(0).setWeight(1.11);
        input.getNeurons().get(1).setThreshold(1.2);
        input.getNeurons().get(1).getInput(0).setWeight(1.21);

        hidden.getNeurons().get(0).setThreshold(1.3);
        hidden.getNeurons().get(0).getInput(0).setWeight(1.31);
        hidden.getNeurons().get(1).setThreshold(1.4);
        hidden.getNeurons().get(1).getInput(0).setWeight(1.41);

        output.getNeurons().get(0).setThreshold(1.5);
        output.getNeurons().get(0).getInput(0).setWeight(1.51);
        output.getNeurons().get(1).setThreshold(1.6);
        output.getNeurons().get(1).getInput(0).setWeight(1.61);

        // Load initial parameters
        knowledge.transferToNetwork(network);

        // Check whether everything has been restored
        assertEquals(0.1, input.getNeurons().get(0).getThreshold(), 0.001);
        assertEquals(0.11, input.getNeurons().get(0).getInput(0).getWeight(), 0.001);
        assertEquals(0.2, input.getNeurons().get(1).getThreshold(), 0.001);
        assertEquals(0.21, input.getNeurons().get(1).getInput(0).getWeight(), 0.001);

        assertEquals(0.3, hidden.getNeurons().get(0).getThreshold(), 0.001);
        assertEquals(0.31, hidden.getNeurons().get(0).getInput(0).getWeight(), 0.001);
        assertEquals(0.4, hidden.getNeurons().get(1).getThreshold(), 0.001);
        assertEquals(0.41, hidden.getNeurons().get(1).getInput(0).getWeight(), 0.001);

        assertEquals(0.5, output.getNeurons().get(0).getThreshold(), 0.001);
        assertEquals(0.51, output.getNeurons().get(0).getInput(0).getWeight(), 0.001);
        assertEquals(0.6, output.getNeurons().get(1).getThreshold(), 0.001);
        assertEquals(0.61, output.getNeurons().get(1).getInput(0).getWeight(), 0.001);
    }
}

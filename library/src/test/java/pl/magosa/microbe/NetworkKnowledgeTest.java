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
        FeedForwardNetwork network = FeedForwardNetwork.newInstance()
            .inputLayer(2)
            .hiddenLayer(2, new TanhTransferFunction())
            .outputLayer(2, new TanhTransferFunction())
            .build();

        Layer hidden = network.getLayers().get(1);
        Layer output = network.getLayers().get(2);

        // Set some initial parameters
        hidden.getNeurons().get(0).getInputConnections().get(0).setWeight(0.31);
        hidden.getNeurons().get(1).getInputConnections().get(0).setWeight(0.41);

        output.getNeurons().get(0).getInputConnections().get(0).setWeight(0.51);
        output.getNeurons().get(1).getInputConnections().get(0).setWeight(0.61);

        // Save knowledge
        NetworkKnowledge knowledge = NetworkKnowledge.factory(network);

        // Change parameters
        hidden.getNeurons().get(0).getInputConnections().get(0).setWeight(1.31);
        hidden.getNeurons().get(1).getInputConnections().get(0).setWeight(1.41);

        output.getNeurons().get(0).getInputConnections().get(0).setWeight(1.51);
        output.getNeurons().get(1).getInputConnections().get(0).setWeight(1.61);

        // Load initial parameters
        knowledge.transferToNetwork(network);

        // Check whether everything has been restored
        assertEquals(0.31, hidden.getNeurons().get(0).getInputConnections().get(0).getWeight(), 0.001);
        assertEquals(0.41, hidden.getNeurons().get(1).getInputConnections().get(0).getWeight(), 0.001);

        assertEquals(0.51, output.getNeurons().get(0).getInputConnections().get(0).getWeight(), 0.001);
        assertEquals(0.61, output.getNeurons().get(1).getInputConnections().get(0).getWeight(), 0.001);
    }
}

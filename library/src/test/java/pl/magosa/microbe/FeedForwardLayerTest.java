package pl.magosa.microbe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class FeedForwardLayerTest {
    @Test
    public void testCreateNeurons() {
        FeedForwardLayer layer = new FeedForwardLayer();
        layer.createNeurons(10, (Neuron neuron) -> {});

        assertEquals(10, layer.getNeurons().size());
    }

    @Test
    public void testJumpsBetweenLayers() {
        FeedForwardLayer layer1 = new FeedForwardLayer();
        FeedForwardLayer layer2 = new FeedForwardLayer();
        layer2.setPreviousLayer(layer1);

        assertEquals(layer1, layer2.getPreviousLayer());
        assertEquals(layer2, layer1.getNextLayer());

        assertEquals(true, layer1.hasNextLayer());
        assertEquals(false, layer2.hasNextLayer());
    }

    // @TODO Test to be continued
}

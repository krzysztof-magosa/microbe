package pl.magosa.microbe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class LayerTest {
    @Test
    public void testCreateNeurons() {
        Layer layer = new Layer();
        layer.createNeurons(10, (Neuron neuron) -> {});

        assertEquals(10, layer.getNeurons().size());
    }

    @Test
    public void testJumpsBetweenLayers() {
        Layer layer1 = new Layer();
        Layer layer2 = new Layer();
        layer2.setPreviousLayer(layer1);

        assertEquals(layer1, layer2.getPreviousLayer());
        assertEquals(layer2, layer1.getNextLayer());

        assertEquals(true, layer1.hasNextLayer());
        assertEquals(false, layer2.hasNextLayer());
    }

    // @TODO Test to be continued
}

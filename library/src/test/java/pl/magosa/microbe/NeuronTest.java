package pl.magosa.microbe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class NeuronTest {
    @Test
    public void testBias() {
        Neuron neuron;

        neuron = new Neuron();
        assertEquals(false, neuron.hasBias());

        neuron = new Neuron();
        neuron.createBias();
        assertEquals(true, neuron.hasBias());
    }

    // @TODO Test to be continued
}

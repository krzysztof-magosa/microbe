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

    @Test
    public void testThreshold() {
        Neuron neuron = new Neuron();
        neuron.setTransferFunction(new LinearTransferFunction());
        neuron.createInputs(1, (Input input) -> {
            input.setWeight(1.0);
        });

        neuron.setThreshold(5.0);
        neuron.getInputs().get(0).setValue(10);
        neuron.activate();
        assertEquals(5.0, neuron.getOutput(), 0.01);

        neuron.setThreshold(1.0);
        neuron.getInputs().get(0).setValue(10);
        neuron.activate();
        assertEquals(9.0, neuron.getOutput(), 0.01);

        neuron.setThreshold(0.0);
        neuron.getInputs().get(0).setValue(15);
        neuron.activate();
        assertEquals(15, neuron.getOutput(), 0.01);
    }

    // @TODO Test to be continued
}

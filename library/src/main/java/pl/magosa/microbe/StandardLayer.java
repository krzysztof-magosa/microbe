package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class StandardLayer extends Layer {
    public StandardLayer(final int neuronsCount, final TransferFunction transferFunction) {
        for (int i = 1; i <= neuronsCount; i++) {
            Neuron neuron = new Neuron();
            neuron.setTransferFunction(transferFunction);
            neurons.add(neuron);
        }
    }
}

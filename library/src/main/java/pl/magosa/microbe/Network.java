package pl.magosa.microbe;

import java.util.HashMap;

/**
 * Abstract class for any network
 *
 * (c) 2014 Krzysztof Magosa
 */
public abstract class Network {
    abstract public void run();
    abstract public void setValues(final double[] values);
    abstract public double[] getOutput();
    abstract protected HashMap<String, Neuron> getNeuronsMap();

    public void printOutput() {
        double[] output = getOutput();

        for (int i = 0; i < output.length; i++) {
            System.out.printf("output[%d] = % .2f\n", i, output[i]);
        }
    }
}

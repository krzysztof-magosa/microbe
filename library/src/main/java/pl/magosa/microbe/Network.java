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
}

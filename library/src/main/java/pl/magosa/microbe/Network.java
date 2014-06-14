package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public abstract class Network {
    abstract public void run();
    abstract public void setValues(final double[] values);
    abstract public double[] getOutput();
}

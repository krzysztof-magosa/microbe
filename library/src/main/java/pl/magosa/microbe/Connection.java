package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class Connection {
    private Neuron source;
    private Neuron destination;
    private double weight;

    public Connection(final Neuron source, final Neuron destination) {
        this.source = source;
        this.destination = destination;
        weight = -0.5 + Math.random();
    }

    public Neuron getSource() {
        return source;
    }

    public Neuron getDestination() {
        return destination;
    }

    public double getInput() {
        return source.getOutput();
    }

    public double getWeightedInput() {
        return source.getOutput() * weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(final double value) {
        weight = value;
    }

    public void incWeight(final double value) {
        weight += value;
    }

    public void decWeight(final double value) {
        weight -= value;
    }

    public static void interconnect(final Neuron source, final Neuron destination) {
        Connection connection = new Connection(source, destination);
        source.addOutputConnection(connection);
        destination.addInputConnection(connection);
    }
}

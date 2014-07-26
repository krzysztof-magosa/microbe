package pl.magosa.microbe;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Class represents sigle neuron
 *
 * (c) 2014 Krzysztof Magosa
 */
public class Neuron {
    protected double output;
    protected double sum;
    protected TransferFunction transferFunction;
    protected ArrayList<Connection> inputConnections;
    protected ArrayList<Connection> outputConnections;

    public Neuron() {
        inputConnections = new ArrayList<>();
        outputConnections = new ArrayList<>();
    }

    public ArrayList<Connection> getInputConnections() {
        return inputConnections;
    }

    public ArrayList<Connection> getOutputConnections() {
        return outputConnections;
    }

    public void addInputConnection(final Connection connection) {
        inputConnections.add(connection);
    }

    public void addOutputConnection(final Connection connection) {
        outputConnections.add(connection);
    }

    /**
     * Sets transfer function for this neuron
     */
    public void setTransferFunction(TransferFunction transferFunction) {
        this.transferFunction = transferFunction;
    }

    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    public double getOutput() {
        return output;
    }

    public double getSum() {
        return sum;
    }

    /**
     * Calculates output of neuron.
     */
    public void activate() {
        sum = 0;
        for (Connection connection : inputConnections) {
            sum += connection.getWeightedInput();
        }

        output = transferFunction.function(sum);
    }
}

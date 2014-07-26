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
    protected ArrayList<Input> inputs;
    protected TransferFunction transferFunction;

    public Neuron() {
        inputs = new ArrayList<>();
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

    /**
     * Gets input
     * @param index Index of input
     * @return Input object
     */
    public Input getInput(final int index) {
        return inputs.get(index);
    }

    /**
     * Creates inputs
     * @param count How many inputs should be created
     */
    public void createInputs(final int count) {
        for (int i = 1; i <= count; i++) {
            createInput((Input input) -> {});
        }
    }

    /**
     * Creates inputs and initialise them using specified lambda
     * @param count How many inputs should be created
     * @param initFunction Lambda which initialise each input
     */
    public void createInputs(final int count, Consumer<Input> initFunction) {
        for (int i = 1; i <= count; i++) {
            createInput(initFunction);
        }
    }

    /**
     * Creates one input initialise it using specified lambda
     * @param initFunction Lambda which initialise input
     */
    public void createInput(Consumer<Input> initFunction) {
        Input input = new Input();
        initFunction.accept(input);
        inputs.add(input);
    }

    public ArrayList<Input> getInputs() {
        return inputs;
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

        for (Input input : inputs) {
            sum += (input.getWeight() * input.getValue());
        }

        output = transferFunction.function(sum);
    }
}

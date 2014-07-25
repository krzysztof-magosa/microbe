package pl.magosa.microbe.examples.or;

import pl.magosa.microbe.Input;
import pl.magosa.microbe.Neuron;
import pl.magosa.microbe.StepTransferFunction;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class Main {
    protected static Neuron neuron;

    private static void print() {
        System.out.printf(
            "(%.0f or %.0f) -> %.0f\n",
            neuron.getInputs().get(0).getValue(),
            neuron.getInputs().get(1).getValue(),
            neuron.getOutput()
        );
    }

    public static void main(String[] args) {
        neuron = new Neuron();

        StepTransferFunction transferFunction = new StepTransferFunction();
        transferFunction.setThreshold(1.0);

        neuron.setTransferFunction(transferFunction);
        neuron.createInputs(2, (Input input) -> {
            input.setWeight(1);
        });

        neuron.getInputs().get(0).setValue(0.0);
        neuron.getInputs().get(1).setValue(0.0);
        neuron.activate();
        print();

        neuron.getInputs().get(0).setValue(1.0);
        neuron.getInputs().get(1).setValue(0.0);
        neuron.activate();
        print();

        neuron.getInputs().get(0).setValue(0.0);
        neuron.getInputs().get(1).setValue(1.0);
        neuron.activate();
        print();

        neuron.getInputs().get(0).setValue(1.0);
        neuron.getInputs().get(1).setValue(1.0);
        neuron.activate();
        print();
    }
}


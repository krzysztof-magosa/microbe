package pl.magosa.microbe.examples.xor;

import pl.magosa.microbe.*;

/**
 * Example of simple network trained under supervision.
 * Problem to solve:  XOR
 * Transfer function: Hyperbolic tangent
 * Assumptions:
 *  -1 -> false
 *   1 -> true
 *
 * (c) 2014 Krzysztof Magosa
 */
public class Main {
    protected static FeedForwardNetwork network;
    protected static TransferFunction transferFunction;

    private static void print(final double x, final double y) {
        network.setValues(new double[] { x, y });
        network.run();
        double[] output = network.getOutput();

        System.out.printf("(% .0f xor % .0f) -> % .0f\n", x, y, output[0]);
    }

    public static void main(String[] args) {
        network = new FeedForwardNetwork();
        transferFunction = new TanhTransferFunction();

        // Input layer
        network.createInputLayer(2);

        // Hidden layer
        network.createLayer((FeedForwardLayer layer) -> {
            layer.createNeurons(3, (Neuron neuron) -> {
                neuron.setTransferFunction(transferFunction);
                neuron.createBias();
                neuron.createInputs(network.getLastLayer().getNeurons().size());
            });
        });

        // Output layer
        network.createLayer((FeedForwardLayer layer) -> {
            layer.createNeurons(1, (Neuron neuron) -> {
                neuron.setTransferFunction(transferFunction);
                neuron.createBias();
                neuron.createInputs(network.getLastLayer().getNeurons().size());
            });
        });

        Teacher teacher = Teacher.factory(network);
        teacher.setLearningRate(0.25);
        teacher.setMomentum(0.7);

        // Truth table
        teacher.addLearningSet(new LearningSet(new double[] { -1.0, -1.0 }, new double[] { -1.0 }));
        teacher.addLearningSet(new LearningSet(new double[] { -1.0,  1.0 }, new double[] {  1.0 }));
        teacher.addLearningSet(new LearningSet(new double[] {  1.0, -1.0 }, new double[] {  1.0 }));
        teacher.addLearningSet(new LearningSet(new double[] {  1.0,  1.0 }, new double[] { -1.0 }));

        // Train
        if (teacher.train(50000, 0.05)) {
            System.out.print("Successfully trained network.\n");
        }
        else {
            System.out.print("You are unlucky, network wasn't trained, try again please.\n");
            System.exit(1);
        }

        print(-1.0, -1.0);
        print(-1.0,  1.0);
        print( 1.0, -1.0);
        print( 1.0,  1.0);
    }
}

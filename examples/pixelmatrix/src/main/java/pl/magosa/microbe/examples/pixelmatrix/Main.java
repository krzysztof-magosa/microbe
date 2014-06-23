package pl.magosa.microbe.examples.pixelmatrix;

import pl.magosa.microbe.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class Main {
    protected static final int width = 5;
    protected static final int height = 5;
    protected static final String[] symbols = {
        "plus",
        "minus",
        "ex",
        "slash",
    };

    protected FeedForwardNetwork network;
    protected TransferFunction transferFunction;
    protected Teacher teacher;

    public double[] transformImage(final String path) throws IOException {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        PixelMatrix matrix = PixelMatrix.factory(image);

        double result[] = matrix.transformToArray((Pixel pixel) -> {
            return new Double[] {
                // we want black to be active pixel, and white to be inactive
                transferFunction.normalize(pixel.getRelativeLuminance(), 1.0, 0.0)
            };
        });

        return result;
    }

    protected void init() {
        network = new FeedForwardNetwork();
        transferFunction = new TanhTransferFunction();

        // Input layer (one neuron per pixel in the picture)
        network.createInputLayer(width * height);

        // Hidden layer
        network.createLayer((FeedForwardLayer layer) -> {
            layer.createNeurons(3, (Neuron neuron) -> {
                neuron.setTransferFunction(transferFunction);
                neuron.createBias();
                neuron.createInputs(network.getLastLayer().getNeurons().size());
            });
        });

        // Output layer
        // one neuron per symbol
        network.createLayer((FeedForwardLayer layer) -> {
            layer.createNeurons(symbols.length, (Neuron neuron) -> {
                neuron.setTransferFunction(transferFunction);
                neuron.createBias();
                neuron.createInputs(network.getLastLayer().getNeurons().size());
            });
        });
    }

    protected void teach() throws IOException {
        // Teacher
        teacher = Teacher.factory(network);
        teacher.setMomentum(0.70);

        for (int i = 0; i < symbols.length; i++) {
            teacher.addLearningSet(
                new LearningSet(
                    transformImage(symbols[i] + ".png"),
                    transferFunction.makeArray(symbols.length, new int[] { i })
                )
            );
        }

        TeacherController controller = new TeacherController(teacher);
        controller.setMaximumLearningRate(0.75);
        controller.setGoal(0.01);
        controller.setDebug(true);
        controller.setDebugInterval(10);
        controller.train();
    }

    protected void check() throws IOException {
        for (int i = 0; i < symbols.length; i++) {
            System.out.printf("Symbol = %s, output[%d] should be near to %.2f.\n", symbols[i], i, transferFunction.getUpperLimit());
            double[] data = transformImage(symbols[i] + "_test.png");
            network.setValues(data);
            network.run();
            double[] output = network.getOutput();

            for (int x = 0; x < symbols.length; x++) {
                System.out.printf("output[%d] = % .2f\n", x, output[x]);
            }

            System.out.println();
        }
    }

    protected void run() {
        try {
            init();
            teach();
            check();
        }
        catch (Exception e) {
            System.out.printf("An error occurred: %s\n", e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}

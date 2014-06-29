package pl.magosa.microbe.examples.normalize;

import pl.magosa.microbe.TanhTransferFunction;
import pl.magosa.microbe.TransferFunction;

/**
 * This example shows how to normalize and denormalize numbers.
 * As you can see in the example, sometimes range (min-max) is reversed, and min value is bigger than max.
 * It's especially useful when you for example want to normalize rgb colors, and treat darker colors as more active.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class Main {
    protected TransferFunction transferFunction;

    protected void normalize(final double value, final double min, final double max) {
        final double normalized = transferFunction.normalize(value, min, max);
        System.out.printf("%8.2f/(%8.2f - %8.2f) -> % 8.2f\n", value, min, max, normalized);
    }

    protected void denormalize(final double value, final double min, final double max) {
        final double normalized = transferFunction.denormalize(value, min, max);
        System.out.printf("%8.2f/(%8.2f - %8.2f) -> % 8.2f\n", value, min, max, normalized);
    }

    protected void run() {
        transferFunction = new TanhTransferFunction();

        System.out.println("Normalization");
        // Normalize RGB component
        // Treat dark color as inactive and bright as active
        //   0 -> -1
        // 255 ->  1
        normalize(0, 0, 255);
        normalize(128, 0, 255);
        normalize(255, 0, 255);
        // Normalize RGB component
        // Treat bright color as inactive, and dark as active
        // 255 -> -1
        //   0 ->  1
        normalize(0, 255, 0);
        normalize(128, 255, 0);
        normalize(255, 255, 0);
        System.out.println();

        System.out.println("Denormalization");
        // Denormalize RGB component
        // Treat inactive as dark color and active as bright
        // -1 ->   0
        //  1 -> 255
        denormalize(-1.0, 0, 255);
        denormalize(0.0, 0, 255);
        denormalize(1.0, 0, 255);
        // Denormalize RGB component
        // Treat inactive as bright color and active as dark
        // -1 -> 255
        //  1 ->   0
        denormalize(-1.0, 255, 0);
        denormalize(0.0, 255, 0);
        denormalize(1.0, 255, 0);
        System.out.println();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}

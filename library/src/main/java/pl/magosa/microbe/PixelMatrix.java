package pl.magosa.microbe;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class represents matrix of pixels
 *
 * (c) 2014 Krzysztof Magosa
 */
public class PixelMatrix {
    protected Pixel[][] data;
    protected int width;
    protected int height;

    /**
     * Creates new matrix with specified dimensions
     */
    public PixelMatrix(final int width, final int height) {
        data = new Pixel[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y][x] = new Pixel();
            }
        }

        this.width = width;
        this.height = height;
    }

    /**
     * Creates new instance of PixelMatrix from BufferedImage
     */
    public static PixelMatrix factory(final BufferedImage image) {
        PixelMatrix instance = new PixelMatrix(image.getWidth(), image.getHeight());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                instance.get(x, y).setRGB(image.getRGB(x, y));
            }
        }

        return instance;
    }

    public Pixel get(final int x, final int y) {
        return data[y][x];
    }

    public int size() {
        return width * height;
    }

    /**
     * Transforms matrix into array suitable to be used as input of network.
     * Specified lambda is executed on each pixel, returned array is appended to array.
     *
     * Example usage:
     * double result[] = matrix.transformToArray((Pixel pixel) -> {
     *     return new Double[] {
     *         transferFunction.normalize(pixel.getRelativeLuminance(), 0.0, 1.0)
     *     };
     * });
     *
     * @param function Lambda which analysis each pixel and returns array
     * @return Array which represents image
     */
    public double[] transformToArray(Function<Pixel, Double[]> function) {
        ArrayList<Double> vector = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Double[] values = function.apply(get(x, y));

                for (int i = 0; i < values.length; i++) {
                    vector.add(values[i]);
                }
            }
        }

        double[] result = new double[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            result[i] = vector.get(i);
        }

        return result;
    }
}

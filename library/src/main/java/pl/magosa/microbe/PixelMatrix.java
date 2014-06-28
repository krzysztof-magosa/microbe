package pl.magosa.microbe;

import java.awt.*;
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

    /**
     * Downsample image to lower resolution.
     *
     * @param targetWidth width of new image
     * @param targetHeight height of new image
     * @return New instance of pixelmatrix with downsampled image
     */
    public PixelMatrix downsample(final int targetWidth, final int targetHeight) {
        PixelMatrix target = new PixelMatrix(targetWidth, targetHeight);

        final double ratioX = (double)width / (double)targetWidth;
        final double ratioY = (double)height / (double)targetHeight;

        for (int targetY = 0; targetY < targetHeight; targetY++) {
            for (int targetX = 0; targetX < targetWidth; targetX++) {
                Pixel pixel = target.get(targetX, targetY);

                int totalPixels = 0;
                double red = 0.0;
                double green = 0.0;
                double blue = 0.0;

                // Calculate region which needs to be downsamples to one pixel
                int startX = (int)(targetX * ratioX);
                int startY = (int)(targetY * ratioY);
                int stopX = (int)(startX + ratioX);
                int stopY = (int)(startY + ratioY);

                // Don't go outside image
                stopX = Math.min(stopX, width);
                stopY = Math.min(stopY, height);

                // Process at least one pixel
                stopX = Math.max(startX+1, stopX);
                stopY = Math.max(startY+1, stopY);

                for (int y = startY; y < stopY; y++) {
                    for (int x = startX; x < stopX; x++) {
                        red += get(x, y).getRed();
                        green += get(x, y).getGreen();
                        blue += get(x, y).getBlue();
                        totalPixels++;
                    }
                }

                pixel.setRed(red / (double)totalPixels);
                pixel.setGreen(green / (double)totalPixels);
                pixel.setBlue(blue / (double)totalPixels);
            }
        }

        return target;
    }
}

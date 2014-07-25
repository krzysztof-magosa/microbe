package pl.magosa.microbe;

import java.awt.*;

/**
 * Class represents one pixel.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class Pixel {
    protected double red;
    protected double green;
    protected double blue;

    public Pixel() {
    }

    public Pixel(final int rgb) {
        setRGB(rgb);
    }

    public Pixel(final int red, final int green, final int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public void setRed(final int red) {
        this.red = red / 255.0;
    }

    public void setGreen(final int green) {
        this.green = green / 255.0;
    }

    public void setBlue(final int blue) {
        this.blue = blue / 255.0;
    }

    public void setRed(final double red) {
        this.red = red;
    }

    public void setGreen(final double green) {
        this.green = green;
    }

    public void setBlue(final double blue) {
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public int getRGB() {
        int r = (int)(getRed() * 255.0);
        int g = (int)(getGreen() * 255.0);
        int b = (int)(getBlue() * 255.0);

        return
            ((r & 0xFF) << 16) |
            ((g & 0xFF) << 8)  |
            ((b & 0xFF) << 0);
    }

    public void setRGB(final int rgb) {
        setRed((rgb >> 16) & 0xFF);
        setGreen((rgb >> 8) & 0xFF);
        setBlue((rgb >> 0) & 0xFF);
    }

    public void setRGB(final int red, final int green, final int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public void setRGB(final double red, final double green, final double blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    public void setColor(final Color color) {
        setRGB(color.getRGB());
    }

    public double getRelativeLuminance() {
        return (red * 0.2126) + (green * 0.7152) + (blue * 0.0722);
    }

    public double getGrayBrightness() {
        return (red + green + blue) / 3.0;
    }

    public void setGrayBrightness(final double value) {
        setRed(value);
        setGreen(value);
        setBlue(value);
    }
}

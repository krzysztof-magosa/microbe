package pl.magosa.microbe;

import java.awt.*;

/**
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

    public void setRGB(final int rgb) {
        setRed((rgb >> 16) & 0xFF);
        setGreen((rgb >> 8) & 0xFF);
        setBlue((rgb >> 0) & 0xFF);
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
}

package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class Normalizer {
    protected double inputMin;
    protected double inputMax;
    protected double outputMin;
    protected double outputMax;

    public void setInputMin(final double min) {
        this.inputMin = min;
    }

    public void setInputMax(final double max) {
        this.inputMax = max;
    }

    public void setInputRange(final double min, final double max) {
        this.inputMin = min;
        this.inputMax = max;
    }

    public void setOutputMin(final double min) {
        this.outputMin = min;
    }

    public void setOutputMax(final double max) {
        this.outputMax = max;
    }

    public void setOutputRange(final double min, final double max) {
        this.outputMin = min;
        this.outputMax = max;
    }

    public double normalize(final double value) {
        return outputMin + (((value - inputMin) / (inputMax - inputMin)) * (outputMax - outputMin));
    }

    public double[] normalize(final double[] values) {
        double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = normalize(values[i]);
        }

        return result;
    }

    public double denormalize(final double value) {
        return inputMin + (((value - outputMin) / (outputMax - outputMin)) * (inputMax - inputMin));
    }

    public double[] denormalize(final double[] values) {
        double[] result = new double[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = denormalize(values[i]);
        }

        return result;
    }
}

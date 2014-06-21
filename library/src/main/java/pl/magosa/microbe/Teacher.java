package pl.magosa.microbe;

import java.util.ArrayList;

/**
 * (c) 2014 Krzysztof Magosa
 */
public abstract class Teacher {
    protected double learningRate = 0.75;
    protected double momentum = 0.95;
    protected double lastEpochError;
    protected ArrayList<LearningSet> learningData;

    public Teacher() {
        learningData = new ArrayList<LearningSet>();
    }

    public void setLearningRate(final double rate) {
        learningRate = rate;
    }

    public void setMomentum(final double momentum) {
        this.momentum = momentum;
    }

    public void addLearningSet(final LearningSet set) {
        learningData.add(set);
    }

    public double getError() {
        return lastEpochError;
    }

    abstract public boolean train(int maxEpochs, double maxError);

    public static Teacher factory(Network network) {
        if (network instanceof FeedForwardNetwork) {
            return new FeedForwardTeacher((FeedForwardNetwork)network);
        }

        throw new RuntimeException("Unsupported network type.");
    }
}

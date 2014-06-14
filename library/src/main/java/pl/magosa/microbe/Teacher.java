package pl.magosa.microbe;

/**
 * (c) 2014 Krzysztof Magosa
 */
public abstract class Teacher {
    abstract public void setLearningRate(final double rate);
    abstract public void setMomentum(final double momentum);
    abstract public void addLearningSet(LearningSet set);
    abstract public boolean train(int maxEpochs, double maxError);
    abstract public double getError();

    public static Teacher factory(Network network) {
        if (network instanceof FeedForwardNetwork) {
            return new FeedForwardTeacher((FeedForwardNetwork)network);
        }

        throw new RuntimeException("Unsupported network type.");
    }
}

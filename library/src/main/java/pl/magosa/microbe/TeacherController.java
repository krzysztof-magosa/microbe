package pl.magosa.microbe;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.Map;

/**
 * Class provides adaptive learning rate for specified teacher.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class TeacherController {
    protected Teacher teacher;
    protected double goal;
    protected double learningRate;
    protected double learningRateIncRatio;
    protected double learningRateDecRatio;
    protected double maximumErrorIncRatio;
    protected double maximumLearningRate;
    protected boolean debug;
    protected int debugEveryNEpoches;

    protected double currentError;
    protected double currentErrorBackup;
    protected double lastError;
    protected double lastErrorBackup;
    protected long epoch;
    protected String lastAction;

    public TeacherController(final Teacher teacher) {
        this.teacher = teacher;

        setGoal(0.05);
        setLearningRate(0.01);
        setLearningRateIncRatio(1.05);
        setLearningRateDecRatio(0.70);
        setMaximumErrorIncRatio(1.04);
        setMaximumLearningRate(0.25);
        setDebug(false);
        setDebugInterval(10);
    }

    public void setGoal(final double goal) {
        this.goal = goal;
    }

    public void setLearningRate(final double learningRate) {
        if (learningRate <= 0.0) {
            throw new RuntimeException("learningRate rate must be higher than 0.0");
        }

        if (learningRate > 1.0) {
            throw new RuntimeException("learningRate rate can't be greater than 1.0");
        }

        this.learningRate = learningRate;
    }

    public void setLearningRateIncRatio(final double learningRateIncRatio) {
        if (learningRateIncRatio <= 1.0) {
            throw new RuntimeException("learningRateIncRatio must be higher than 1.0");
        }

        this.learningRateIncRatio = learningRateIncRatio;
    }

    public void setLearningRateDecRatio(final double learningRateDecRatio) {
        if (learningRateDecRatio >= 1.0) {
            throw new RuntimeException("learningRateDecRatio must be lower than 1.0");
        }

        if (learningRateDecRatio <= 0.0) {
            throw new RuntimeException("learningRateDecRatio must be higher than 0.0");
        }

        this.learningRateDecRatio = learningRateDecRatio;
    }

    public void setMaximumErrorIncRatio(final double maximumErrorIncRatio) {
        if (maximumErrorIncRatio <= 1.0) {
            throw new RuntimeException("maximumErrorIncRatio must be higher than 1.0");
        }

        this.maximumErrorIncRatio = maximumErrorIncRatio;
    }

    public void setMaximumLearningRate(final double maximumLearningRate) {
        if (learningRate <= 0.0) {
            throw new RuntimeException("maximumLearningRate rate must be higher than 0.0");
        }

        if (learningRate > 1.0) {
            throw new RuntimeException("maximumLearningRate rate can't be greater than 1.0");
        }

        this.maximumLearningRate = maximumLearningRate;
    }

    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    public void setDebugInterval(final int epoches) {
        if (epoches <= 0) {
            throw new RuntimeException("debugEveryNEpoches must be higher than 0");
        }

        this.debugEveryNEpoches = epoches;
    }

    protected void printDebug() {
        System.out.printf("Epoch          = % d\n", epoch);
        System.out.printf("Previous error = % .10f\n", lastError);
        System.out.printf("Current error  = % .10f\n", currentError);
        System.out.printf("Learning rate  = % .10f\n", learningRate);
        System.out.printf("Last action    = %s\n", lastAction);
        System.out.println();

        lastAction = "";
    }

    /**
     * Performs training, trying to keep learning rate as high as it's possible.
     */
    public void train() {
        teacher.setLearningRate(learningRate);

        teacher.calculateSquaredErrorEpoch();
        lastError = currentError = teacher.getError();
        for (epoch = 1; ; epoch++) {
            currentErrorBackup = currentError;
            lastErrorBackup = lastError;

            lastError = teacher.getError();
            if (teacher.train(1, goal)) {
                break;
            }
            currentError = teacher.getError();

            /**
             * Maximum error increase ratio has been exceeded.
             *  - rollback last epoch
             *  - slow down learning
             */
            if ((currentError / lastError) > maximumErrorIncRatio) {
                teacher.rollback();
                teacher.calculateSquaredErrorEpoch();
                learningRate = learningRate * learningRateDecRatio;

                currentError = currentErrorBackup;
                lastError = lastErrorBackup;

                lastAction = "rollback, slow down";
            }
            /**
             * Error of network is smaller than previous.
             * Try to speed up learning.
             */
            else if (currentError < lastError) {
                learningRate = learningRate * learningRateIncRatio;

                lastAction = "speed up";
            }

            if (learningRate > maximumLearningRate) {
                learningRate = maximumLearningRate;
            }

            teacher.setLearningRate(learningRate);

            if (debug && (epoch % debugEveryNEpoches == 0)) {
                printDebug();
            }
        }
    }
}

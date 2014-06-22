package pl.magosa.microbe;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.Map;

/**
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
    protected int slopeMinEpoches;
    protected int slopeMaxEpoches;
    protected boolean debug;
    protected int debugEveryNEpoches;

    protected double currentError;
    protected double lastError;
    protected double slope;
    protected boolean hasSlope = false;
    protected long epoch;
    protected SimpleRegression regression;
    protected CircularFifoQueue<Double> slopeFifo;

    public TeacherController(final Teacher teacher) {
        this.teacher = teacher;
        regression = new SimpleRegression();

        setGoal(0.05);
        setLearningRate(0.01);
        setLearningRateIncRatio(1.05);
        setLearningRateDecRatio(0.70);
        setMaximumErrorIncRatio(1.04);
        setMaximumLearningRate(0.25);
        setSlopeMinEpoches(20);
        setSlopeMaxEpoches(100);
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

    public void setSlopeMinEpoches(final int slopeMinEpoches) {
        if (slopeMinEpoches <= 0) {
            throw new RuntimeException("slopeMinEpoches must be higher than 0");
        }

        this.slopeMinEpoches = slopeMinEpoches;
    }

    public void setSlopeMaxEpoches(final int slopeMaxEpoches) {
        if (slopeMaxEpoches <= 0) {
            throw new RuntimeException("slopeMaxEpoches must be higher than 0");
        }

        this.slopeMaxEpoches = slopeMaxEpoches;
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
        if (hasSlope) {
            System.out.printf("Error slope    = % .10f\n", slope);
        }
        else {
            System.out.printf("Error slope    =  not yet\n");
        }

        System.out.printf("Learning rate  = % .10f\n", learningRate);
        System.out.println();
    }

    public void train() {
        slopeFifo = new CircularFifoQueue<>(slopeMaxEpoches);
        teacher.setLearningRate(learningRate);

        for (epoch = 1; ; epoch++) {
            lastError = teacher.getError();
            if (teacher.train(1, goal)) {
                break;
            }
            currentError = teacher.getError();

            slopeFifo.add(currentError);
            if (slopeFifo.size() >= slopeMinEpoches) {
                regression.clear();

                int x = 0;
                for (Double y : slopeFifo) {
                    regression.addData(x++, y);
                }

                slope = regression.getSlope();
                hasSlope = true;
            }

            if ((currentError / lastError) > maximumErrorIncRatio) {
                teacher.rollback();
                learningRate = learningRate * learningRateDecRatio;
            }
            else if (hasSlope && (slope < -0.00001)) {
                learningRate = learningRate * learningRateIncRatio;
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

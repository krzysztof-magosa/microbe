package pl.magosa.microbe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class FeedForwardTeacher extends Teacher<FeedForwardNetwork> {
    protected Random randomGenerator;
    protected FeedForwardLayer outputLayer;
    protected ArrayList<FeedForwardLayer> workingLayers;
    protected HashMap<Integer, Double> prevCorrection;

    public FeedForwardTeacher(FeedForwardNetwork network) {
        this.network = network;

        learningData = new ArrayList<LearningSet>();
        randomGenerator = new Random();

        outputLayer = network.getLayers().get(network.getLayers().size() - 1);
        workingLayers = new ArrayList<FeedForwardLayer>();
        for (int i = network.getLayers().size() - 1; i > 0 ; i--) {
            workingLayers.add(network.getLayers().get(i));
        }

        prevCorrection = new HashMap<Integer, Double>();
        for (FeedForwardLayer layer : workingLayers) {
            for (Neuron neuron : layer.getNeurons()) {
                prevCorrection.put(System.identityHashCode(neuron), 0.0);
            }
        }
    }

    protected void backPropagate(final double[] desired) {
        HashMap<Integer, Double> errorGradient = new HashMap<Integer, Double>();

        for (FeedForwardLayer layer : workingLayers) {
            for (int index = 0; index < layer.getNeurons().size(); index++) {
                Neuron neuron = layer.getNeurons().get(index);
                int hashId = System.identityHashCode(neuron);

                if (layer == outputLayer) {
                    errorGradient.put(hashId, (desired[index] - neuron.getOutput()) * neuron.getTransferFunction().derivative(neuron.getOutput()));
                }
                else {
                    double productSum = 0;
                    for (Neuron nlNeuron : layer.getNextLayer().getNeurons()) {
                        int nlHashId = System.identityHashCode(nlNeuron);
                        productSum += (errorGradient.get(nlHashId) * nlNeuron.getInputs().get(index).getWeight());
                    }

                    errorGradient.put(hashId, neuron.getTransferFunction().derivative(neuron.getOutput()) * productSum);
                }

                for (Input input : neuron.getInputs()) {
                    double correction = learningRate * input.getValue() * errorGradient.get(hashId);
                    double newWeight = input.getWeight() + correction + (momentum * prevCorrection.get(hashId));

                    input.setWeight(newWeight);
                    prevCorrection.put(hashId, correction);
                }

                double thresholdCorrection = learningRate * -1.0 * errorGradient.get(hashId);
                neuron.applyThresholdCorrection(thresholdCorrection);
            }
        }
    }

    public boolean train(int maxEpochs, double maxError) {
        for (int epoch = 1; epoch <= maxEpochs; epoch++) {
            lastEpochError = squaredErrorEpoch();

            // Don't train already trained network
            if (lastEpochError <= maxError) {
                return true;
            }

            for (int index = 0; index < learningData.size(); index++) {
                LearningSet set = learningData.get(index);

                network.setValues(set.getInput());
                network.run();

                backPropagate(set.getOutput());
            }

            Collections.shuffle(learningData);
        }

        return false;
    }
}

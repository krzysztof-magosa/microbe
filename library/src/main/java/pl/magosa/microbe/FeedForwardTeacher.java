package pl.magosa.microbe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class represents back propagation teacher for feed forward network.
 *
 * (c) 2014 Krzysztof Magosa
 */
public class FeedForwardTeacher extends Teacher<FeedForwardNetwork> {
    protected FeedForwardLayer outputLayer;
    protected ArrayList<FeedForwardLayer> workingLayers;
    protected HashMap<Integer, Double> prevWeightCorrection;
    protected HashMap<Integer, Double> prevWeightCorrectionBackup;
    protected NetworkKnowledge knowledgeBackup;

    public FeedForwardTeacher(FeedForwardNetwork network) {
        this.network = network;

        outputLayer = network.getLayers().get(network.getLayers().size() - 1);
        workingLayers = new ArrayList<>();
        for (int i = network.getLayers().size() - 1; i > 0 ; i--) {
            workingLayers.add(network.getLayers().get(i));
        }

        prevWeightCorrection = new HashMap<>();
        for (FeedForwardLayer layer : workingLayers) {
            for (Neuron neuron : layer.getNeurons()) {
                prevWeightCorrection.put(System.identityHashCode(neuron), 0.0);
            }
        }

        prevWeightCorrectionBackup = new HashMap<>();
        knowledgeBackup = new NetworkKnowledge();
    }

    /**
     * Back propagates errors through all layers from output to first hidden.
     * @param desired Expected output of network.
     */
    protected void backPropagate(final double[] desired) {
        HashMap<Integer, Double> errorGradient = new HashMap<>();

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
                        productSum += (errorGradient.get(nlHashId) * nlNeuron.getInput(index).getWeight());
                    }

                    errorGradient.put(hashId, neuron.getTransferFunction().derivative(neuron.getOutput()) * productSum);
                }

                for (Input input : neuron.getInputs()) {
                    double correction = learningRate * input.getValue() * errorGradient.get(hashId);
                    double newWeight = input.getWeight() + correction + (momentum * prevWeightCorrection.get(hashId));

                    input.setWeight(newWeight);
                    prevWeightCorrection.put(hashId, correction);
                }

                double thresholdCorrection = learningRate * -1.0 * errorGradient.get(hashId);
                neuron.applyThresholdCorrection(thresholdCorrection);
            }
        }
    }

    /**
     * Backups thresholds and weights of all neurons, so last step may be rollbacked.
     */
    protected void backupParameters() {
        prevWeightCorrectionBackup.clear();
        prevWeightCorrectionBackup.putAll(prevWeightCorrection);

        knowledgeBackup.transferFromNetwork(network);
    }

    /**
     * Performs rollback restoring thresholds and weights of all neurons to previous state.
     */
    public void rollback() {
        prevWeightCorrection.clear();
        prevWeightCorrection.putAll(prevWeightCorrectionBackup);

        knowledgeBackup.transferToNetwork(network);
    }

    /**
     * Performs one epoch of training
     */
    protected void trainEpoch() {
        for (int index = 0; index < learningData.size(); index++) {
            LearningSet set = learningData.get(index);

            network.setValues(set.getInput());
            network.run();

            backPropagate(set.getOutput());
        }
    }
}

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
    protected Layer outputLayer;
    protected ArrayList<Layer> workingLayers;
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
        for (Layer layer : workingLayers) {
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

        for (Layer layer : workingLayers) {
            for (int index = 0; index < layer.getNeurons().size(); index++) {
                Neuron neuron = layer.getNeurons().get(index);

                if (neuron instanceof BiasNeuron) {
                    continue;
                }

                int hashId = System.identityHashCode(neuron);
                double output = neuron.getOutput();

                if (layer == outputLayer) {
                    double error = (desired[index] - output);
                    double gradient = error * neuron.getTransferFunction().derivative(output);
                    errorGradient.put(hashId, gradient);
                }
                else {
                    double productSum = 0;
                    for (Connection connection : neuron.getOutputConnections()) {
                        int nlHashId = System.identityHashCode(connection.getDestination());
                        productSum += (errorGradient.get(nlHashId) * connection.getWeight());
                    }

                    errorGradient.put(hashId, neuron.getTransferFunction().derivative(output) * productSum);
                }

                for (Connection connection : neuron.getInputConnections()) {
                    double correction = learningRate * connection.getInput() * errorGradient.get(hashId);
                    double newWeight = connection.getWeight() + correction + (momentum * prevWeightCorrection.get(hashId));

                    connection.setWeight(newWeight);
                    prevWeightCorrection.put(hashId, correction);
                }
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

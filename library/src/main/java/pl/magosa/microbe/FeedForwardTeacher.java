package pl.magosa.microbe;

import java.util.ArrayDeque;
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
    protected NetworkKnowledge knowledgeBackup;

    public FeedForwardTeacher(FeedForwardNetwork network) {
        this.network = network;

        outputLayer = network.getLayers().get(network.getLayers().size() - 1);
        workingLayers = new ArrayList<>();
        for (int i = network.getLayers().size() - 1; i > 0 ; i--) {
            workingLayers.add(network.getLayers().get(i));
        }

        for (Layer layer : workingLayers) {
            for (Neuron neuron : layer.getNeurons()) {
                neuron.setLearningData(new BPLearningData());
            }
        }

        knowledgeBackup = new NetworkKnowledge();
    }

    /**
     * Back propagates errors through all layers from output to first hidden.
     * @param desired Expected output of network.
     */
    protected void backPropagate(final double[] desired) {
        for (Layer layer : workingLayers) {
            for (int index = 0; index < layer.getNeurons().size(); index++) {
                Neuron neuron = layer.getNeurons().get(index);

                if (neuron instanceof BiasNeuron) {
                    continue;
                }

                BPLearningData data = (BPLearningData)neuron.getLearningData();
                double output = neuron.getOutput();

                if (layer == outputLayer) {
                    data.gradient = (desired[index] - output) * neuron.getTransferFunction().derivative(output);
                }
                else {
                    double productSum = 0;
                    for (Connection connection : neuron.getOutputConnections()) {
                        BPLearningData nlData = (BPLearningData)connection.getDestination().getLearningData();
                        productSum += (nlData.gradient * connection.getWeight());
                    }

                    data.gradient = neuron.getTransferFunction().derivative(output) * productSum;
                }

                for (Connection connection : neuron.getInputConnections()) {
                    double correction = learningRate * connection.getInput() * data.gradient;
                    connection.incWeight(correction + (momentum * data.correction));

                    data.correction = correction;
                }
            }
        }
    }

    /**
     * Backups thresholds and weights of all neurons, so last step may be rollbacked.
     */
    protected void backupParameters() {
        for (Layer layer : workingLayers) {
            for (Neuron neuron : layer.getNeurons()) {
                BPLearningData data = (BPLearningData)neuron.getLearningData();
                data.backup();
            }
        }

        knowledgeBackup.transferFromNetwork(network);
    }

    /**
     * Performs rollback restoring thresholds and weights of all neurons to previous state.
     */
    public void rollback() {
        for (Layer layer : workingLayers) {
            for (Neuron neuron : layer.getNeurons()) {
                BPLearningData data = (BPLearningData)neuron.getLearningData();
                data.rollback();
            }
        }

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

    protected class BPLearningData {
        protected double gradient;
        protected double correction;
        protected double correctionBackup;

        public void rollback() {
            correction = correctionBackup;
        }

        public void backup() {
            correctionBackup = correction;
        }
    }
}

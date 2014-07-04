package pl.magosa.microbe;

import org.junit.Test;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class FeedForwardNetworkTest {
    @Test(expected = IllegalStateException.class)
    public void noParameters() {
        FeedForwardNetwork network = FeedForwardNetwork.newInstance()
            .build();
    }

    @Test(expected = IllegalStateException.class)
    public void lackOfInputNeurons() {
        FeedForwardNetwork.Builder builder = FeedForwardNetwork.newInstance();

        builder
            .inputLayer(0)
            .hiddenLayer(1, new SigmoidTransferFunction())
            .outputLayer(1, new SigmoidTransferFunction())
            .build();

        builder.build();
    }

    @Test(expected = IllegalStateException.class)
    public void lackOfInputLayer() {
        FeedForwardNetwork network = FeedForwardNetwork.newInstance()
            .hiddenLayer(1, new SigmoidTransferFunction())
            .outputLayer(1, new SigmoidTransferFunction())
            .build();
    }

    @Test(expected = IllegalStateException.class)
    public void lackOfHiddenLayer() {
        FeedForwardNetwork network = FeedForwardNetwork.newInstance()
            .inputLayer(1)
            .outputLayer(1, new SigmoidTransferFunction())
            .build();
    }

    @Test(expected = IllegalStateException.class)
    public void lackOfOutputLayer() {
        FeedForwardNetwork network = FeedForwardNetwork.newInstance()
            .inputLayer(1)
            .hiddenLayer(1, new SigmoidTransferFunction())
            .build();
    }

    @Test(expected = IllegalStateException.class)
    public void twiceBuild() {
        FeedForwardNetwork.Builder builder = FeedForwardNetwork.newInstance();

        builder
            .inputLayer(1)
            .hiddenLayer(1, new SigmoidTransferFunction())
            .outputLayer(1, new SigmoidTransferFunction())
            .build();

        builder.build();
    }
}

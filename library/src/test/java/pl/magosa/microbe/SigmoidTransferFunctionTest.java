package pl.magosa.microbe;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class SigmoidTransferFunctionTest {
    private static TransferFunction transferFunction;

    @BeforeClass
    public static void setUp() {
        transferFunction = new SigmoidTransferFunction();
    }

    @Test
    public void testFunction() {
        assertEquals(0.67939655511586, transferFunction.function(0.751), 0.001);
        assertEquals(0.55749456313761, transferFunction.function(0.231), 0.001);
        assertEquals(0.5, transferFunction.function(0.0), 0.001);
        assertEquals(0.73105857863, transferFunction.function(1.0), 0.001);
    }

    @Test
    public void testDerivative() {
        assertEquals(0.248580, transferFunction.derivative(transferFunction.function(0.151)), 0.001);
        assertEquals(0.654003, transferFunction.function(transferFunction.function(0.561)), 0.001);
    }

    @Test
    public void testGetUpperLimit() {
        assertEquals(1.0, transferFunction.getUpperLimit(), 0.001);
    }

    @Test
    public void testGetLowerLimit() {
        assertEquals(0.0, transferFunction.getLowerLimit(), 0.001);
    }

    @Test
    public void makeArray() {
        double[] result = transferFunction.makeArray(5, new int[] { 1, 2 });
        assertArrayEquals(new double[] { 0.0, 1.0, 1.0, 0.0, 0.0 }, result, 0.01);
    }
}

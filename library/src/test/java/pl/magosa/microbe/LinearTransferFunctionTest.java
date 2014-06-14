package pl.magosa.microbe;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class LinearTransferFunctionTest {
    private static TransferFunction transferFunction;

    @BeforeClass
    public static void setUpClass() {
        transferFunction = new LinearTransferFunction();
    }

    @Test
    public void testFunction() {
        assertEquals(0.5, transferFunction.function(0.5), 0.001);
        assertEquals(0.1, transferFunction.function(0.1), 0.001);
    }

    @Test(expected = RuntimeException.class)
    public void testDerivative() {
        transferFunction.derivative(1);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUpperLimit() {
        transferFunction.getUpperLimit();
    }

    @Test(expected = RuntimeException.class)
    public void testGetLowerLimit() {
        transferFunction.getLowerLimit();
    }
}

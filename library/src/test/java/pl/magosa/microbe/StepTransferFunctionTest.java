package pl.magosa.microbe;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class StepTransferFunctionTest {
    private static TransferFunction transferFunction;

    @BeforeClass
    public static void setUpClass() {
        transferFunction = new StepTransferFunction();
    }

    @Test
    public void testFunction() {
        assertEquals(0.0, transferFunction.function(-100.0), 0.0);
        assertEquals(0.0, transferFunction.function(-0.5), 0.0);
        assertEquals(0.0, transferFunction.function(-0.01), 0.0);

        assertEquals(1.0, transferFunction.function(0.0), 0.0);
        assertEquals(1.0, transferFunction.function(0.01), 0.0);
        assertEquals(1.0, transferFunction.function(100), 0.0);
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

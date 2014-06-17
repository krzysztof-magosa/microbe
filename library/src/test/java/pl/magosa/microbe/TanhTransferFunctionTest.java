package pl.magosa.microbe;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class TanhTransferFunctionTest {
    private static TransferFunction transferFunction;

    @BeforeClass
    public static void setUp() {
        transferFunction = new TanhTransferFunction();
    }

    @Test
    public void testFunction() {
        assertEquals(0.76159415595576, transferFunction.function(1.0), 0.001);
        assertEquals(0.0, transferFunction.function(0.0), 0.001);
        assertEquals(0.46211715726001, transferFunction.function(0.5), 0.001);
        assertEquals(-0.76159415595576, transferFunction.function(-1.0), 0.001);
    }

    @Test
    public void testDerivative() {
        assertEquals(0.97754116486012, transferFunction.derivative(transferFunction.function(0.151)), 0.001);
        assertEquals(0.74120496390821, transferFunction.derivative(transferFunction.function(0.561)), 0.001);
    }

    @Test
    public void testGetUpperLimit() {
        assertEquals(1.0, transferFunction.getUpperLimit(), 0.001);
    }

    @Test
    public void testGetLowerLimit() {
        assertEquals(-1.0, transferFunction.getLowerLimit(), 0.001);
    }
}

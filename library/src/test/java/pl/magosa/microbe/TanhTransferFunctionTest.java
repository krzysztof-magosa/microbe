package pl.magosa.microbe;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
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

    @Test
    public void makeArray() {
        double[] result = transferFunction.makeArray(5, new int[] { 1, 2 });
        assertArrayEquals(new double[] { -1.0, 1.0, 1.0, -1.0, -1.0 }, result, 0.01);
    }

    @Test
    public void normalize() {
        // Normal range                                val   min   max
        assertEquals(-1.0, transferFunction.normalize(   0,    0,    5), 0.01);
        assertEquals(-0.5, transferFunction.normalize( 250,    0, 1000), 0.01);
        assertEquals( 0.0, transferFunction.normalize( 128,    0,  255), 0.01);

        // Range with negative minimum                 val   min   max
        assertEquals(-1.0, transferFunction.normalize(-100, -100,  100), 0.01);
        assertEquals( 0.0, transferFunction.normalize(   0, -100,  100), 0.01);
        assertEquals( 1.0, transferFunction.normalize( 100, -100,  100), 0.01);

        // Reversed range                              val   min   max
        assertEquals( 1.0, transferFunction.normalize(   0,    5,    0), 0.01);
        assertEquals( 0.0, transferFunction.normalize( 128,  255,    0), 0.01);
        assertEquals( 0.5, transferFunction.normalize( 250, 1000,    0), 0.01);

        // Reversed range with negative maximum        val   min   max
        assertEquals( 1.0, transferFunction.normalize(-100,  100, -100), 0.01);
        assertEquals( 0.0, transferFunction.normalize(   0,  100, -100), 0.01);
        assertEquals(-1.0, transferFunction.normalize( 100,  100, -100), 0.01);
    }
}

package pl.magosa.microbe;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class InputTest {
    private static Input input;

    @BeforeClass
    public static void setUpClass() {
        input = new Input();
    }

    @Test
    public void testWeightedValue() {
        input.setValue(0.6);
        input.setWeight(0.3);
        assertEquals(0.18, input.getWeightedValue(), 0.01);

        input.setValue(1.0);
        input.setWeight(0.0);
        assertEquals(0.0, input.getWeightedValue(), 0.01);
    }
}

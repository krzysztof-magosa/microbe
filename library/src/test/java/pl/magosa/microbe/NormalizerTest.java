package pl.magosa.microbe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * (c) 2014 Krzysztof Magosa
 */
public class NormalizerTest {
    @Test
    public void normalizeNormal() {
        Normalizer normalizer = new Normalizer();
        normalizer.setInputRange(0.0, 255.0);
        normalizer.setOutputRange(0.0, 1.0);

        assertEquals(0.0, normalizer.normalize(0.0), 0.001);
        assertEquals(0.5, normalizer.normalize(127.5), 0.001);
        assertEquals(1.0, normalizer.normalize(255.0), 0.001);
    }

    @Test
    public void normalizeNegative() {
        Normalizer normalizer = new Normalizer();
        normalizer.setInputRange(-200.0, -100.0);
        normalizer.setOutputRange(0.0, 1.0);

        assertEquals(0.0, normalizer.normalize(-200.0), 0.001);
        assertEquals(0.5, normalizer.normalize(-150.0), 0.001);
        assertEquals(1.0, normalizer.normalize(-100.0), 0.001);
    }

    @Test
    public void normalizeReverse() {
        Normalizer normalizer = new Normalizer();
        normalizer.setInputRange(-100.0, -200.0);
        normalizer.setOutputRange(0.0, 1.0);

        assertEquals(0.0, normalizer.normalize(-100.0), 0.001);
        assertEquals(0.5, normalizer.normalize(-150.0), 0.001);
        assertEquals(1.0, normalizer.normalize(-200.0), 0.001);
    }

    @Test
    public void denormalizeNormal() {
        Normalizer normalizer = new Normalizer();
        normalizer.setInputRange(0.0, 255.0);
        normalizer.setOutputRange(0.0, 1.0);

        assertEquals(0.0, normalizer.denormalize(0.0), 0.001);
        assertEquals(127.5, normalizer.denormalize(0.5), 0.001);
        assertEquals(255.0, normalizer.denormalize(1.0), 0.001);
    }

    @Test
    public void denormalizeNegative() {
        Normalizer normalizer = new Normalizer();
        normalizer.setInputRange(-200.0, -100.0);
        normalizer.setOutputRange(0.0, 1.0);

        assertEquals(-200.0, normalizer.denormalize(0.0), 0.001);
        assertEquals(-150.0, normalizer.denormalize(0.5), 0.001);
        assertEquals(-100.0, normalizer.denormalize(1.0), 0.001);
    }

    @Test
    public void denormalizeReverse() {
        Normalizer normalizer = new Normalizer();
        normalizer.setInputRange(-100.0, -200.0);
        normalizer.setOutputRange(0.0, 1.0);

        assertEquals(-100.0, normalizer.denormalize(0.0), 0.001);
        assertEquals(-150.0, normalizer.denormalize(0.5), 0.001);
        assertEquals(-200.0, normalizer.denormalize(1.0), 0.001);
    }
}

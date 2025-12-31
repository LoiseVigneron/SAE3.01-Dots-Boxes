package fr.univlille.dotsboxes.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTest {

    @Test
    void testConstructorAndAccessors() {
        Tuple<String, Integer> tuple = new Tuple<>("Alice", 42);

        assertEquals("Alice", tuple.first(), "Le premier élément doit être correctement stocké.");
        assertEquals(42, tuple.second(), "Le second élément doit être correctement stocké.");
    }

    @Test
    void testEqualsAndHashCodeSameValues() {
        Tuple<String, Integer> t1 = new Tuple<>("Bob", 10);
        Tuple<String, Integer> t2 = new Tuple<>("Bob", 10);

        assertEquals(t1, t2, "Deux tuples avec les mêmes valeurs doivent être égaux.");
        assertEquals(t1.hashCode(), t2.hashCode(), "Deux tuples égaux doivent avoir le même hashCode.");
    }

    @Test
    void testEqualsDifferentValues() {
        Tuple<String, Integer> t1 = new Tuple<>("Alice", 1);
        Tuple<String, Integer> t2 = new Tuple<>("Alice", 2);
        Tuple<String, Integer> t3 = new Tuple<>("Bob", 1);

        assertNotEquals(t1, t2, "Des tuples avec des seconds éléments différents ne doivent pas être égaux.");
        assertNotEquals(t1, t3, "Des tuples avec des premiers éléments différents ne doivent pas être égaux.");
    }

    @Test
    void testEqualsWithNull() {
        Tuple<String, Integer> t1 = new Tuple<>("Alice", 1);

        assertNotEquals(t1, null, "Un tuple ne doit pas être égal à null.");
    }

    @Test
    void testEqualsWithDifferentType() {
        Tuple<String, Integer> t1 = new Tuple<>("Alice", 1);

        assertNotEquals(t1, "Alice", "Un tuple ne doit pas être égal à un objet d'un autre type.");
    }

    @Test
    void testToStringFormat() {
        Tuple<String, Integer> tuple = new Tuple<>("Alice", 42);

        String toString = tuple.toString();

        assertTrue(toString.contains("Alice"), "Le toString doit contenir la valeur du premier élément.");
        assertTrue(toString.contains("42"), "Le toString doit contenir la valeur du second élément.");
        assertTrue(toString.startsWith("Tuple"), "Le toString doit commencer par le nom du record.");
    }
}

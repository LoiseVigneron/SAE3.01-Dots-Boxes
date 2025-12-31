package fr.univlille.dotsboxes.grid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArreteTest {

    private Arrete arrete;
    private Point p1;
    private Point p2;

    @BeforeEach
    void setUp() {
        p1 = new Point(0, 0);
        p2 = new Point(1, 1);
        arrete = new Arrete(p1, p2);
    }

    @Test
    void testInitialisationPoints() {
        assertEquals(p1, arrete.getPointOne(), "Le pointA doit être initialisé correctement.");
        assertEquals(p2, arrete.getPointTwo(), "Le pointB doit être initialisé correctement.");
    }

    @Test
    void testSelectionParDefaut() {
        assertFalse(arrete.getSelection(), "Par défaut, une arête ne doit pas être sélectionnée.");
    }

    @Test
    void testSetSelectionTrue() {
        arrete.setSelected(true);
        assertTrue(arrete.getSelection(), "Après setSelection(true), l'arête doit être sélectionnée.");
    }

    @Test
    void testSetSelectionFalse() {
        arrete.setSelected(true);
        arrete.setSelected(false);
        assertFalse(arrete.getSelection(), "Après setSelection(false), l'arête ne doit plus être sélectionnée.");
    }
}

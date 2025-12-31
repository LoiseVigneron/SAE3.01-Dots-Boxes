package fr.univlille.dotsboxes.grid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PointTest {

    private Point point;

    @BeforeEach
    void setUp() {
        point = new Point(2, 3);
    }

    @Test
    void testInitialisation() {
        assertEquals(2, point.getX(), "coorX doit être initialisé correctement.");
        assertEquals(3, point.getY(), "coorY doit être initialisé correctement.");
    }
}

package fr.univlille.dotsboxes.grid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(4, 3);
    }

    @Test
    void testInitialisationDimensions() {
        assertEquals(4, grid.getMaxX(), "maxX doit être initialisé correctement.");
        assertEquals(3, grid.getMaxY(), "maxY doit être initialisé correctement.");
    }

    @Test
    void testNombrePoints() {
        List<List<Point>> points = grid.getPointsList();
        assertEquals(3, points.size(), "On doit avoir maxY lignes de points.");
        assertEquals(4, points.get(0).size(), "Chaque ligne doit contenir maxX points.");
    }

    @Test
    void testNombreArretes() {
        List<List<Arrete>> arretes = grid.getEdgesList();
        assertEquals(2, arretes.size(), "Il doit y avoir 2 listes d'arêtes (horizontales et verticales).");

        int horizontales = arretes.get(0).size();
        int verticales = arretes.get(1).size();

        assertEquals((4 - 1) * 3, horizontales, "Le nombre d'arêtes horizontales doit être (maxX-1)*maxY.");
        assertEquals((3 - 1) * 4, verticales, "Le nombre d'arêtes verticales doit être (maxY-1)*maxX.");
    }

    @Test
    void testNombreCases() {
        List<List<Case>> cases = grid.getCasesList();
        assertEquals(2, cases.size(), "On doit avoir maxY-1 lignes de cases.");
        assertEquals(3, cases.get(0).size(), "Chaque ligne doit contenir maxX-1 cases.");
    }

    @Test
    void testArreteSelection() {
        Arrete a = grid.getEdgesList().get(0).get(0);
        assertFalse(a.getSelection(), "Par défaut une arête ne doit pas être sélectionnée.");

        a.setSelected(true);
        assertTrue(a.getSelection(), "Après setSelection(true), l’arête doit être sélectionnée.");
    }

    @Test
    void testAffiche() {
        // Redirection de la sortie standard
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        grid.display();

        String output = outContent.toString();

        assertTrue(output.contains("O"), "L'affichage doit contenir des sommets O.");
        assertTrue(output.contains("═") || output.contains("━"), "L'affichage doit contenir des arêtes horizontales.");

        System.setOut(System.out);
    }
}

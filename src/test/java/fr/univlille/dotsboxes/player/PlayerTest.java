package fr.univlille.dotsboxes.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Alice", "o");
    }

    @Test
    void testInitialisation() {
        assertEquals("Alice", player.getName(), "Le username devrait être initialisé correctement.");
        assertEquals(0, player.getScore(), "Le score initial devrait être 0.");
    }

    @Test
    void testGetUsername() {
        assertEquals("Alice", player.getName());
    }

    @Test
    void testGetScoreInitiallyZero() {
        assertEquals(0, player.getScore());
    }

    @Test
    void testAddScorePositive() {
        player.setScore(5);
        assertEquals(5, player.getScore(), "Le score devrait augmenter après un ajout positif.");
    }

    @Test
    void testAddScoreMultipleCalls() {
        player.incrementScore();
        assertEquals(1, player.getScore(), "Le score devrait être la somme des ajouts.");
    }

    @Test
    void testAddScoreZero() {
        player.setScore(0);
        assertEquals(0, player.getScore(), "Le score ne devrait pas changer après un ajout de 0.");
    }

    @Test
    void testAddScoreNegative() {
        player.setScore(-2);
        assertEquals(-2, player.getScore(), "Le score devrait accepter des valeurs négatives si on les ajoute.");
    }
}

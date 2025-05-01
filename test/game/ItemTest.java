package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemTest {
    private Item sword;
    private Item chest;

    @BeforeEach
    void setUp() {
        sword = new Item("Sword", "A sharp steel sword", true);
        chest = new Item("Chest", "A large wooden chest", false);
    }

    @Test
    void testGetName() {
        assertEquals("Sword", sword.getName(), "Item name should be Sword");
        assertEquals("Chest", chest.getName(), "Item name should be Chest");
    }

    @Test
    void testGetDescription() {
        assertEquals("A sharp steel sword", sword.getDescription(), "Sword description should match");
        assertEquals("A large wooden chest", chest.getDescription(), "Chest description should match");
    }

    @Test
    void testIsPortable() {
        assertTrue(sword.isPortable(), "Sword should be portable");
        assertFalse(chest.isPortable(), "Chest should not be portable");
    }
}

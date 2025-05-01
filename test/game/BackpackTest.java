package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BackpackTest {
    private Backpack backpack;
    private Item sword;
    private Item shield;

    @BeforeEach
    void setUp() {
        backpack = new Backpack(2); // Устанавливаем вместимость рюкзака 2
        sword = new Item("Sword", "Old sword", true);
        shield = new Item("Shield", "Wooden shield", true);
    }

    @Test
    void testAddItemSuccess() {
        assertTrue(backpack.addItem(sword), "Sword should be added successfully");
        assertTrue(backpack.addItem(shield), "Shield should be added successfully");
    }

    @Test
    void testAddItemFailsWhenFull() {
        backpack.addItem(sword);
        backpack.addItem(shield);
        Item potion = new Item("Health Potion", "something", true);
        assertFalse(backpack.addItem(potion), "Should not be able to add item when backpack is full");
    }

    @Test
    void testRemoveItem() {
        backpack.addItem(sword);
        assertTrue(backpack.removeItem(sword), "Sword should be removed successfully");
        assertFalse(backpack.getItems().contains(sword), "Backpack should not contain sword after removal");
    }

    @Test
    void testGetCapacity() {
        assertEquals(2, backpack.getCapacity(), "Backpack capacity should be 2");
    }
}

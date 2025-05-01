package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.List;

class RoomTest {
    private Room hall;
    private Room kitchen;
    private Item sword;
    private Character warrior;

    @BeforeEach
    void setUp() {
        hall = new Room("Hall", "A large hall with a chandelier.");
        kitchen = new Room("Kitchen", "A small kitchen with a fireplace.");
        sword = new Item("Sword", "A sharp steel sword", true);
        warrior = new Character("Warrior", "A brave warrior");
    }

    @Test
    void testGetName() {
        assertEquals("Hall", hall.getName(), "Room name should be Hall");
    }

    @Test
    void testGetDescription() {
        assertEquals("A large hall with a chandelier.", hall.getDescription(), "Room description should match");
    }

    @Test
    void testSetAndGetExits() {
        hall.setExit("north", kitchen);
        Map<String, Room> exits = hall.getExits();
        assertTrue(exits.containsKey("north"), "Exit to the north should exist");
        assertEquals(kitchen, exits.get("north"), "North exit should lead to the kitchen");
    }

    @Test
    void testAddAndGetItems() {
        hall.addItem(sword);
        List<Item> items = hall.getItems();
        assertTrue(items.contains(sword), "Room should contain the sword");
    }

    @Test
    void testAddAndRemoveCharacters() {
        hall.addCharacter(warrior);
        assertTrue(hall.getCharacters().contains(warrior), "Room should contain the warrior");
        hall.removeCharacter(warrior);
        assertFalse(hall.getCharacters().contains(warrior), "Room should not contain the warrior after removal");
    }
}

package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GamePlanTest {
    private GamePlan gamePlan;

    @BeforeEach
    void setUp() {
        gamePlan = new GamePlan();
    }

    @Test
    void testInitialRoom() {
        assertEquals("Entrance", gamePlan.getCurrentRoom().getName(), "Initial room should be Entrance");
    }

    @Test
    void testRoomTransitions() {
        gamePlan.setCurrentRoom(gamePlan.getCurrentRoom().getExits().get("hall"));
        assertEquals("Hall", gamePlan.getCurrentRoom().getName(), "Player should be in Hall after moving from Entrance");
    }

    @Test
    void testGetItemFromRoom() {
        gamePlan.setCurrentRoom(gamePlan.getCurrentRoom().getExits().get("hall")); // Переход в зал
        Item memorial = gamePlan.getItem("Memorial");
        assertNotNull(memorial, "Memorial should be found in Hall");
        assertEquals("Memorial", memorial.getName(), "Found item should be Memorial");
    }

    @Test
    void testGetItemFromBackpack() {
        Item book = gamePlan.getItem("Book");
        assertNotNull(book, "Book should be found in the backpack");
        assertEquals("Book", book.getName(), "Found item should be Book");
    }

    @Test
    void testGameWinLoseState() {
        gamePlan.setGameWon(true);
        assertTrue(gamePlan.isGameWon(), "Game should be marked as won");
        gamePlan.setGameLoosed(true);
        assertTrue(gamePlan.isGameLoosed(), "Game should be marked as lost");
    }

    @Test
    void testGetRandomRoom() {
        Room randomRoom = gamePlan.getRandomRoom();
        assertNotNull(randomRoom, "Random room should not be null");
    }
}

package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandHandlerTest {
    private CommandHandler commandHandler;
    private GamePlan gamePlan;
    private Character orc;

    @BeforeEach
    void setUp() {
        gamePlan = new GamePlan();
        commandHandler = new CommandHandler(gamePlan);
    }

    @Test
    void testGoValidDirection() {
        String response = commandHandler.handleCommand("go", "hall");
        assertEquals("The main hall of the fortress. Uther is waiting for you here.\nCharacters here: Uther\nItems here: Memorial", response, "Going to hall should return correct description");
    }

    @Test
    void testGoInvalidDirection() {
        String response = commandHandler.handleCommand("go", "nowhere");
        assertEquals("You can't go that way.", response, "Invalid direction should return correct error message");
    }

    @Test
    void testTakeItem() {
        Room altar = new Room("altar", "An ancient altar room.");
        gamePlan.setCurrentRoom(altar);
        Item totem = new Item("Totem", "A sacred totem", true);
        altar.addItem(totem);

        String response = commandHandler.handleCommand("take", "Totem");
        assertEquals("You took Totem.", response, "Taking a portable item should succeed");
        assertTrue(gamePlan.getBackpack().getItems().contains(totem), "Totem should be in the backpack");
    }

    @Test
    void testTakeNonPortableItem() {
        gamePlan.setCurrentRoom(gamePlan.getCurrentRoom().getExits().get("hall"));
        String response = commandHandler.handleCommand("take", "Memorial");
        assertEquals("You can't take that item.", response, "Non-portable item should not be taken");
    }

    @Test
    void testUseTotem() {
        gamePlan.getBackpack().addItem(new Item("Totem", "The war totem.", true));
        String response = commandHandler.handleCommand("use", "Totem");
        assertEquals("You used the Totem and saw a vision of the fall of Lorderon!", response, "Using the Totem should trigger the correct event");
        assertTrue(gamePlan.isGameWon(), "Game should be won after using Totem");
    }

    @Test
    void testTalkToCharacter() {
        gamePlan.setCurrentRoom(gamePlan.getCurrentRoom().getExits().get("hall"));
        String response = commandHandler.handleCommand("talk", "Uther");
        assertEquals("You talked to Uther.\nArthas, you must find totem, it's somewhere near", response, "Talking to Uther should return the correct message");
    }

    @Test
    void testAttackCharacter() {
        orc = new Character("Orc", "A fierce orc");
        gamePlan.getCurrentRoom().addCharacter(orc);

        String response = commandHandler.handleCommand("attack", "Orc");

        assertEquals("You killed Orc!", response, "Attacking the Orc should return the correct message.");
        assertFalse(gamePlan.getCurrentRoom().getCharacters().contains(orc), "Orc should be removed from the room after dying.");
    }

    @Test
    void testUnknownCommand() {
        String response = commandHandler.handleCommand("fly");
        assertEquals("Unknown command.", response, "Unknown command should return correct error message");
    }
}

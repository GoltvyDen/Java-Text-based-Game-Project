package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterTest {
    private Character character;

    @BeforeEach
    void setUp() {
        character = new Character("Warrior", "Brave warrior of the realm");
        character.setHealth(100);
    }

    @Test
    void testGetName() {
        assertEquals("Warrior", character.getName(), "Character name should be Warrior");
    }

    @Test
    void testGetDescription() {
        assertEquals("Brave warrior of the realm", character.getDescription(), "Character description should match");
    }

    @Test
    void testInitialHealth() {
        assertEquals(100, character.getHealth(), "Character should start with 100 health");
    }

    @Test
    void testTakeDamage() {
        character.takeDamage(20);
        assertEquals(80, character.getHealth(), "Character should have 80 health after taking 20 damage");
    }

    @Test
    void testSetHealth() {
        character.setHealth(50);
        assertEquals(50, character.getHealth(), "Character health should be set to 50");
    }
}

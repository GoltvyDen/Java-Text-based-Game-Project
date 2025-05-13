package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GamePlan {

    private Room currentRoom;
    private Map<String, Room> rooms;
    private Backpack backpack;
    private boolean gameWon;
    private boolean gameLoosed;
    private Random random;
    private boolean isOrcDead;

    public GamePlan() {
        rooms = new HashMap<>();
        backpack = new Backpack(2);
        random = new Random();
        createRooms();
        gameWon = false;
        gameLoosed = false;
        isOrcDead = false;
    }

    private void createRooms() {
        Room entrance = new Room("Entrance", "You are at the entrance of the fortress. The witchers told you that a totem lies here, one that reveals the future.");
        Room hall = new Room("Hall", "The main hall of the fortress. Uther is waiting for you here.");
        Room corridor = new Room("Сorridor", "A long room. A fierce orc warrior walking here.");
        Room altar = new Room("Altar", "Altar for worship");
        Room portalRoom = new Room("Portal", "Magical portal room, though it's unclear how it works.");

        Item mask = new Item("Troll mask", "Troll witch doctor mask.", true);
        Item memorial = new Item("Memorial", "Fallen Warriors Memorial", false);
        Item totem = new Item("Totem", "The war totem.", true);
        Item book = new Item("Book", "Some adventure book.", true);

        corridor.addItem(mask);
        hall.addItem(memorial);
        altar.addItem(totem);

        Character uther = new Character("Uther", "Arthas, you must find totem, it's somewhere near");
        Character orc = new Character("Orc", "A fierce orc warrior.");
        hall.addCharacter(uther);
        corridor.addCharacter(orc);

        entrance.setExit("hall", hall);
        hall.setExit("entrance", entrance);
        hall.setExit("corridor", corridor);
        hall.setExit("altar", altar);
        hall.setExit("portal", portalRoom);
        corridor.setExit("hall", hall);
        altar.setExit("hall", hall);
        portalRoom.setExit("hall", hall);

        rooms.put("Entrance", entrance);
        rooms.put("Hall", hall);
        rooms.put("Corridor", corridor);
        rooms.put("Altar", altar);
        rooms.put("Portal", portalRoom);

        currentRoom = entrance;
        backpack.addItem(book);
    }

    public boolean isOrcDead() {
        return isOrcDead;
    }

    public void setOrcDead(boolean isOrcDead) {
        this.isOrcDead = isOrcDead;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameLoosed() {
        return gameLoosed;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public void setGameLoosed(boolean gameLoosed) {
        this.gameLoosed = gameLoosed;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public Item getItem(String itemName) {
        for(Item item : currentRoom.getItems()){
            if(item.getName().equals(itemName)){
                return item;
            }
        }
        for(Item item : backpack.getItems()){
            if(item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }

    public Room getRandomRoom() {
        int randomIndex = random.nextInt(rooms.size());
        return (Room) rooms.values().toArray()[randomIndex];
    }
}

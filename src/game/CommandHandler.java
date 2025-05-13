package game;

public class CommandHandler {
    private GamePlan gamePlan;

    public CommandHandler(GamePlan gamePlan) {
        this.gamePlan = gamePlan;
    }
    public String handleCommand(String command, String... parameters) {
        String currentRoomName = gamePlan.getCurrentRoom().getName();
        if (currentRoomName.equals("Сorridor")) {
            if (!gamePlan.isOrcDead() && !(command.equals("go") || (command.equals("attack") && parameters.length > 0 && parameters[0].equals("Orc")))) {
                gamePlan.setGameLoosed(true);
                return "The orc sees your hesitation and strikes! You have been defeated.";
            }
        }
        switch (command) {
            case "go":
                return handleGo(parameters);
            case "take":
                return handleTake(parameters);
            case "use":
                return handleUse(parameters);
            case "talk":
                return handleTalk(parameters);
            case "end":
                return "End of game.";
            case "inventory":
                return handleInventory();
            case "drop":
                return handleDrop(parameters);
            case "examine":
                return handleExamine(parameters);
            case "attack":
                return handleAttack(parameters);
            default:
                return "Unknown command.";
        }
    }
    private String handleGo(String... parameters) {
        if (parameters.length == 0) {
            return "Go where?";
        }
        String direction = parameters[0];
        Room nextRoom = gamePlan.getCurrentRoom().getExits().get(direction);
        if (nextRoom == null) {
            return "You can't go that way.";
        }
        gamePlan.setCurrentRoom(nextRoom);
        if (nextRoom.getName().equals("Portal")) {
            Room randomRoom = gamePlan.getRandomRoom();
            gamePlan.setCurrentRoom(randomRoom);
            return "You entered the portal and teleported to " + randomRoom.getName() + ".\n" + randomRoom.getDescription();
        }
        StringBuilder description = new StringBuilder(nextRoom.getDescription());
        if (!nextRoom.getCharacters().isEmpty()) {
            description.append("\nCharacters here: ");
            for (Character character : nextRoom.getCharacters()) {
                description.append(character.getName()).append(", ");
            }
            description.delete(description.length() - 2, description.length());
        }
        if (!nextRoom.getItems().isEmpty()) {
            description.append("\nItems here: ");
            for (Item item : nextRoom.getItems()) {
                description.append(item.getName()).append(", ");
            }
            description.delete(description.length() - 2, description.length());
        }
        return description.toString();
    }
    private String handleTake(String... parameters) {
        if (parameters.length == 0) {
            return "Take what?";
        }
        StringBuilder itemNameBuilder = new StringBuilder();
        for (String parameter : parameters) {
            itemNameBuilder.append(parameter).append(" ");
        }
        String itemName = itemNameBuilder.toString().trim();
        Item item = gamePlan.getItem(itemName);
        if (item == null) {
            return "There is no such item here.";
        }
        if (!item.isPortable()) {
            return "You can't take that item.";
        }
        if (gamePlan.getBackpack().getItems().size() < gamePlan.getBackpack().getCapacity()) {
            gamePlan.getBackpack().addItem(item);
            gamePlan.getCurrentRoom().getItems().remove(item);
            return "You took " + itemName + ".";
        } else {
            return "Your backpack is full.";
        }
    }
    private String handleUse(String... parameters) {
        if (parameters.length == 0) {
            return "Use what?";
        }
        String itemName = parameters[0];
        for (Item item : gamePlan.getBackpack().getItems()) {
            if (itemName.equals("Totem")){
                gamePlan.getBackpack().removeItem(item);
                gamePlan.setGameWon(true);
                return "You used the " + itemName + " and saw a vision of the fall of Lorderon!";
            } else {
                return "You cannot use " + itemName + ".";
            }
        }
        return "You don't have that item.";
    }
    private String handleTalk(String... parameters) {
        if (parameters.length == 0) {
            return "Talk to whom?";
        }
        String characterName = parameters[0];
        for (Character character : gamePlan.getCurrentRoom().getCharacters()) {
            if (character.getName().equals(characterName)) {
                return "You talked to " + characterName + ".\n" + character.getDescription();
            }
        }
        return "There is no such character here.";
    }
    private String handleInventory() {
        StringBuilder inventory = new StringBuilder("Inventory:\n");
        for (Item item : gamePlan.getBackpack().getItems()) {
            inventory.append("- ").append(item.getName()).append("\n");
        }
        return inventory.toString();
    }
    private String handleDrop(String... parameters) {
        if (parameters.length == 0) {
            return "Drop what?";
        }
        StringBuilder itemNameBuilder = new StringBuilder();
        for (String parameter : parameters) {
            itemNameBuilder.append(parameter).append(" ");
        }
        String itemName = itemNameBuilder.toString().trim();
        Item item = null;
        for (Item backpackItem : gamePlan.getBackpack().getItems()) {
            if (backpackItem.getName().equals(itemName)) {
                item = backpackItem;
                break;
            }
        }
        if (item == null) {
            return "You don't have that item.";
        }
        if (gamePlan.getBackpack().removeItem(item)) {
            gamePlan.getCurrentRoom().addItem(item);
            return "You dropped " + itemName + ".";
        } else {
            return "You don't have that item.";
        }
    }
    private String handleExamine(String... parameters) {
        if (parameters.length == 0) {
            return "Examine what?";

        }
        StringBuilder itemNameBuilder = new StringBuilder();
        for (String parameter : parameters) {
            itemNameBuilder.append(parameter).append(" ");
        }
        String itemName = itemNameBuilder.toString().trim();
        for (Item item : gamePlan.getCurrentRoom().getItems()) {
            if (item.getName().equals(itemName)) {
                return item.getDescription();
            }
        }
        for (Item item : gamePlan.getBackpack().getItems()) {
            if (item.getName().equals(itemName)) {
                return item.getDescription();
            }
        }
        return "There is no such item here or in your inventory.";
    }
    private String handleAttack(String... parameters) {
        if (parameters.length == 0) {
            return "Attack whom?";
        }
        String characterName = parameters[0];
        for (Character character : gamePlan.getCurrentRoom().getCharacters()) {
            if (character.getName().equals(characterName)) {
                character.takeDamage(100);
                if (character.getHealth() <= 0) {
                    gamePlan.getCurrentRoom().removeCharacter(character);
                    if (characterName.equals("Orc")) {
                        gamePlan.setOrcDead(true);
                    }
                    return "You killed " + characterName + "!";
                }
                return "You attacked " + characterName + ". " + character.getName() + "'s health is now " + character.getHealth() + ".";
            }
        }
        return "There is no such character here.";
    }
}

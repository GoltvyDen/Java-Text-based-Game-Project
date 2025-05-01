package ui;

import game.Game;
import game.GamePlan;
import game.CommandHandler;

import java.util.Scanner;

public class TextInterface {

    private Game game;
    private GamePlan gamePlan;
    private CommandHandler commandHandler;
    private Scanner scanner;

    public TextInterface(GamePlan gamePlan, Game game) {
        this.game = game;
        this.gamePlan = gamePlan;
        commandHandler = new CommandHandler(gamePlan);
        scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("Welcome to Warcraft Adventure!");
        System.out.println(gamePlan.getCurrentRoom().getDescription());

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0];
            String[] parameters = new String[parts.length - 1];
            System.arraycopy(parts, 1, parameters, 0, parameters.length);

            String result = commandHandler.handleCommand(command, parameters);
            System.out.println(result);

            if (gamePlan.isGameWon()){
                System.out.println("You won the game!");
                break;
            }

            if (gamePlan.isGameLoosed()){
                System.out.println("Game Over");
                break;
            }


            if (command.equals("end")) {
                break;
            }
        }
    }
}
package game;

import ui.TextInterface;

public class Game {

    private GamePlan gamePlan;
    private TextInterface ui;

    public Game() {
        gamePlan = new GamePlan();
        ui = new TextInterface(gamePlan, this);
    }

    public void play() {
        ui.play();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
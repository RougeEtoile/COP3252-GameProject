package Poker;

public class Context
{
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private Game game = new Game();

    public Game currentGame() {
        return game;
    }
    public Game resetGame() { game = new Game(); return game;}
}

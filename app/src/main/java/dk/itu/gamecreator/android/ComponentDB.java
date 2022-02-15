package dk.itu.gamecreator.android;

import java.util.List;

public final class ComponentDB {

    /*
    * Placeholder for a Database until we create a real one - this is just a
    * Singleton class that can hold data that persists across the different
    * fragments and activities. The private constructor makes sure that it is
    * only created once.
    *
    * */

    static ComponentDB instance = null;
    Game currentGame;
    int id;

    private ComponentDB() {
        currentGame = new Game();
        id = 0;
    }

    public static ComponentDB getInstance() {
        if (instance == null) {
            instance = new ComponentDB();
        }
        return instance;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void addGameComponent(GameComponent gc) {
        currentGame.addComponent(gc);
    }

    public void addSolution(SolutionComponent sc) {
        currentGame.addSolution(sc);
    }

    public int getNextId() {
        id = id + 1;
        return id;
    }

}

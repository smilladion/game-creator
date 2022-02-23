package dk.itu.gamecreator.android;

import java.util.ArrayList;
import java.util.List;

public final class ComponentDB {

    /*
    * Placeholder for a Database until we create a real one - this is just a
    * Singleton class that can hold data that persists across the different
    * fragments and activities. The private constructor makes sure that it is
    * only created once.
    *
    * */

    private static ComponentDB instance = null;
    private Game currentGame = null;
    private List<Game> allGames;
    private int id;

    private ComponentDB() {
        allGames = new ArrayList<>();
        id = 0;
    }

    public static ComponentDB getInstance() {
        if (instance == null) {
            instance = new ComponentDB();
        }
        return instance;
    }

    public void newGame() {
        currentGame = new Game();
    }

    public void saveGame() {
        allGames.add(currentGame);
        currentGame = null;
    }

    public List<Game> getAllGames() {
        return allGames;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public int getNextId() {
        id = id + 1;
        return id;
    }

}

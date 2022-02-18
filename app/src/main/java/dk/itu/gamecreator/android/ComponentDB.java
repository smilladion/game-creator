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

    static ComponentDB instance = null;
    Game currentGame;
    List<Game> allGames;
    int id;

    private ComponentDB() {
        currentGame = new Game();
        allGames = new ArrayList<>();
        id = 0;
    }

    public static ComponentDB getInstance() {
        if (instance == null) {
            instance = new ComponentDB();
        }
        return instance;
    }

    public void addGame(Game game) {
        allGames.add(game);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public int getNextId() {
        id = id + 1;
        return id;
    }

}

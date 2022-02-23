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
    private Game chosenGame = null;
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

    public void setCurrentGame(Game game) {
        currentGame = game;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setChosenGame(Game game) {
        this.chosenGame = game;
    }

    public Game getChosenGame() {
        return chosenGame;
    }

    public int getNextComponentId() {
        id = id + 1;
        return id;
    }

}

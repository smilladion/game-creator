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
    private int cID;
    private int gID;

    private ComponentDB() {
        allGames = new ArrayList<>();
        cID = 0;
        gID = 0;
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
        if (currentGame.getgID() != 0) {
            currentGame = null;
        } else {
            currentGame.setID(getNextgID());
            allGames.add(currentGame);
            currentGame = null;
        }
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

    private int getNextgID() {
        gID = gID + 1;
        return gID;
    }

    public int getNextComponentId() {
        cID = cID + 1;
        return cID;
    }

}

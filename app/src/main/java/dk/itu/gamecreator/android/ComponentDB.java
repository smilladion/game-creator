package dk.itu.gamecreator.android;

import java.util.ArrayList;
import java.util.List;

import dk.itu.gamecreator.android.Components.ComponentI;

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
    private int cID;
    private int gID;

    private ComponentI component;

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

    private int getNextgID() {
        gID = gID + 1;
        return gID;
    }

    public int getNextComponentId() {
        cID = cID + 1;
        return cID;
    }

    public ComponentI getComponent() {
        return component;
    }

    public void setComponent(ComponentI component) {
        this.component = component;
    }

}

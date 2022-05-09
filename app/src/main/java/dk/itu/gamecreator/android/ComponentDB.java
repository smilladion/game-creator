package dk.itu.gamecreator.android;

import java.util.ArrayList;
import java.util.List;

import dk.itu.gamecreator.android.Components.Component;

public final class ComponentDB {

    /*
    * Placeholder for a Database until we create a real one - this is just a
    * Singleton class that can hold data that persists across the different
    * fragments and activities. The private constructor makes sure that it is
    * only created once.
    *
    * */

    private static ComponentDB instance = null;

    private List<Game> allGames;
    private int cID;
    private int gID;
    private int sID;

    private ComponentDB() {
        allGames = new ArrayList<>();
        cID = 0;
        gID = 0;
        sID = 0;
    }

    public static ComponentDB getInstance() {
        if (instance == null) {
            instance = new ComponentDB();
        }
        return instance;
    }

    public List<Game> getAllGames() {
        return allGames;
    }

    public int getNextgID() {
        gID = gID + 1;
        return gID;
    }

    public int getNextComponentId() {
        cID = cID + 1;
        return cID;
    }

    public int getNextStageID() {
        sID = sID + 1;
        return sID;
    }
}

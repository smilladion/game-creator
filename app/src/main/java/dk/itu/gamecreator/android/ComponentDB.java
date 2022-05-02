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
    private Game currentGame = null;
    private Stage currentStage = null;
    private List<Game> allGames;
    private int cID;
    private int gID;
    private int sID;

    private Component component;

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

    public void newGame() {
        currentGame = new Game();
    }

    public void saveGame() {
        //If this was a game being edited, and already has an id.
        if (currentGame.getgID() != 0) {
            currentGame = null;
        } else {
            currentGame.setID(getNextgID());
            allGames.add(currentGame);
            currentGame = null;
        }
    }

    public void setNextStages() {
        /** Setting all stages to have the following stage as nextStage.
         * This is already done on creation, (and thus used for preview!)
         * But is broken if user deletes a stage.
         * */
        for (int i = 0; i < currentGame.getStages().size(); i++) {
            if (i == (currentGame.getStages().size()-1)) {
                currentGame.getStages().get(i).setNextStage(null);
            } else {
                currentGame.getStages().get(i).setNextStage(
                        currentGame.getStages().get(i + 1)
                );
            }
        }
    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    public Stage getCurrentStage() {
        return currentStage;
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

    public int getNextStageID() {
        sID = sID + 1;
        return sID;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}

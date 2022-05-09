package dk.itu.gamecreator.android;

import dk.itu.gamecreator.android.Components.Component;

public class ViewModel {

    private static ViewModel instance = null;

    private Game currentGame = null;
    private Stage currentStage = null;
    private ComponentDB cDB;
    private Component component;

    private boolean isInMyGames;

    private ViewModel() {
        cDB = ComponentDB.getInstance();
    }

    public static ViewModel getInstance() {
        if (instance == null) {
            instance = new ViewModel();
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
            currentGame.setID(cDB.getNextgID());
            cDB.getAllGames().add(currentGame);
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

    public void setCurrentGame(Game game) {
        currentGame = game;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public boolean isInMyGames() {
        return isInMyGames;
    }

    public void setInMyGames(boolean inMyGames) {
        isInMyGames = inMyGames;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

}

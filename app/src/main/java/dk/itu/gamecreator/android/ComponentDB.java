package dk.itu.gamecreator.android;

import java.util.List;

public final class ComponentDB {

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

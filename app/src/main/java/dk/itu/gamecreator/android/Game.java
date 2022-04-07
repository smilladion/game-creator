package dk.itu.gamecreator.android;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import dk.itu.gamecreator.android.Components.Component;

public class Game {
    private final ArrayList<Stage> stages;
    private int gID;
    private String name;

    public Game() {
        stages = new ArrayList<>();
    }

    public ArrayList<Stage> getStages() { return stages; }

    public void addStage(Stage stage) { stages.add(stage); }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int id) {
        gID = id;
    }

    public int getgID() {
        return gID;
    }

    public String getName() {
        return name;
    }
}

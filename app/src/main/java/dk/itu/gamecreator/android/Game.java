package dk.itu.gamecreator.android;

import android.location.Location;
import java.util.ArrayList;

public class Game {
    private final ArrayList<Stage> stages;
    private int gID;
    private String name;
    private Location location;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

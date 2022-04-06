package dk.itu.gamecreator.android;

import java.util.ArrayList;
import dk.itu.gamecreator.android.Components.Component;

public class Game {
    private final ArrayList<Component> components;
    private final ArrayList<Stage> stages;
    private int gID;
    private String name;

    public Game() {
        components = new ArrayList<>();
        stages = new ArrayList<>();
    }

    public ArrayList<Stage> getStages() { return stages; }

    public void addStage(Stage stage) { stages.add(stage); }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

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

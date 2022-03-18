package dk.itu.gamecreator.android;

import java.util.ArrayList;
import dk.itu.gamecreator.android.Components.ComponentI;

public class Game {
    private final ArrayList<ComponentI> components;
    private int gID;
    private String name;

    public Game() {
        components = new ArrayList<>();
    }

    public ArrayList<ComponentI> getComponents() {
        return components;
    }

    public void addComponent(ComponentI component) {
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

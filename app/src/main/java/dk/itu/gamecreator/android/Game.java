package dk.itu.gamecreator.android;

import java.util.ArrayList;

import dk.itu.gamecreator.android.Components.Component;

public class Game {
    private final ArrayList<Component> components;
    private static int id;
    private String name;

    public Game() {
        components = new ArrayList<>();
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

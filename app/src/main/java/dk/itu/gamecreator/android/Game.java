package dk.itu.gamecreator.android;

import java.util.ArrayList;

import dk.itu.gamecreator.android.Components.Component;

public class Game {
    private final ArrayList<Component> components;

    public Game() {
        components = new ArrayList<>();
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        components.add(component);
    }
}

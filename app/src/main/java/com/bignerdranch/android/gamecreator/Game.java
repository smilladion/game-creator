package com.bignerdranch.android.gamecreator;

import java.util.ArrayList;

public class Game {
    private ArrayList<GameComponent> components;
    private SolutionComponent solution;

    public Game() {
        components = new ArrayList<>();
    }

    public ArrayList<GameComponent> getComponents() {
        return components;
    }

    public SolutionComponent getSolution() {
        return solution;
    }

    public void addComponent(GameComponent component) {
        components.add(component);
    }

    public void setSolution(SolutionComponent solution) {
        this.solution = solution;
    }
}

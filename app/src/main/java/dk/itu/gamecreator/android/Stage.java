package dk.itu.gamecreator.android;

import java.util.ArrayList;
import java.util.List;

import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.SolutionComponent;

public class Stage {

    private Stage nextStage;
    private List<Component> gameComponents;
    private SolutionComponent solutionComponent;
    private int id;

    public Stage() {
        gameComponents = new ArrayList<>();
    }

    public Stage getNextStage() {
        return nextStage;
    }

    public void setNextStage(Stage nextStage) {
        this.nextStage = nextStage;
    }

    public List<Component> getGameComponents() {
        return gameComponents;
    }

    public void addGameComponent(Component component) {
        gameComponents.add(component);
    }

    public SolutionComponent getSolutionComponent() {
        return solutionComponent;
    }

    public void setSolutionComponent(SolutionComponent solutionComponent) {
        this.solutionComponent = solutionComponent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return "Stage " + id;
    }
}

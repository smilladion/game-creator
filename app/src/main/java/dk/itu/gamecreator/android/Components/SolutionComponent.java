package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.Stage;

public abstract class SolutionComponent implements Component {

    private final int id;
    private boolean isSolved = false;
    private SolvedListener solvedListener;

    public SolutionComponent(int id) {
        this.id = id;
    }

    public abstract View getDisplayView(Context context);

    public abstract View getCreateView(Context context);

    public abstract boolean saveComponent(Context context);

    public abstract String getName();

    @Override
    public boolean isSolutionComponent() {
        return true;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
        if (solved) {
            solvedListener.onChange();
        }
    }

    public void addSolvedListener(SolvedListener solvedListener) {
        this.solvedListener = solvedListener;
    }

    public interface SolvedListener {
        void onChange();
    }
}

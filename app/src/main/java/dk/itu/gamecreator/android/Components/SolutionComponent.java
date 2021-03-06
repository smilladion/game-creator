package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

public abstract class SolutionComponent implements Component {

    private final int id;
    private boolean isSolved = false;
    private SolvedListener solvedListener;

    /**
     * Define all the different views your getCreateView will need, ie:
     * EditText editText;
     * */

    public SolutionComponent(int id) {
        this.id = id;
    }

    /**
     * Return a view of how the component will look like when in a game.
     * Start by creating an outer layout (LinearLayout for example), and
     * add the different child views to it.
     * Set any click listeners or other functionality here.
     * */
    public abstract View getDisplayView(Context context);

    /**
     * Return a view for how this Component gets created. You can either
     * make the XML programatically in the function, or create an XML-file
     * in the res/layout.
     * Retrieve the view like this:
     * View view = LayoutInflater.from(context).inflate(R.layout.[fragment_create_your_component], null, false);
     * And use it to get your different views, ie:
     * editText = view.findViewById(R.id.edit_text);
     * */
    public abstract View getCreateView(Context context);

    /**
     * Check that all required input is there. If not, return false.
     * Get input, and save it in local fields, so it is ready for use in getDisplayView.
     * Return true.
     * */
    public abstract boolean saveComponent(Context context);

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

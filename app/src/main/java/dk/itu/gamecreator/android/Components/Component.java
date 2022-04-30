package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

public interface Component {

    /**
     * Return a view of how the component will look like when in a game.
     * Start by creating an outer layout (LinearLayout for example), and
     * add the different child views to it.
     * Set any click listeners or other functionality here.
     * */
    View getDisplayView(Context context);

    /**
     * Return a view for how this Component gets created. You can either
     * make the XML programatically in the function, or create an XML-file
     * in the res/layout.
     * Retrieve the view like this:
     * View view = LayoutInflater.from(context).inflate(R.layout.[fragment_create_your_component], null, false);
     * And use it to get your different views, ie:
     * editText = view.findViewById(R.id.edit_text);
     * */
    View getCreateView(Context context);

    /**
     * Check that all required input is there. If not, return false.
     *
     * Get input, and save it in local fields, so it is ready for use in getDisplayView.
     * These can NOT be saved as Views, as creation of these needs a Context.
     * Save the info in Strings/arrays/ints, and create the actual views in the getDisplayView method.
     * Return true.
     * */
    boolean saveComponent(Context context);

}

package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

public interface Component {

    /** A view can not be created before we have the context within which it is
    * going to be displayed. All information must be stored in fields of ints, String,
    * arrays etc, and only in the getView method can the Buttons, TextFields, EditFields
    * etc be created.
    * */
    View getDisplayView(Context context);


    /**
     * Instead of inflating an XML file, reference the ID of the outermost LinearLayout in
     * the XML file you want to use for creating this component. Ie - if the ID is "text_layout"
     * get the view like this:
     * View view = (LinearLayout) ((Activity) context).findViewById(R.id.text_layout);
     * Then you can save the different views as fields in the class. Ie:
     * editText = view.findViewById(R.id.input_text_1);
     * When you have accessed all the views you need to create the component later on return the view.
     */
    View getCreateView(Context context);

    /* Might be taken out. */
    String getGravity();

    /**
     * Save the different input fields / check boxes or whatever else fields that
     * the component constructor has. These can NOT be saved as Views (Like buttons)
     * as creation of these needs a Context. Save the info in Strings/arrays/ints
     * and create the actual views in the getDisplayView method.
     */
    void saveComponent(Context context);
}

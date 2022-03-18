package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

public interface ComponentI {

    /* A view can not be created before we have the context within which it is
    * going to be displayed. All information must be stored in fields of ints, String,
    * arrays etc, and only in the getView method can the Buttons, TextFields, EditFields
    * etc be created.
    * */
    View getDisplayView(Context context);


    /* Define the XML programatically. Start with creating a LinearLayout,
     * and add children to it.
     */
    View getCreateView(Context context);

    /* Might be taken out. */
    String getGravity();
}

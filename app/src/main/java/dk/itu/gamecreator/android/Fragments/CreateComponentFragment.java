package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.R;

public class CreateComponentFragment extends Fragment {

    ComponentDB cDB;
    Component component;
    Button doneButton;
    Button discardButton;
    Button backButton;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();
        bundle = getArguments();

        // Checks if the fragment was opened through an edit button and fetches component data
        if (bundle != null) {
            int index = bundle.getInt("componentIndex");
            component = cDB.getCurrentGame().getComponents().get(index);
        } else {
            component = cDB.getComponent(); // Create new component
        }

        return inflater.inflate(R.layout.fragment_create_component, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        LinearLayout componentLayout = view.findViewById(R.id.component_layout);
        View componentView = component.getCreateView(this.getContext());
        componentLayout.addView(componentView);
        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);
        backButton = view.findViewById(R.id.fragment_back_button);
        doneButton.setOnClickListener(v -> onDone(v));
        discardButton.setOnClickListener(v -> onDiscard(v));
        backButton.setOnClickListener(v -> onDiscard(v));
    }

    public void onDone(View view) {
        if (component.saveComponent(this.getContext())) {
            if (bundle == null) {
                cDB.getCurrentGame().addComponent(component);
            }

            getParentFragmentManager().popBackStack();
        }
    }

    public void onDiscard(View view) {
        getParentFragmentManager().popBackStack();
    }
}

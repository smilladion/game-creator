package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.itu.gamecreator.android.ClassFinder;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Fragments.CreateComponentFragment;
import dk.itu.gamecreator.android.Stage;
import dk.itu.gamecreator.android.R;

public class StageRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Stage> stages;
    private final LayoutInflater mInflater;
    private final Context context;
    private ComponentDB cDB;
    private FragmentManager fragmentManager;

    public StageRecycler(Context context, List<Stage> stages, FragmentManager fragmentManager) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.stages = stages;
        this.fragmentManager = fragmentManager;
        cDB = ComponentDB.getInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_stage, parent, false);
        return new StageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Stage stage = stages.get(position);
        StageViewHolder stageViewHolder = (StageViewHolder) holder;

        RecyclerViewAdapter componentAdapter = new RecyclerViewAdapter(context, stage.getGameComponents());
        stageViewHolder.recyclerView.setAdapter(componentAdapter);

        ListPopupWindow listPopupWindow = new ListPopupWindow(context, null,
                com.google.android.material.R.attr.listPopupWindowStyle);

        // Set button as the list popup's anchor
        listPopupWindow.setAnchorView(stageViewHolder.addComponent);

        // Get component class names using the ClassFinder
        ArrayList<Class<?>> classes = new ArrayList<>(ClassFinder.load());
        ArrayList<String> componentTypes = new ArrayList<>();

        for (Class<?> clazz : classes) {
            String name = clazz.getSimpleName();
            String nameFormatted = name.replace("Component", "")
                    .replaceAll("(?=[A-Z])", " ").trim();
            componentTypes.add(nameFormatted);
        }

        // Set list popup's content
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_popup_window_item, componentTypes);
        listPopupWindow.setAdapter(adapter);

        // Respond to list popup window item click
        listPopupWindow.setOnItemClickListener((parent, v, pos, id) -> {

            Class<?> clazz = classes.get(pos);

            try {
                Component component = (Component) clazz.getConstructor(int.class).newInstance(cDB.getNextComponentId());
                cDB.setComponent(component);

                FragmentManager fm = fragmentManager;
                fm.beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.create_fragment, new CreateComponentFragment(), null)
                        .addToBackStack(null)
                        .commit();
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }

            // Dismiss popup
            listPopupWindow.dismiss();
        });

        // Show list popup window on button click.
        stageViewHolder.addComponent.setOnClickListener(view1 -> listPopupWindow.show());
    }

    @Override
    public int getItemCount() {
        return stages.size();
    }

    public static class StageViewHolder extends RecyclerView.ViewHolder {

        TextView stageName;
        Button addComponent;
        RecyclerView recyclerView;

        public StageViewHolder(@NonNull View itemView) {
            super(itemView);
            stageName = itemView.findViewById(R.id.stage_number);
            addComponent = itemView.findViewById(R.id.add_component_button);
            recyclerView = itemView.findViewById(R.id.current_components);
        }
    }
}

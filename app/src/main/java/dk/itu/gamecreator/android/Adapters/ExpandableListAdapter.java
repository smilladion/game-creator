package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.ClassFinder;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.SolutionComponent;
import dk.itu.gamecreator.android.Fragments.CreateComponentFragment;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Stage;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Stage> stages;
    private HashMap<String, List<Component>> map;
    private ComponentDB cDB;
    private FragmentManager fragmentManager;

    public ExpandableListAdapter(Context context, List<Stage> stages, HashMap<String, List<Component>> map, FragmentManager fragmentManager) {
        this.context = context;
        this.stages = stages;
        this.map = map;
        this.fragmentManager = fragmentManager;

        cDB = ComponentDB.getInstance();
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        String stageName = stages.get(listPosition).getName();
        return this.map.get(stageName).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Component component = (Component) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.component_group, null);
        }

        TextView componentName = convertView.findViewById(R.id.component_name);
        componentName.setText(ClassFinder.nameForClass(component.getClass()));

        Button editButton = convertView.findViewById(R.id.edit_button);
        Button deleteButton = convertView.findViewById(R.id.delete_button);
        editButton.setOnClickListener(v -> onEdit(listPosition, expandedListPosition));
        deleteButton.setOnClickListener(v -> onDelete(listPosition, expandedListPosition));

        LinearLayout componentLayout = convertView.findViewById(R.id.component_layout);
        componentLayout.removeAllViews();
        View componentView = component.getDisplayView(context);
        setViewAndChildrenEnabled(componentView, false);
        componentLayout.addView(componentView);

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        String stageName = stages.get(listPosition).getName();
        return this.map.get(stageName).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.stages.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.stages.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.stage_group, null);
        }

        Stage s = (Stage) getGroup(listPosition);
        ExpandableListView elv = (ExpandableListView) parent;

        if (s.isExpanded()) {
            elv.expandGroup(listPosition);
            notifyDataSetChanged();
        }

        TextView listTitleTextView = convertView.findViewById(R.id.stage_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText("#" + (listPosition + 1));

        Button addComponent = convertView.findViewById(R.id.add_component_button);
        Button addSolutionComponent = convertView.findViewById(R.id.add_solution_component_button);
        Button deleteStage = convertView.findViewById(R.id.delete_stage);

        deleteStage.setOnClickListener(v -> onDeleteStage(listPosition));

        // Get component class names using the ClassFinder
        ArrayList<ArrayList<Class<?>>> classes = new ArrayList<>(ClassFinder.load());
        ArrayList<String> gameComponents = new ArrayList<>();
        ArrayList<String> solutionComponents = new ArrayList<>();

        for (Class<?> clazz : classes.get(0)) {
            gameComponents.add(ClassFinder.nameForClass(clazz));
        }

        for (Class<?> clazz : classes.get(1)) {
            solutionComponents.add(ClassFinder.nameForClass(clazz));
        }

        if (s.getSolutionComponent() != null) {
            addSolutionComponent.setEnabled(false);
        } else {
            addSolutionComponent.setEnabled(true);
        }

        createPopUp(addComponent, gameComponents, classes.get(0), s);
        createPopUp(addSolutionComponent, solutionComponents, classes.get(1), s);

        return convertView;
    }

    // Source for this method: https://stackoverflow.com/a/28509431
    private static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }

    public void createPopUp(Button button, List<String> componentNames, ArrayList<Class<?>> classes, Stage s) {
        ListPopupWindow listPopupWindow = new ListPopupWindow(context, null,
                com.google.android.material.R.attr.listPopupWindowStyle);

        // Set button as the list popup's anchor
        listPopupWindow.setAnchorView(button);

        // Set list popup's content
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_popup_window_item, componentNames);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setWidth(400);

        // Respond to list popup window item click
        listPopupWindow.setOnItemClickListener((par, v, pos, id) -> {

            Class<?> clazz = classes.get(pos);
            cDB.setCurrentStage(s);
            s.setExpanded(true);

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

        // Has something to do with the way ExpandableList works. (As the whole field is
        // actually a button that expands / minimises the list.
        button.setFocusable(false);
        // Show list popup window on button click.
        button.setOnClickListener(view1 -> listPopupWindow.show());
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Stage s = (Stage) getGroup(groupPosition);
        s.setExpanded(false);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Stage s = (Stage) getGroup(groupPosition);
        s.setExpanded(true);
    }

    private void onDelete(int listPosition, int expandedListPosition) {
        Stage s = (Stage) getGroup(listPosition);
        Component c = s.getGameComponents().get(expandedListPosition);

        if (c instanceof SolutionComponent) {
            s.setSolutionComponent(null);
        }

        String stageName = stages.get(listPosition).getName();
        map.get(stageName).remove(expandedListPosition);
        notifyDataSetChanged();
    }

    private void onEdit(int listPosition, int expandedListPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt("componentIndex", expandedListPosition);
        cDB.setCurrentStage(cDB.getCurrentGame().getStages().get(listPosition));

        CreateActivity activity = (CreateActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.create_fragment, CreateComponentFragment.class, bundle)
                .addToBackStack(null)
                .commit();
    }

    private void onDeleteStage(int listPosition) {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setMessage("Delete stage?");
        builder.setCancelable(false);
        builder.setPositiveButton("Delete", (dialogInterface, i) -> deleteStage(listPosition));
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {}).show();
    }

    private void deleteStage(int listPosition) {
        String stageName = stages.get(listPosition).getName();
        map.remove(stageName);
        stages.remove(listPosition);
        cDB.getCurrentGame().getStages().remove(listPosition);
        notifyDataSetChanged();
    }
}

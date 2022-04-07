package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.itu.gamecreator.android.ClassFinder;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Fragments.CreateComponentFragment;
import dk.itu.gamecreator.android.Stage;
import dk.itu.gamecreator.android.R;


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
        cDB = ComponentDB.getInstance();
        this.fragmentManager = fragmentManager;
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
        componentName.setText(component.getName());
        Button editButton = convertView.findViewById(R.id.edit_button);
        Button deleteButton = convertView.findViewById(R.id.delete_button);
        LinearLayout componentLayout = convertView.findViewById(R.id.component_layout);
        componentLayout.removeAllViews();
        View componentView = component.getDisplayView(context);
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

        TextView listTitleTextView = convertView.findViewById(R.id.stage_name);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(s.getName());
        Button addComponent = convertView.findViewById(R.id.add_component_button);

        ListPopupWindow listPopupWindow = new ListPopupWindow(context, null,
                com.google.android.material.R.attr.listPopupWindowStyle);

        // Set button as the list popup's anchor
        listPopupWindow.setAnchorView(addComponent);

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
        listPopupWindow.setOnItemClickListener((par, v, pos, id) -> {

            Class<?> clazz = classes.get(pos);
            cDB.setCurrentStage(s);

            try {
                Component component = (Component) clazz.getConstructor(int.class).newInstance(cDB.getNextComponentId());
                cDB.setComponent(component);
                System.out.println("COMPONENT NAME! = " + component.getName());

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
        addComponent.setFocusable(false);
        // Show list popup window on button click.
        addComponent.setOnClickListener(view1 -> listPopupWindow.show());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}

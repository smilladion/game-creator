package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Stage;
import dk.itu.gamecreator.android.R;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> stageNames;
    private HashMap<Stage, List<Component>> map;

    public ExpandableListAdapter(Context context, List<String> stageNames, HashMap<Stage, List<Component>> map) {
        this.context = context;
        this.stageNames = stageNames;
        this.map = map;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.map.get(this.stageNames.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.component_group, null);
        }
        /*
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        */

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.map.get(this.stageNames.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.stageNames.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.stageNames.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.stage_group, null);
        }
        /*
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);*/
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

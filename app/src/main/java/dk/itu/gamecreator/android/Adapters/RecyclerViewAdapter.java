package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

import dk.itu.gamecreator.android.ClassFinder2;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.R;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemMoveCallback.ItemTouchHelperContract {

    private final List<Component> components;
    private final LayoutInflater mInflater;
    private final Context context;

    public RecyclerViewAdapter(Context context, List<Component> components) {
        this.mInflater = LayoutInflater.from(context);
        this.components = components;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_text, parent, false);
        return new ComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Component component = components.get(position);
        ComponentViewHolder componentHolder = (ComponentViewHolder) holder;
        View componentView = component.getDisplayView(context);
        componentHolder.layout.addView(componentView);
        componentHolder.componentName.setText(ClassFinder2.nameForClass(component.getClass()));
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(components, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(components, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onRowSelected(RecyclerViewAdapter.ViewHolder viewHolder) {
        viewHolder.rowView.setBackgroundColor(Color.GRAY);
    }

    public void onRowClear(RecyclerViewAdapter.ViewHolder viewHolder) {
        viewHolder.rowView.setBackgroundColor(Color.WHITE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View rowView;

        ViewHolder(View itemView) {
            super(itemView);
            rowView = itemView;
        }
    }

    public static class ComponentViewHolder extends ViewHolder {
        LinearLayout layout;
        TextView componentName;

        ComponentViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.component_layout);
            componentName = itemView.findViewById(R.id.component_name);
        }
    }
}

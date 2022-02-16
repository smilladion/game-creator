package dk.itu.gamecreator.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.TextComponent;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<GameComponent> components;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, List<GameComponent> components) {
        this.mInflater = LayoutInflater.from(context);
        this.components = components;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GameComponent component = components.get(position);

        if (component instanceof TextComponent) {
            holder.myTextView.setText(((TextComponent) component).getText());
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return components.size();
    }

    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.component_text);
        }
    }
}

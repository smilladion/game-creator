package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.itu.gamecreator.android.Stage;
import dk.itu.gamecreator.android.R;

public class StageRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Stage> stages;
    private final LayoutInflater mInflater;
    private final Context context;

    public StageRecycler(Context context, List<Stage> stages) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.stages = stages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_stage, parent, false);
        return new StageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stages.size();
    }

    public static class StageViewHolder extends RecyclerView.ViewHolder {

        TextView stageName;
        Button addComponent;

        public StageViewHolder(@NonNull View itemView) {
            super(itemView);
            stageName = itemView.findViewById(R.id.stage_number);
            addComponent = itemView.findViewById(R.id.add_component_button);
        }
    }
}

package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.Activities.GameActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;

public class GameRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Game> games;
    private final LayoutInflater mInflater;
    private final Context context;
    private ComponentDB cDB;

    public GameRecyclerAdapter(Context context, List<Game> games) {
        this.mInflater = LayoutInflater.from(context);
        this.games = games;
        this.context = context;
        cDB = ComponentDB.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Game game = games.get(position);
        GameViewHolder gameViewHolder = (GameViewHolder) holder;
        String gameName = game.getName();
        gameViewHolder.gameName.setText(gameName);
        gameViewHolder.playButton.setOnClickListener(view -> onPlay(game));
        gameViewHolder.editButton.setOnClickListener(view -> onEdit(game));
        gameViewHolder.deleteButton.setOnClickListener(view -> onDelete(gameViewHolder));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void onPlay(Game game) {
        cDB.setCurrentGame(game);
        cDB.setInMyGames(true);
        Intent intent = new Intent(context, GameActivity.class);
        context.startActivity(intent);
    }

    public void onEdit(Game game) {
        cDB.setCurrentGame(game);
        Intent intent = new Intent(context, CreateActivity.class);
        context.startActivity(intent);
    }

    private void onDelete(RecyclerView.ViewHolder holder) {
        games.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
        notifyItemRangeChanged(holder.getAdapterPosition(), games.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View rowView;

        ViewHolder(View itemView) {
            super(itemView);
            rowView = itemView;
        }
    }

    public static class GameViewHolder extends ViewHolder {
        TextView gameName;
        Button playButton;
        Button editButton;
        Button deleteButton;

        GameViewHolder(View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.game_name);
            playButton = itemView.findViewById(R.id.play_button);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}

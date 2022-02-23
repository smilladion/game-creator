package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.SolutionComponent;
import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;

public class PlayActivity extends AppCompatActivity {

    LinearLayout ll;
    Button backButton;
    ComponentDB cDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ll = findViewById(R.id.game_layout);
        backButton = findViewById(R.id.back_button_play);
        cDB = ComponentDB.getInstance();

        backButton.setOnClickListener(this::goBack);
        int i = 1;
        for(Game game: cDB.getAllGames()) {
            //Inner layout for each game + its edit and delete button
            LinearLayout gameLayout = new LinearLayout(this);

            Button gameButton = new Button(this);
            gameButton.setText(game.getName());
            gameButton.setOnClickListener(view -> gameClick(view, game));

            Button editButton = new Button(this);
            editButton.setText("Edit");
            editButton.setOnClickListener(view -> editGame(game));

            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");

            gameLayout.addView(gameButton);
            gameLayout.addView(editButton);
            gameLayout.addView(deleteButton);

            ll.addView(gameLayout);
            i++;
        }
    }

    public void editGame(Game game) {
        cDB.setCurrentGame(game);
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gameClick(View view, Game game) {
        cDB.setChosenGame(game);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}

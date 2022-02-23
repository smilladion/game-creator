package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;

public class GameActivity extends AppCompatActivity {

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

        Game game = cDB.getChosenGame();
        for (Component c: game.getComponents()) {
            ll.addView(c.getView(this));
        }

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }


}

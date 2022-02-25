package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    ComponentDB cDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll = findViewById(R.id.game_layout);
        cDB = ComponentDB.getInstance();

        Game game = cDB.getChosenGame();
        for (Component c: game.getComponents()) {
            ll.addView(c.getView(this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

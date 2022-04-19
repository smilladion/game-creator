package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dk.itu.gamecreator.android.Adapters.GameRecyclerAdapter;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.R;

public class PlayActivity extends AppCompatActivity {

    private LinearLayout ll;
    private ComponentDB cDB;
    private Button mapButton;

    private GameRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cDB = ComponentDB.getInstance();

        // Action bar
        setTitle("My Games");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll = findViewById(R.id.game_layout);
        mapButton = findViewById(R.id.map_button);

        recyclerView = findViewById(R.id.current_games);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mapButton.setOnClickListener(this::onMapClicked);

        populateRecyclerView();
    }

    // Using this method makes sure that the game name updates when editing it
    @Override
    protected void onResume() {
        super.onResume();

        ll.removeAllViews();
        populateRecyclerView();
    }

    public void populateRecyclerView() {
        adapter = new GameRecyclerAdapter(this, cDB.getAllGames());
        recyclerView.setAdapter(adapter);
    }

    private void onMapClicked(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    // Used for the back button in the title bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            cDB.setCurrentGame(null);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

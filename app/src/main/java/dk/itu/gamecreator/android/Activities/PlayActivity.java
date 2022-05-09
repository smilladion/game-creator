package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dk.itu.gamecreator.android.Adapters.GameRecyclerAdapter;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.ViewModel;

public class PlayActivity extends AppCompatActivity {

    private LinearLayout ll;
    private ComponentDB cDB;
    private ViewModel VM;

    private GameRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private TextView noGameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cDB = ComponentDB.getInstance();
        VM = ViewModel.getInstance();

        // Action bar
        setTitle("My Games");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll = findViewById(R.id.game_layout);

        noGameText = findViewById(R.id.text_no_games);

        recyclerView = findViewById(R.id.current_games);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (cDB.getAllGames().isEmpty()) {
            noGameText.setText("You have not made any games yet.");
        } else {
            noGameText.setVisibility(View.GONE);
        }

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

    // Used for the back button in the title bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            VM.setCurrentGame(null);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

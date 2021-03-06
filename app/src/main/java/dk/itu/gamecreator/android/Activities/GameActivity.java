package dk.itu.gamecreator.android.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Fragments.GameFragment;
import dk.itu.gamecreator.android.R;

public class GameActivity extends AppCompatActivity {

    private ComponentDB cDB;
    private FragmentManager fm;
    private Fragment gameFragment = new GameFragment(R.id.game_fragment, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setTitle("Play Game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = getSupportFragmentManager();

        cDB = ComponentDB.getInstance();

        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.game_fragment, gameFragment)
                .commit();
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

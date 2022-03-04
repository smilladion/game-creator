package dk.itu.gamecreator.android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import dk.itu.gamecreator.android.ComponentDB;

import dk.itu.gamecreator.android.Dialogs.GameNameDialog;
import dk.itu.gamecreator.android.Fragments.ConfigFragment;
import dk.itu.gamecreator.android.Fragments.EditorFragment;
import dk.itu.gamecreator.android.Fragments.GameFragment;
import dk.itu.gamecreator.android.R;

public class CreateActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem editorTab;
    TabItem previewTab;
    TabItem configTab;

    Button saveGame;

    Fragment editorFragment = new EditorFragment();
    Fragment previewFragment = new GameFragment();
    Fragment configFragment = new ConfigFragment();

    ComponentDB cDB;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Action bar
        setTitle("Game Editor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = getSupportFragmentManager();

        cDB = ComponentDB.getInstance();

        saveGame = findViewById(R.id.save_game_button);
        saveGame.setOnClickListener(this::saveGame);

        tabLayout = findViewById(R.id.tab_layout);
        editorTab = findViewById(R.id.editor_tab);
        previewTab = findViewById(R.id.preview_tab);
        configTab = findViewById(R.id.config_tab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment frag = editorFragment;
                int pos = tab.getPosition();
                if (pos == 0) {
                    frag = editorFragment;
                } else if (pos == 1) {
                    frag = previewFragment;
                } else if (pos == 2) {
                    frag = configFragment;
                }
                fm.beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.create_fragment, frag, null)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fm.beginTransaction().setReorderingAllowed(true)
                .add(R.id.create_fragment, editorFragment, null)
                .commit();
    }

    public void saveGame(View view) {
        if (cDB.getCurrentGame().getComponents().isEmpty()) {
            Toast toast = Toast.makeText(this, "Add a game component to create a game!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (cDB.getCurrentGame().getName() == null || cDB.getCurrentGame().getName().trim().equals("")) {
            GameNameDialog.getDialog(this);
        } else {
            cDB.saveGame();
            cDB.newGame();

            finish();

            Toast toast = Toast.makeText(this, "Game saved!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    // Used for the back button in the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

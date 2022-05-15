package dk.itu.gamecreator.android.Activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import dk.itu.gamecreator.android.ComponentDB;

import dk.itu.gamecreator.android.Dialogs.GameNameDialog;

import dk.itu.gamecreator.android.Fragments.ConfigFragment;
import dk.itu.gamecreator.android.Fragments.EditorFragment;
import dk.itu.gamecreator.android.Fragments.GameFragment;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Stage;
import dk.itu.gamecreator.android.Util;

public class CreateActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem editorTab;
    private TabItem previewTab;
    private TabItem configTab;

    private Fragment editorFragment = new EditorFragment();
    private Fragment previewFragment = new GameFragment(R.id.create_fragment, true);
    private Fragment configFragment = new ConfigFragment();

    private ComponentDB cDB;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Action bar
        setTitle("Game Editor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fm = getSupportFragmentManager();

        cDB = ComponentDB.getInstance();

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
                    cDB.setNextStages();
                    frag = previewFragment;
                } else if (pos == 2) {
                    frag = configFragment;
                }
                fm.beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.create_fragment, frag)
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

    // Used for the buttons in the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.save_button) {
            saveGame();
        }

        return super.onOptionsItemSelected(item);
    }

    // Creates the save button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void saveGame() {
        ArrayList<String> stageNames = new ArrayList<>();

        for (Stage s : cDB.getCurrentGame().getStages()) {
            if (s.getSolutionComponent() == null) {
                stageNames.add(s.getName());
            }
        }

        /** Setting all stages to have the following stage as nextStage.
         * This is already done on creation, (and thus used for preview!)
         * But is broken if user deletes a stage.
         * */
        for (int i = 0; i < cDB.getCurrentGame().getStages().size(); i++) {
            if (i == (cDB.getCurrentGame().getStages().size()-1)) {
                cDB.getCurrentGame().getStages().get(i).setNextStage(null);
            } else {
                cDB.getCurrentGame().getStages().get(i).setNextStage(
                        cDB.getCurrentGame().getStages().get(i + 1)
                );
            }
        }

        if (!stageNames.isEmpty()) {
            String s = "";

            for (String str : stageNames) {
                s = s + str + ", ";
            }

            Toast toast = Toast.makeText(this,
                    "The following stages have no solution component: " + s, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (cDB.getCurrentGame().getStages().isEmpty()) {
            Toast toast = Toast.makeText(this,
                    "Nothing to save. Add a stage to start creating a game", Toast.LENGTH_SHORT);
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
}

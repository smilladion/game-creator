package dk.itu.gamecreator.android.Dialogs;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Util;
import dk.itu.gamecreator.android.ViewModel;

public class GameNameDialog {

    public static AlertDialog getDialog(Context context) {
        ViewModel VM = ViewModel.getInstance();

        AlertDialog dialog = new MaterialAlertDialogBuilder(context)
            .setTitle("Game name:")
            .setCancelable(false)
            .setView(R.layout.game_name_dialog)
            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
            .setPositiveButton("Save", (dialogInterface, i) -> {})
            .create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
            EditText gameName = dialog.findViewById(R.id.set_game_name);
            String gameNameText = gameName.getText().toString();

            if (gameNameText != null && !gameNameText.trim().equals("")) {
                VM.getCurrentGame().setName(gameName.getText().toString());

                VM.saveGame();
                VM.newGame();

                dialog.dismiss();

                ((CreateActivity) context).finish();

                Toast toast = Toast.makeText(context, "Game saved!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            } else {
                gameName.setError("Game name cannot be blank");
                gameName.requestFocus();
            }
        });

        return dialog;
    }
}

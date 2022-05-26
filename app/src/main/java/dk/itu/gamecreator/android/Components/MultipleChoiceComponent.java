package dk.itu.gamecreator.android.Components;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;

public class MultipleChoiceComponent extends SolutionComponent {

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;

    private EditText text1;
    private EditText text2;
    private EditText text3;
    private EditText text4;

    private Button addExtraOption;
    private Button deleteOption1;
    private Button deleteOption2;
    private Button deleteOption3;
    private Button deleteOption4;

    private LinearLayout checkboxLayout;
    private LinearLayout textviewLayout;
    private LinearLayout deleteOptionLayout;

    private List<Option> options = new ArrayList<>();

    private LinearLayout dynamicLayout;

    public MultipleChoiceComponent(int id) {
        super(id);
    }

    @Override
    public boolean saveComponent(Context context) {
        for (Option option : options) {
            String text = option.getText();
            if (text.length() == 0) {
                Toast toast = Toast.makeText(context,
                        "Text fields can't be empty", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return false;
            }
        }
        return true;
    }

    @Override
    public View getDisplayView(Context context) {

        GridLayout outer = new GridLayout(context);

        for (Option option : options) {
            Button b = new Button(context);
            b.setText(option.getDisplayText());
            b.setOnClickListener(view -> checkSolution(view, option, context));
            outer.addView(b);
        }
        return outer;
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.multi_test, null, false);
        /*
        checkBox1 = view.findViewById(R.id.checkbox_1);
        checkBox2 = view.findViewById(R.id.checkbox_2);
        checkBox3 = view.findViewById(R.id.checkbox_3);
        checkBox4 = view.findViewById(R.id.checkbox_4);

        text1 = view.findViewById(R.id.input_text1);
        text2 = view.findViewById(R.id.input_text2);
        text3 = view.findViewById(R.id.input_text3);
        text4 = view.findViewById(R.id.input_text4);

        deleteOption1 = view.findViewById(R.id.delete_option_1);
        deleteOption2 = view.findViewById(R.id.delete_option_2);
        deleteOption3 = view.findViewById(R.id.delete_option_3);
        deleteOption4 = view.findViewById(R.id.delete_option_4);

        checkboxLayout = view.findViewById(R.id.checkbox_layout);
        textviewLayout = view.findViewById(R.id.textview_layout);
        deleteOptionLayout = view.findViewById(R.id.delete_button_layout);
        */

        dynamicLayout = view.findViewById(R.id.multiple_choice_dynamic_layout);

        addExtraOption = view.findViewById(R.id.add_option);

        populateOptions(context);

        addExtraOption.setOnClickListener(v ->
                addOption(context)
        );

        return view;
    }

    public void addOption(Context context) {
        Option option = new Option(context);
        options.add(option);
        populateOptions(context);
        /*
        CheckBox checkBox = new CheckBox(context);
        checkBox.setHeight(70);
        TextInputLayout textInputLayout = new TextInputLayout(context);
        textInputLayout.setMinWidth(300);
        textInputLayout.setMinimumHeight(70);
        TextInputEditText textInputEditText =
                new TextInputEditText(context);
        textInputEditText.setWidth(300);
        textInputEditText.setHeight(70);
        textInputLayout.addView(textInputEditText);
        Button deleteButton = new Button(new ContextThemeWrapper(context, R.style.buttonStyle), null, 0);
        deleteButton.setText("X");
        deleteButton.setHeight(70);

        Option option = new Option(context, checkBox, textInputEditText, deleteButton);
        options.add(option);
        populateOptions(context);

         */
    }

    @Override
    public String getName() {
        return "Multiple choice";
    }

    public void checkSolution(View view, Option option, Context context) {
        if (option.isCorrect()) {
            Toast toast = Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            setSolved(true);
        } else {
            Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void populateOptions(Context context) {
        if (options.size() == 0) {
            /*checkboxLayout.removeAllViews();
            textviewLayout.removeAllViews();
            deleteOptionLayout.removeAllViews();

             */
                /*
                CheckBox checkBox = option.getCheckBox();
                checkBox.setChecked(option.isCorrect());
                checkboxLayout.addView(checkBox);

                EditText editText = option.getEditText();
                editText.setText(option.getDisplayText());
                textviewLayout.addView(editText);

                Button deleteButton = option.getDeleteButton();
                deleteOptionLayout.addView(deleteButton);

                 */
            options.add(new Option(context));
            options.add(new Option(context));
            options.add(new Option(context));
        }

        for (Option option : options) {
            System.out.println("_______________________3");
            if (!option.isAdded()) {
                option.setAdded(true);
                dynamicLayout.addView(option.getOptionView(context));

                option.getCheckBox().setOnClickListener(v ->
                        option.setCorrect(!option.isCorrect())
                );

                option.getDeleteButton().setOnClickListener(v ->
                        {
                            options.remove(option);
                            populateOptions(context);
                        }
                );
            }
        }
    }

    private class Option {
        private String text = "";
        private boolean isCorrect;
        private EditText editText;
        private Context context;
        private CheckBox checkBox;
        private Button deleteButton;
        private LinearLayout linearLayout;
        private boolean isAdded = false;

        public Option(Context context) {
            this.context = context;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            params.weight = 0.10f;

            linearLayout = new LinearLayout(context);
            linearLayout.setWeightSum(1.0f);

            checkBox = new CheckBox(context);
            checkBox.setLayoutParams(params);

            params.weight = 0.80f;

            editText = new TextInputEditText(context);
            editText.setLayoutParams(params);

            deleteButton = new Button(new ContextThemeWrapper(context, R.style.buttonStyle), null, 0);
            deleteButton.setText("X");
            deleteButton.setLayoutParams(params);

            linearLayout.addView(checkBox);
            linearLayout.addView(editText);
            linearLayout.addView(deleteButton);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }

        public View getOptionView(Context context) {

            //TextInputLayout textInputLayout = new TextInputLayout(context);
            //TextInputEditText textInputEditText =
                    new TextInputEditText(context);
            //textInputLayout.addView(textInputEditText);
            //textInputLayout.setLayoutParams(params);

            return linearLayout;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }

        public String getText() {
            String optionText = editText.getText().toString().trim();
            return optionText; }

        public String getDisplayText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public EditText getEditText() {
            return editText;
        }

        public void setEditText(EditText editText) {
            this.editText = editText;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }

        public void setDeleteButton(Button deleteButton) {
            this.deleteButton = deleteButton;
        }

        public void setAdded(boolean added) {
            this.isAdded = added;
        }

        public boolean isAdded() {
            return isAdded;
        }
    }
}

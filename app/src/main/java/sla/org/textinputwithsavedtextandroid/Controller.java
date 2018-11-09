package sla.org.textinputwithsavedtextandroid;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Controller {
    private TextView topLabel;
    private EditText topTextField;
    private EditText bottomTextField;
    private ListView sideListView;
    private ArrayAdapter<String> arrayAdapter;

    private Model model;

    Controller(TextView tv, EditText top, EditText bottom, ListView lv, Context cntxt) {
        topLabel = tv;
        topTextField = top;
        bottomTextField = bottom;
        sideListView = lv;
        arrayAdapter = (ArrayAdapter<String>)sideListView.getAdapter();

        model = new Model(cntxt);
        // Now that model has been initialized from a file, update View with saved values from Model
        if (model.getTopLabelText().length() > 0) {
            topLabel.setText(model.getTopLabelText());
        }
        if (model.getTopTextFieldText().length() > 0) {
            topTextField.setText(model.getTopTextFieldText());
        }
        if (model.getBottomTextFieldText().length() > 0) {
            bottomTextField.setText(model.getBottomTextFieldText());
        }
        ArrayList sideListViewTexts = model.getSideListViewTexts();
        for (int i = 0; i < sideListViewTexts.size(); i++) {
            arrayAdapter.add((String)sideListViewTexts.get(i));
        }

        topTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                topTextFieldUpdated();
                topLabel.setText(topTextField.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bottomTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomTextFieldReady();
            }
        });
    }

    void save() {
        System.out.println("Controller save");

        // push the latest GUI text into the model
        model.setAllData(topLabel.getText().toString(), topTextField.getText().toString(),
                bottomTextField.getText().toString(), arrayAdapter);
        model.save();
    }

    // these methods are event handler methods that are called when each GUI control is used
    public void topTextFieldClear() {
        System.out.println("topTextFieldClear: " + topTextField.getText());

        // Keep the top label intact but clear the text field
        topLabel.setText(topTextField.getText());
        topTextField.setText("");
    }

    public void topTextFieldUpdated() {
        System.out.println("topTextFieldUpdated: " + topTextField.getText());

        // Update the top label when the top text field is updated.
        if (topTextField.getText().length() > 0) {
            topLabel.setText(topTextField.getText());
        }
    }

    public void bottomTextFieldReady() {
        arrayAdapter.add(bottomTextField.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        bottomTextField.setText("");
    }

}

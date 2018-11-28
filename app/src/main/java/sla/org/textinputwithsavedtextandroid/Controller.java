package sla.org.textinputwithsavedtextandroid;

import android.content.Context;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Controller {
    private TextView topLabel;
    private EditText topTextField;
    private EditText bottomTextField;
    private ImageView topImageView;
    private ArrayList<Integer> imageIDs;
    private int whichImage;
    private ListView sideListView;
    private ArrayAdapter<String> arrayAdapter;

    private Model model;

    Controller(TextView tv, EditText top, EditText bottom, ImageView iv, ArrayList<Integer> imgIDs, ListView lv, Context cntxt) {
        topLabel = tv;
        topTextField = top;
        bottomTextField = bottom;
        topImageView = iv;
        imageIDs = imgIDs;
        sideListView = lv;
        arrayAdapter = (ArrayAdapter<String>)sideListView.getAdapter();

        // Read saved text by creating a Model object
        model = new Model(cntxt);
        // Update the GUI's text from via the Model object.
        whichImage = model.getCurrentImgID();
        topImageView.setImageResource(imageIDs.get(whichImage));
        topLabel.setText(model.getTopLabelText());
        topTextField.setText(model.getTopTextFieldText());
        bottomTextField.setText(model.getBottomTextFieldText());
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

        topImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextImage();
            }
        });

    }

    void save() {
        // push the latest GUI text into the model
        model.setAllData(whichImage, topLabel.getText().toString(), topTextField.getText().toString(),
                bottomTextField.getText().toString(), arrayAdapter);
        model.save();
    }

    public void topTextFieldUpdated() {
        // Update the top label when the top text field is updated.
        topLabel.setText(topTextField.getText());
    }

    public void bottomTextFieldReady() {
        arrayAdapter.add(bottomTextField.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        bottomTextField.setText("");
    }

    public void nextImage() {
        if (whichImage + 1 <imageIDs.size()) {
            whichImage = whichImage + 1;
        } else {
            whichImage = 0;
        }
        topImageView.setImageResource(imageIDs.get(whichImage));
    }
}

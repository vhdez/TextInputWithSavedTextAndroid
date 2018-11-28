package sla.org.textinputwithsavedtextandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TextInputWithSavedText extends AppCompatActivity {
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_with_saved_text);

        TextView topText = findViewById(R.id.TopText);
        EditText inputTopText = findViewById(R.id.InputTopText);
        EditText inputBottomList = findViewById(R.id.InputBottomList);

        ImageView topImageView = findViewById(R.id.TopImage);
        topImageView.setImageResource(R.drawable.condorito);
        ArrayList<Integer> imageIDs = new ArrayList();
        imageIDs.add(R.drawable.mario);
        imageIDs.add(R.drawable.greenie);
        imageIDs.add(R.drawable.spongebob);
        imageIDs.add(R.drawable.condorito);

        ListView bottomListView = findViewById(R.id.BottomListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        bottomListView.setAdapter(arrayAdapter);

        controller = new Controller(topText, inputTopText, inputBottomList, topImageView, imageIDs, bottomListView, getApplicationContext());
    }

    @Override
    protected void onStop() {
        controller.save();
        super.onStop();
    }
}

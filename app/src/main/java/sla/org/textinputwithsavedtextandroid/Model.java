package sla.org.textinputwithsavedtextandroid;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.io.*;
import java.util.ArrayList;

// the Model contains all of the View's text so that it can be read and written to a file
public class Model {
    // This is the App's context (where it can save data)
    private Context context;

    // These fields are the mutable text from the View
    private String topLabelText;
    private String topTextFieldText;
    private String bottomTextFieldText;
    private ArrayList<String> sideListViewTexts;

    // Create a model that is generic (did NOT get restored from saved data)
    Model(Context cntxt) {
        context = cntxt;

        topLabelText = "";
        topTextFieldText = "";
        bottomTextFieldText = "";
        sideListViewTexts = new ArrayList();

        // Try restoring saved text from file
        try {
            // Open file to read saved text
            System.out.println("Model() opening SavedText.txt");
            File savedText = new File(context.getFilesDir(), "SavedText.txt");
            if (savedText.exists()) {
                System.out.println("Model() found SavedText.txt");
                BufferedReader input = new BufferedReader(new FileReader(savedText));

                topLabelText = input.readLine();
                System.out.println("Model() read topLabelText:" + topLabelText);
                topTextFieldText = input.readLine();
                System.out.println("Model() read topTextFieldText:" + topTextFieldText);
                bottomTextFieldText = input.readLine();
                System.out.println("Model() read bottomTextFieldText:" + bottomTextFieldText);
                String newSideListText = input.readLine();
                int i = 1;
                System.out.println("Model() read newSideListText " + i++ + ":" + newSideListText);
                while (newSideListText != null) {
                    sideListViewTexts.add(newSideListText);
                    newSideListText = input.readLine();
                    System.out.println("Model() read newSideListText " + i++ + ":" + newSideListText);
                }

                System.out.println("Model() closing SavedText.txt");
                // Close file
                input.close();
                System.out.println("Model() closed SavedText.txt");
            }
        } catch (Exception e) {
            System.out.println("Model() threw exception");
            e.printStackTrace();
        }
    }

    // write the model to a file
    void save() {
        try {

            System.out.println("Model.save() opening SavedText.txt");
            // Create the file if necessary
            File savedText = new File(context.getFilesDir(), "SavedText.txt");

            // Write the final model to a saved file
            BufferedWriter writer = new BufferedWriter(new FileWriter(savedText));
            if (writer != null) {
                if (topLabelText != null) {
                    System.out.println("Model.save() wrote topLabelText: " + topLabelText);
                    writer.write(topLabelText);
                } else {
                    System.out.println("Model.save() wrote topLabelText: ");
                    writer.write("");
                }
                writer.newLine();
                if (topTextFieldText != null) {
                    System.out.println("Model.save() wrote topTextFieldText: " + topTextFieldText);
                    writer.write(topTextFieldText);
                } else {
                    System.out.println("Model.save() wrote topTextFieldText: ");
                    writer.write("");
                }
                writer.newLine();
                if (bottomTextFieldText != null) {
                    System.out.println("Model.save() wrote bottomTextFieldText: " + bottomTextFieldText);
                    writer.write(bottomTextFieldText);
                } else {
                    System.out.println("Model.save() wrote bottomTextFieldText: ");
                    writer.write("");
                }
                writer.newLine();
                int length = sideListViewTexts.size();
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        System.out.println("Model.save() wrote sideListViewTexts" + i + ": " + sideListViewTexts.get(i));
                        writer.write(sideListViewTexts.get(i));
                        writer.newLine();
                    }
                } else {
                    System.out.println("Model.save() wrote sideListViewTexts: ");
                    writer.write("");
                    writer.newLine();
                }
            }
            System.out.println("Model.save() closing SavedText.txt");
            writer.close();
            System.out.println("Model.save() closed SavedText.txt");

        } catch (Exception e) {
            System.out.println("Model.save() threw exception!!!");
            e.printStackTrace();
        }

    }

    // getter and setter methods for all of the Model's fields
    String getTopLabelText() {
        return topLabelText;
    }

    String getTopTextFieldText() {
        return topTextFieldText;
    }

    String getBottomTextFieldText() {
        return bottomTextFieldText;
    }

    ArrayList<String> getSideListViewTexts() {
        return sideListViewTexts;
    }

    void setAllData(String updatedTopLabelText, String updatedTopTextFieldText,
                    String updatedBottomTextFieldText, ArrayAdapter<String> listViewItems) {
        // Update the model with all text currently seen in View
        topLabelText = updatedTopLabelText;
        topTextFieldText = updatedTopTextFieldText;
        bottomTextFieldText = updatedBottomTextFieldText;

        int length = listViewItems.getCount();
        sideListViewTexts.clear();
        for (int i = 0; i < length; i++) {
            sideListViewTexts.add(listViewItems.getItem(i));
        }
    }
}

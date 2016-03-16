package com.example.caleb.elasticitycalculator;

import android.annotation.TargetApi;
import android.support.v7.app.ActionBarActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method called when CALCULATE button is pressed.
    public void calculate(View view) {
        try {
            // Take in the inputs from the text boxes.
            double topInitial = Double.parseDouble(
                    ((EditText) findViewById(R.id.topInitialText)).getText().toString());
            double topFinal = Double.parseDouble(
                    ((EditText) findViewById(R.id.topFinalText)).getText().toString());
            double bottomInitial = Double.parseDouble(
                    ((EditText) findViewById(R.id.bottomInitialText)).getText().toString());
            double bottomFinal = Double.parseDouble(
                    ((EditText) findViewById(R.id.bottomFinalText)).getText().toString());

            // Calculate the elasticity.
            double elasticity = ((topInitial - topFinal) * (bottomInitial + topInitial))
                    / ((topInitial + topFinal) * (bottomInitial - bottomFinal));

            // Set the text of the result box to the calculated elasticity.
            ((TextView)findViewById(R.id.textView)).setText("" + elasticity);
        } catch (NumberFormatException e) {
            // Alert the user if one of the text boxes is empty.
            Toast.makeText(this, "Please input a value in each field.", Toast.LENGTH_SHORT).show();
        } catch (ArithmeticException e) {
            // If an ArithmeticException was reached, set the result text to "0".
            ((TextView)findViewById(R.id.textView)).setText("0");
        }
    }

    // Copy text contained by the view that calls this method. To be called by a TextView.
    public void copyText(View view) {
        // Create a new clip from the view's text.
        ClipData clip = ClipData.newPlainText("Elasticity", ((TextView)view).getText());

        // Set the clipboard's contents to the new clip.
        ((ClipboardManager)getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(clip);

        // Inform the user that text has been copied to the clipboard.
        Toast.makeText(this, "Number copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(19)
    // Change the layout to the expanded full view.
    public void expand(View view) {
        // Specify the root container.
        ViewGroup rootContainer = (ViewGroup)findViewById(R.id.rootContainer);

        // Create the initial scene.
        //Scene start = Scene.getSceneForLayout(rootContainer, R.layout.activity_main, this);

        // Create the final scene.
        Scene finish = Scene.getSceneForLayout(rootContainer, R.layout.expanded, this);

        // Have the starting scene enter.
        //start.enter();

        // Transition to the finishing scene.
        TransitionManager.go(finish, new android.transition.ChangeBounds());
    }

    @TargetApi(19)
    // Change the layout to the collapsed view.
    public void collapse(View view) {
        // Specify the root container.
        ViewGroup rootContainer = (ViewGroup)findViewById(R.id.rootContainer);

        // Create the initial scene.
        Scene start = Scene.getSceneForLayout(rootContainer, R.layout.activity_main, this);

        // Create the final scene.
        //Scene finish = Scene.getSceneForLayout(rootContainer, R.layout.expanded, this);

        // Have the starting scene enter.
        //start.enter();

        // Transition to the finishing scene.
        TransitionManager.go(start, new android.transition.ChangeBounds());
    }
}

package com.example.abraham.diceapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;


public class DiceActivity extends Activity {

    private RandomizeDice mRandomizeDice = new RandomizeDice();
    //private ColorWheel mColorWheel = new ColorWheel();

    //View.OnClickListener listener = new View.OnClickListener() {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  @Override
        // protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        //Declare view Variables
        final TextView Dice1 = (TextView) findViewById(R.id.Dice1);
        final TextView Dice2 = (TextView) findViewById(R.id.Dice2);
        final TextView Addition = (TextView) findViewById(R.id.Addition);
        final TextView Multiplication = (TextView) findViewById(R.id.Multiplication);
        final TextView Double = (TextView) findViewById(R.id.Double);
        final Button RandomizeButton = (Button) findViewById(R.id.button);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        View.OnClickListener listener = new View.OnClickListener() {
            //    View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // @Override
                //public void onClick(View view) {
                //Randomize Dice
                int iDice1 = mRandomizeDice.rDice();
                int iDice2 = mRandomizeDice.rDice();
                //Set Dice to show updated dice
                Dice1.setText(iDice1);
                Dice2.setText(iDice2);
                //Answer Questions + Update Answers;
                int summation = iDice1 + iDice2;
                Addition.setText(summation);
                int multiplication = iDice1 * iDice2;
                Multiplication.setText(multiplication);
                if (iDice1 == iDice2) {
                    Double.setText("Yes");
                } else {
                    Double.setText("No");
                }
            }
            //;
        };
    }
}




/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}*/

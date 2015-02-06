package com.example.abraham.mymainapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainMenuActivity extends Activity {


    private Button bioButton;
    private Button interestButton;
    private Button goodButton;
    private Button badButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bioButton = (Button)findViewById(R.id.bioButton);
        bioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(0);
            }
        });

        interestButton = (Button)findViewById(R.id.interestsButton);
        interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(1);
            }
        });

        goodButton = (Button)findViewById(R.id.goodButton);
        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo(2);
            }
        });

        badButton = (Button)findViewById(R.id.badButton);
        badButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = "Error: There is nothing to dislike about Abraham!";
                Toast toast = Toast.makeText(MainMenuActivity.this, newText, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    // Starts Story Activity
    private void showInfo(int number){
        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("number", number);
        startActivity(intent);
    }
}


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
}*/

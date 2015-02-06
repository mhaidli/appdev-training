package com.example.abraham.mymainapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class InformationActivity extends Activity {

    private InfoArray mInfoArray = new InfoArray();
    private Info mInfo;
    private ImageView mImageView;
    private TextView mTextView;
    private int number;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        mImageView = (ImageView)findViewById(R.id.imageView);
        mTextView = (TextView)findViewById(R.id.textView);
        //Get number
        Intent intent = getIntent();
        number = intent.getIntExtra("number", 1);
        //number = 0;
        loadInfo(number);

        mButton = (Button)findViewById(R.id.backButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void loadInfo(int number){

        mInfo = mInfoArray.getInfo(number);
        Drawable drawable = getResources().getDrawable(mInfo.getImageID());
        mImageView.setImageDrawable(drawable);
        String textToShow = mInfo.getText();
        mTextView.setText(textToShow);
    }

}


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_information, menu);
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

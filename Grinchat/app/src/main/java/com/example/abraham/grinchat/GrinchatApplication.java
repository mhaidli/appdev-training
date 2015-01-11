package com.example.abraham.grinchat;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Abraham on 1/10/2015.
 */


public class GrinchatApplication extends Application {

    @Override
     public void OnCreate() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "xwlFf55OTK5ztXzU5gTZWw92dXQwxwEqJ2QpJerg", "A0Ix4MilSXu60ytfP2YNYe8l5dIgTLFYgCvXektk");

        /*
        Testing connection to parse
        ParseObject testObject = new ParseObject("TestObject");
testObject.put("foo", "bar");
testObject.saveInBackground();
         */
    }
}

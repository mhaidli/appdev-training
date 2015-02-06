package com.example.abraham.grinchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


//public class SignUpActivity extends ActionBarActivity {
public class SignUpActivity extends Activity {

    protected EditText mUserName;
    protected EditText mPassword;
    protected EditText mPasswordConfirmation;
    protected EditText mEmail;
    protected Button mSignUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_sign_up);

        mUserName = (EditText) findViewById(R.id.usernameText);
        mPassword = (EditText) findViewById(R.id.passwordText);
        mPasswordConfirmation = (EditText) findViewById(R.id.confirmPasswordText);
        mEmail = (EditText) findViewById(R.id.emailText);
        mSignUpButton = (Button) findViewById(R.id.signUpButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUserName.getText().toString();
                String password = mPassword.getText().toString();
                String passwordConfirmation = mPasswordConfirmation.getText().toString();
                String email = mEmail.getText().toString();

                //Trim strings
                username = username.trim();
                password = password.trim();
                passwordConfirmation = passwordConfirmation.trim();
                email = email.trim();

                if (username.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.error_message_missing_field_signup);
                    builder.setTitle(R.string.error_message_missing_field_title);
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (password.equals(passwordConfirmation)) {

                    setProgressBarIndeterminateVisibility(true);
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);

                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            setProgressBarIndeterminateVisibility(false);
                            if (e == null) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle(R.string.error_message_missing_field_title);
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.error_message_mismatching_passwords);
                    builder.setTitle(R.string.error_message_missing_field_title);
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }
}


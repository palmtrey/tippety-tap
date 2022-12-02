package com.example.tippetytap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class NewUserActivity extends AppCompatActivity {

    int ENTER_PASSWORD_COUNT = 3;

    Vector<Timing> users = new Vector<Timing>();

    int userIndex = 0;
    boolean typingPassword = false;
    int passwordCount = 0;
    Vector<Character> chars = new Vector<Character>();
    Vector<Long> pressTimes = new Vector<Long>();
    StringBuffer oldPassword = new StringBuffer();

    Timing user2;




    EditText usernameField;
    Button nextButton0;

    TextView enterPasswordView;
    TextView countView;
    EditText passwordField;
    Button nextButton1;

    Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("[INFO]", "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        usernameField = (EditText) findViewById(R.id.usernameField);
        nextButton0 = (Button) findViewById(R.id.nextButton0);

        enterPasswordView = (TextView) findViewById(R.id.enterPasswordView);
        countView = (TextView) findViewById(R.id.countView);
        passwordField = (EditText) findViewById(R.id.passwordField);
        nextButton1 = (Button) findViewById(R.id.nextButton1);

        completeButton = (Button) findViewById(R.id.completeButton);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            ArrayList<Timing> l = (ArrayList<Timing>) extras.getSerializable("users");
            users = new Vector<Timing>(l);
            Log.wtf("[INFO]", "Length of users vector: " + Integer.toString(users.size()));
        }



        // Set visibility of starting elements
        enterPasswordView.setVisibility(View.INVISIBLE);
        countView.setVisibility(View.INVISIBLE);
        passwordField.setVisibility(View.INVISIBLE);
        nextButton1.setVisibility(View.INVISIBLE);
        completeButton.setVisibility(View.INVISIBLE);

        // Set focus change function for passwordField
        passwordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.wtf("[INFO]", "Typing password...");
                typingPassword = true;
            }
        });


        // Add TextWatcher() to passwordField
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (typingPassword){
                    String userEntered = s.toString();
                    Date date = new Date();
                    long time = date.getTime();
                    Log.wtf("[INFO]", Long.toString(time));

                    Log.wtf("[INFO]", s.toString());
//                    Log.wtf("[INFO]", Integer.toString(start));
//                    Log.wtf("[INFO]", Integer.toString(before));
//                    Log.wtf("[INFO]", Integer.toString(count));

                    if (oldPassword.length() == 0){
                        chars.clear();
                        pressTimes.clear();

                        Log.wtf("[INFO]", "Chars and pressTimes cleared.");
                    }

                    // If character is a backspace, remove the previous char and time
                    if (userEntered.length() < oldPassword.length()){
                        chars.remove(chars.size() - 1);
                        pressTimes.remove(pressTimes.size() - 1);

                        Log.wtf("[INFO]", "Removed from chars and pressTimes.");
                        Log.wtf("[INFO]", "chars: " + chars.toString());
                        Log.wtf("[INFO]", "pressTimes: " + pressTimes.toString());

                    // Else, add the character and time to the arrays
                    }else{
                        chars.add(s.charAt(s.length() - 1));
                        pressTimes.add(time);

                        Log.wtf("[INFO]", "Added to chars and pressTimes.");
                        Log.wtf("[INFO]", "chars: " + chars.toString());
                        Log.wtf("[INFO]", "pressTimes: " + pressTimes.toString());
                    }
                    oldPassword.setLength(0);
                    oldPassword.append(userEntered);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void next0(View v){
        String username = usernameField.getText().toString();

        // Make sure the username field isn't blank.
        if (!username.matches("")){

            // Check if any existing users have the same username
            for (int i = 0; i < users.size(); i++){
                if (username.equals(users.get(i).getUsername())){
                    Log.wtf("[UERROR]", "A user with that username already exists.");
                    return;
                }
            }
            // If there are no usernames matching the proposed one, proceed to create the user.
            Timing user = new Timing(username);
            user2 = new Timing(username);
            userIndex = users.size();
            users.add(user);

            // Set visibility of username fields
            usernameField.setVisibility(View.INVISIBLE);
            nextButton0.setVisibility(View.INVISIBLE);

            // Set visibility of password fields
            enterPasswordView.setVisibility(View.VISIBLE);
            countView.setVisibility(View.VISIBLE);
            passwordField.setVisibility(View.VISIBLE);
            nextButton1.setVisibility(View.VISIBLE);


        }else {
            // If username field is blank, do nothing.
            return;
        }
    }

    public void next1(View v){
        Timing user = users.get(userIndex);
        Log.wtf("[SUMMARY3]", users.get(userIndex).summary());
        String currentPassword = user.getPassword();

        String userEntered = passwordField.getText().toString();


        if(!userEntered.equals("")){
            if (currentPassword.equals("")){
                user.setPassword(userEntered);
                user.setChars(chars);
                user.pushTimeSequence(pressTimes);
                passwordCount++;
                users.set(userIndex, user);

            }else if(currentPassword.equals(userEntered)){

                Log.wtf("[SUMMARY2]", user.summary());
                user.pushTimeSequence(pressTimes);

                Log.wtf("[SUMMARY2]", user.summary());
                passwordCount++;
                users.set(userIndex, user);


            }else{
                // Do nothing if the user's password does not match the previous password entered
                return;
            }




            // Update the UI
            typingPassword = false;
            passwordField.setText("");
            typingPassword = true;
            countView.setText(Integer.toString(passwordCount) + "/" + Integer.toString(ENTER_PASSWORD_COUNT) + " complete");

            if (passwordCount == ENTER_PASSWORD_COUNT - 1){
                nextButton1.setVisibility(View.INVISIBLE);
                completeButton.setVisibility(View.VISIBLE);
            }

            // Clear oldPassword
            oldPassword.setLength(0);

            // Partial user summary
            Log.wtf("[SUMMARY]", "Partial User Summary:\n" + users.get(userIndex).summary());
            Log.wtf("[INFO]", "users vector size: " + Integer.toString(users.size()));

        }else {
            // Do nothing if the user did not enter anything in the password field.
            return;
        }
    }

    public void complete(View v){
        Timing user = users.get(userIndex);
        String currentPassword = user.getPassword();

        String userEntered = passwordField.getText().toString();

        if(currentPassword.equals(userEntered)){
            user.pushTimeSequence(pressTimes);
            passwordCount++;
            users.set(userIndex, user);

            // Print summary of user
            Log.wtf("[SUMMARY]", users.get(userIndex).summary());

            // Return to main activity
            Intent mainIntent = new Intent( this, MainActivity.class );
            mainIntent.putExtra("users", users);
            this.startActivity( mainIntent );
        }else{
            return;
        }


    }


}
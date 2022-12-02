package com.example.tippetytap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    double THRESHOLD = 10;

    Vector<Timing> users = new Vector<Timing>();

    boolean typingPassword = false;
    Vector<Character> chars = new Vector<Character>();
    Vector<Long> pressTimes = new Vector<Long>();
    StringBuffer oldPassword = new StringBuffer();

    EditText usernameEdit;
    EditText passwordEdit;

    TextView resultTextbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab extras from NewUserActivity if necessary
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<Timing> l = (ArrayList<Timing>) extras.getSerializable("users");
            users = new Vector<Timing>(l);
            Log.wtf("[INFO]", "Length of users vector: " + Integer.toString(users.size()));
        }

        for (int i = 0; i < users.size(); i++){
            Log.wtf("[SUMMARY_MAIN]", "User " + Integer.toString(i) + ": " + users.get(i).summary());
        }




        usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);

        resultTextbox = (TextView) findViewById(R.id.resultTextbox);

        resultTextbox.setVisibility(View.INVISIBLE);

        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.wtf("[INFO]", "Typing password...");
                typingPassword = true;
            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (typingPassword){
                    String userEntered = s.toString();
                    Date date = new Date();
                    long time = date.getTime();
                    Log.wtf("[INFO]", Long.toString(time));

                    Log.wtf("[INFO]", s.toString());

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

    public void newUser(View v){
        Log.i("[INFO]", "New user function");
        Timing user = new Timing("Cam");
//        users.add(user);
        Intent newUserIntent = new Intent( this, NewUserActivity.class );
        newUserIntent.putExtra("users", users);
        this.startActivity( newUserIntent );

    }

    // Submit button pressed
    public void submit(View v){
        double distance;
        String username = usernameEdit.getText().toString();

        Log.wtf("[INFO]", "Username entered: " + username);

        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equals(username)){



                Timing user = users.get(i);

                if (user.getChars().equals(chars)){
                    Timing comparison = new Timing("compare");
                    comparison.setPassword(user.getPassword());
                    comparison.pushTimeSequence(pressTimes);
                    distance = user.compare(comparison);

                    Log.wtf("[OUTPUT]", "Distance: " + Double.toString(distance));

                    if (distance < THRESHOLD){
                        resultTextbox.setText("Password accepted.\nDistance: " + Double.toString(distance));
//                    resultTextbox.setTextColor(0x62c21f);
                    }else{
                        resultTextbox.setText("Password not accepted.\nDistance: " + Double.toString(distance));
//                    resultTextbox.setTextColor(0xc2381f);
                    }


                }else{
                    resultTextbox.setText("Wrong password. Try again.");

                }
                resultTextbox.setVisibility(View.VISIBLE);



                return;
            }else{
                resultTextbox.setText("Invalid username.");
                resultTextbox.setVisibility(View.VISIBLE);
            }
        }

        resultTextbox.setText("No users have been created yet.\nPress New User to create one.");
        resultTextbox.setVisibility(View.VISIBLE);

        Log.wtf("[USER_WARN]", "Invalid username!");

        return;




    }


}


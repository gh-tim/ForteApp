package com.example.forte;

//Name: Timothy Kwek Chong Jun, Student ID: 22078620

//import
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    //define variables
    TextView password;
    Button btn;
    Button btnCopy;
    Integer lengthEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show action bar for the application
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcherf);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //find txtLength, password, and btn
        EditText txtLength = (EditText)findViewById(R.id.txtLength);
        password = (TextView)findViewById(R.id.password);
        btn = (Button)findViewById(R.id.btn);

        //default launch the app with a random password
        password.setText(generatePassword(10));

        //button to generate new password
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lengthEntered = Integer.parseInt(txtLength.getText().toString());
                }
                catch(NumberFormatException ex) {
                    //Show an error toast when no number is entered
                    Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (lengthEntered <= 20 && lengthEntered > 5) {
                    password.setText(generatePassword(lengthEntered));
                } else {
                    //Show an error toast when number entered is out of bounds (less than 5 and more than 20 characters)
                    Toast.makeText(MainActivity.this, "Length must be between 5 and 20", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //find btnCopy
        btnCopy = (Button)findViewById(R.id.btnCopy);
        //button to copy the most recently generated password
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordValue = password.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied text", passwordValue);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //function to generate a cryptographically strong random password using SecureRandom
    public String generatePassword(int txtLength) {
        final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String NUMBERS = "0123456789";
        final String SPECIAL = "!@#$%^&*()-_+=";

        String characters = LOWERCASE + UPPERCASE + NUMBERS + SPECIAL;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < txtLength; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        //return the password
        return password.toString();
    }

}
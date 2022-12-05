package com.example.trailmakingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private String firstName;
    private String lastName;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.test1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTMT(1);
            }
        });

        button2 = (Button) findViewById(R.id.test2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTMT(2);
            }
        });
    }

    public void openTMT(int i) {
        if (i == 1) {
            Intent intent = new Intent(this, tmt.class);
            startActivity(intent);
        }
        if (i == 2) {
            Intent intent = new Intent(this, tmt_large.class);
            startActivity(intent);
        }
    }

    public void onBtnClick (View view) {
        TextView txtFirstName = findViewById(R.id.txtFirstName);
        TextView txtLastName = findViewById(R.id.txtLastName);
        TextView txtEmail = findViewById(R.id.txtEmail);

        EditText edtTxtFirstName = findViewById(R.id.edtTxtFirstName);
        EditText edtTxtLastName = findViewById(R.id.edtTxtLastName);
        EditText edtTxtEmail = findViewById(R.id.edtTxtEmail);

        txtFirstName.setText("First Name: " + edtTxtFirstName.getText().toString());
        txtLastName.setText("Last Name: " + edtTxtLastName.getText().toString());
        txtEmail.setText("Info: " + edtTxtEmail.getText().toString());

        firstName = edtTxtFirstName.getText().toString();
        lastName = edtTxtLastName.getText().toString();
        email = edtTxtEmail.getText().toString();

        Log.i("tag", firstName);
        Log.i("tag", lastName);
        Log.i("tag", email);
    }
}
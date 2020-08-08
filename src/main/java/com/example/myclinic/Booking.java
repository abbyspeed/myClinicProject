package com.example.myclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Booking extends AppCompatActivity {

    private EditText name, ic, address, phone, date;
    private Button proceed;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        radioGroup = findViewById(R.id.radioGroup);

        Spinner mySpinnerDoctor = (Spinner) findViewById(R.id.e_doctor);

        ArrayAdapter<String> myAdapterDoctor = new ArrayAdapter<String>(Booking.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.doctors));
        myAdapterDoctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnerDoctor.setAdapter(myAdapterDoctor);

        Spinner mySpinnerTime = (Spinner) findViewById(R.id.e_time);

        ArrayAdapter<String> myAdapterTime = new ArrayAdapter<String>(Booking.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));
        myAdapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnerTime.setAdapter(myAdapterTime);

        proceed = (Button) findViewById(R.id.b_display);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Booking.this, Appointments.class));
                //values of getText name, ic, address, phone, date, radiobutton must be passed into here and the database
            }
        });

    }

    public void checkButton(View view){
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "You have selected " + radioButton.getText(),
                        Toast.LENGTH_SHORT).show();
    }

}



package com.example.myclinic;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Update extends AppCompatActivity {
    Button proceed;
    RadioGroup gender;

    FirebaseAuth auth;
    FirebaseFirestore store;

    Appointment appointment;
    Date date = new Date();
    Spinner doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        TextView header = findViewById(R.id.head_b);
        header.setText("Appointment Update");

        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");

        appointment = new Appointment(uid, null, null);

        // Initialized Firebase package
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        doctor = findViewById(R.id.e_doctor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctor.setAdapter(adapter);
        doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                appointment.setDoctor(adapterView.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Link components
        proceed = findViewById(R.id.b_display);
        gender = findViewById(R.id.radioGroup);
        Button btn = findViewById(R.id.button2);
        Button btn2 = findViewById(R.id.button3);

        proceed.setText("AMEND");

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            date.setYear(year);
            date.setDate(day);
            date.setMonth(month);
        }, date.getYear(), date.getMonth(), date.getDay());

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hour, minute) -> {
            date.setHours(hour);
            date.setMinutes(minute);
        }, date.getHours(), date.getMinutes(), false
        );

        btn.setOnClickListener((view) -> {
            datePickerDialog.show();
        });

        btn2.setOnClickListener((view) -> {
            timePickerDialog.show();
        });


        proceed.setOnClickListener((view) -> {
            submitForm();
        });

    }

    protected void submitForm() {
//        cur_user.setName(name.getText().toString());
//        cur_user.setAddress(address.getText().toString());
//        cur_user.setPhoneno(address.getText().toString());
//
//        if (gender.getCheckedRadioButtonId() == R.id.male) {
//            cur_user.setGender("male");
//        } else if (gender.getCheckedRadioButtonId() == R.id.female) {
//            cur_user.setGender("female");
//        } else {
//            Toast.makeText(this, "You have not selected any gender", Toast.LENGTH_SHORT).show();
//            return;
//        }

        DateFormat format = DateFormat.getDateTimeInstance();
        appointment.setDatentime(format.format(date));

        DocumentReference ref = store.collection("clientinfo")
                .document(auth.getUid())
                .collection("appointments").document(appointment.getUid());


        ref.set(appointment.getPacket()).addOnSuccessListener(
                documentReference -> {
                    Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
                    toast.show();
                }
        ).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());


    }


}



package com.example.myclinic;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

public class Booking extends Fragment {
    Button proceed;
    RadioGroup gender;

    FirebaseAuth auth;
    FirebaseFirestore store;

    Appointment appointment;
    Date date = new Date();
    Spinner doctor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_booking, container, false);

        super.onCreate(savedInstanceState);

        appointment = new Appointment(null, null, null);

        // Initialized Firebase package
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        doctor = root.findViewById(R.id.e_doctor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
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
        proceed = root.findViewById(R.id.b_display);
        Button btn = root.findViewById(R.id.button2);
        Button btn2 = root.findViewById(R.id.button3);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, year, month, day) -> {
            date.setYear(year);
            date.setDate(day);
            date.setMonth(month);
        }, date.getYear(), date.getMonth(), date.getDay());

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (timePicker, hour, minute) -> {
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

        return root;
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
                .collection("appointments").document();

        appointment.setUid(ref.getId());

        ref.set(appointment.getPacket()).addOnSuccessListener(
                documentReference -> {
                    Toast toast = Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT);
                    toast.show();
                }
        ).addOnFailureListener(e -> Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show());


    }


}



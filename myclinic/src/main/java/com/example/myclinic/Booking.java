package com.example.myclinic;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
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
    Calendar myCalendar = Calendar.getInstance();
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
            myCalendar.set(year, month, day, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE));
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (timePicker, hour, minute) -> {
            myCalendar.set(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE),
                    hour, minute);
        }, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true
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

        addReminder();

        DateFormat format = DateFormat.getDateTimeInstance();
        appointment.setDatentime(format.format(myCalendar.getTime()));

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

    public void addReminder() {
        Calendar begin = Calendar.getInstance();
        begin.set(2019, 0, 19, 7, 30);
        Calendar end = Calendar.getInstance();
        end.set(2019, 0, 19, 8, 30);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Appointment")
                .putExtra(CalendarContract.Events.DESCRIPTION, appointment.getDoctor());

        startActivity(intent);
    }

}



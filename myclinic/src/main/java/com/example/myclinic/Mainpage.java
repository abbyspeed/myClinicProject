package com.example.myclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Mainpage extends AppCompatActivity {
    private Button appointments, doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        appointments = findViewById(R.id.b_appoint);
        doctors = findViewById(R.id.b_doctors);

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Mainpage.this, Appointments.class));
            }
        });

        doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Mainpage.this, Doctors.class));
            }
        });
    }
}

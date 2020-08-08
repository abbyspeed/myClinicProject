package com.example.myclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Appointments extends AppCompatActivity {

    private Button back, booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        back=(Button)findViewById(R.id.back2home);
        booking=(Button)findViewById(R.id.b_book);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Appointments.this, Mainpage.class));
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Appointments.this, Booking.class));
            }
        });
    }
}

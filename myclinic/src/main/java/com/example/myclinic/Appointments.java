package com.example.myclinic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Appointments extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button back, booking;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter cAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();

        if (cAdapter != null) {
            cAdapter.startListening();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();

        if (cAdapter != null) {
            cAdapter.stopListening();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        back = findViewById(R.id.back2home);
        booking = findViewById(R.id.b_book);

        recyclerView = findViewById(R.id.recycleview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Query query = db.collection("clientinfo")
                .document(auth.getUid())
                .collection("appointments");

        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query, Appointment.class)
                .build();

        FirestoreRecyclerAdapter<Appointment, Adapter.ViewHolder> cAdapter = new FirestoreRecyclerAdapter<Appointment, Adapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position, @NonNull Appointment model) {
                holder.getDeleteBtn().setOnClickListener((view -> {
                    db.collection("clientinfo")
                            .document(auth.getUid())
                            .collection("appointments")
                            .document(model.getUid())
                            .delete();
                }));

                holder.getUpdateBtn().setOnClickListener((view -> {
                    Intent intent = new Intent(Appointments.this, Update.class);
                    intent.putExtra("UID", model.getUid());
                    startActivity(intent);
                }));


                if (model.getDoctor() != null || model.getDatentime() != null) {
                    Log.w("DATE", model.getPacket().keySet().toString());
                    holder.getDoctorView().setText(model.getDoctor());

                    DateFormat format = DateFormat.getDateTimeInstance();
                    try {
                        Date date = format.parse(model.getDatentime());
                        holder.getDateView().setText(date.getDate() + " " + date.getMonth() + " " + date.getYear());
                        holder.getTimeView().setText(date.getHours() + " " + date.getMinutes());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }

            @NonNull
            @Override
            public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.w("Adapter", "Loggin1");
                ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.dbox, parent, false);

                Adapter.ViewHolder vh = new Adapter.ViewHolder(v);

                return vh;

            }
        };

        cAdapter.startListening();

        recyclerView.setAdapter(cAdapter);

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

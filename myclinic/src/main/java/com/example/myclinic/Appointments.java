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

import java.util.ArrayList;

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
        cAdapter.stopListening();
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
                .whereEqualTo("uid", auth.getCurrentUser().getUid());


        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        FirestoreRecyclerAdapter<User, Adapter.ViewHolder> cAdapter = new FirestoreRecyclerAdapter<User, Adapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position, @NonNull User model) {
                Log.w("Adapter", "Loggin2");
                holder.textview.setText(model.packet.get("name").toString());
            }

            @NonNull
            @Override
            public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.w("Adapter", "Loggin1");
                TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.dbox, parent, false);

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

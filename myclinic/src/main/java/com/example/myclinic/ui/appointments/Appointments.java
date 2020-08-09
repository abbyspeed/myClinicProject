package com.example.myclinic.ui.appointments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclinic.Adapter;
import com.example.myclinic.Appointment;
import com.example.myclinic.R;
import com.example.myclinic.Update;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Appointments extends Fragment {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter cAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    public void onStart() {
        super.onStart();

        if (cAdapter != null) {
            cAdapter.startListening();
        }

    }


    @Override
    public void onStop() {
        super.onStop();

        if (cAdapter != null) {
            cAdapter.stopListening();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_appointments, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Button booking = root.findViewById(R.id.b_book);

        recyclerView = root.findViewById(R.id.recycleview);

        layoutManager = new LinearLayoutManager(getContext());
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
                    Intent intent = new Intent(getContext(), Update.class);
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

        booking.setOnClickListener(view -> {
            NavDirections action = AppointmentsDirections.actionAppointmentsToBooking();
            Navigation.findNavController(getView()).navigate(action);
        });

        return root;
    }
}

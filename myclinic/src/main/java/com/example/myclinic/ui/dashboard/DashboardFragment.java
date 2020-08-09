package com.example.myclinic.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myclinic.Login;
import com.example.myclinic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DashboardFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView name, icno, addr, phoneno;
        name = root.findViewById(R.id.textname);
        icno = root.findViewById(R.id.texticno);
        addr = root.findViewById(R.id.textaddr);
        phoneno = root.findViewById(R.id.textphoneno);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore store = FirebaseFirestore.getInstance();

        store.collection("clientinfo").document(auth.getUid()).addSnapshotListener((value, error) -> {
            name.setText(value.get("name").toString());
            icno.setText(value.get("icno").toString());
            addr.setText(value.get("address").toString());
            phoneno.setText(value.get("phoneno").toString());
        });

        Button logoutBtn = root.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(view -> {
            auth.signOut();
            getActivity().finish();
            startActivity(new Intent(getContext(), Login.class));
        });

        return root;
    }
}
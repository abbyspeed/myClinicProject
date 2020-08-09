package com.example.myclinic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.myclinic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button appointBtn = root.findViewById(R.id.b_appoint);
        TextView name = root.findViewById(R.id.user);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore store = FirebaseFirestore.getInstance();
        store.collection("clientinfo").document(auth.getUid())
                .addSnapshotListener((value, error) -> {
                    name.setText(value.get("name").toString());
                });

        appointBtn.setOnClickListener((view) -> {
            NavDirections action = HomeFragmentDirections.actionNavigationHomeToAppointments();
            Navigation.findNavController(view).navigate(action);
        });


        return root;
    }
}
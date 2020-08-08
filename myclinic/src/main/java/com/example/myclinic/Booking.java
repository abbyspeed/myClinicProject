package com.example.myclinic;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Booking extends AppCompatActivity {
    EditText name, ic_no, address, phone_num, date;
    Button proceed;
    RadioGroup radioGroup;
    RadioButton radioButton;
    User cur_user;

    FirebaseAuth auth;
    FirebaseFirestore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        cur_user = new User(null, null, null, null);

        // Initialized Firebase package
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        // set uid to current login user
        cur_user.setUid(auth.getCurrentUser().getUid());

        // Link components
        name = findViewById(R.id.e_name);
        ic_no = findViewById(R.id.e_icno);
        address = findViewById(R.id.e_address);
        phone_num = findViewById(R.id.e_phoneno);
        proceed = findViewById(R.id.b_display);

        proceed.setOnClickListener((view) -> {
            submitForm();
        });

    }

    protected void submitForm() {
        cur_user.setName(name.getText().toString());
        cur_user.setAddress(address.getText().toString());
        cur_user.setPhoneno(address.getText().toString());

        store.collection("clientinfo").add(cur_user.packet).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }


}



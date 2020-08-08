package com.example.myclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    private EditText userName, userEmail, userPassword, userPhone;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    String email, phone, password, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //INITIALIZE CLOUD FIRESTORE

        setupUIviews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Registration.this,Login.class));

                if(validate())
                {
                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                //sendEMailVerification
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(Registration.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Registration.this, Login.class));
                            }
                            else
                            {
                                Toast.makeText(Registration.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });


                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,Login.class));
            }
        });
    }

    private void setupUIviews() {
        userName = (EditText) findViewById(R.id.etUsername);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin= (TextView)findViewById(R.id.tvUserLogin);
        userPhone = (EditText)findViewById(R.id.etUserPhone);
    }

    private Boolean validate()
    {
        Boolean result = false;

        name = userName.getText().toString();
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        phone = userPhone.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty())
        {
            Toast.makeText(this, "Please Enter All The Details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }

        return result;

    }
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(Registration.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this, Login.class));
                    } else {
                        Toast.makeText(Registration.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        userprofile userprofile = new userprofile(phone, email, name);
        myRef.setValue(userprofile);
    }
}



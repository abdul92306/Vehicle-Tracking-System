package com.example.abdulrahman.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class D_sginup extends AppCompatActivity {

    private Button sginup,login;
    private EditText name,email,vehicle_no,password;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        firebaseAuth.addAuthStateListener(authStateListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_sginup);

        sginup=findViewById(R.id.sginup);
        login=findViewById(R.id.login);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        vehicle_no=findViewById(R.id.vehicle_no);
        password=findViewById(R.id.password);
        firebaseAuth=FirebaseAuth.getInstance();

        sginup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String NAME=name.getText().toString();
                final String EMAIL=email.getText().toString();
                final String VEHICLE_NO=vehicle_no.getText().toString();
                final String PASSWORD=password.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            String user_id=firebaseAuth.getCurrentUser().getUid();
                            database=FirebaseDatabase.getInstance();
                            reference=FirebaseDatabase.getInstance().getReference("users").child("drivers").child(user_id);
                            reference.child("Name").setValue(NAME);
                            reference.child("Vehicle No").setValue(VEHICLE_NO);


                            Toast.makeText(D_sginup.this,"Registered Sucessfully",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(D_sginup.this,D_Dashboard.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(D_sginup.this,"Registeration UnSucessfull",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(D_sginup.this,D_Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Check If user Logged In Already
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    Intent intent=new Intent(D_sginup.this,D_Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
}

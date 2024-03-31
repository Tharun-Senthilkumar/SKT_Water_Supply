package com.example.waterdeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextMobileno;
    EditText editTextEmail;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername=(EditText) findViewById(R.id.editTextUsername);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        editTextMobileno=(EditText) findViewById(R.id.editTextMobile);
        editTextEmail=(EditText) findViewById(R.id.editTextEmailAddress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void signupButtonClicked (View v) {
        String txtUsername = editTextUsername.getText().toString().trim();
        String txtPassword = editTextPassword.getText().toString().trim();
        String txtMobileno = editTextMobileno.getText().toString().trim();
        String txtEmail = editTextEmail.getText().toString().trim();

        if (txtUsername.isEmpty()) {
            editTextUsername.setError("Please enter Username");
            editTextUsername.requestFocus();
            return; // Return if username is empty
        }

        if (txtPassword.isEmpty() || txtPassword.length() < 6) {
            editTextPassword.setError("Please enter Password Containing at least 6 characters");
            editTextPassword.requestFocus();
            return; // Return if password is empty or less than 6 characters
        }

        if (txtMobileno.isEmpty()) {
            editTextMobileno.setError("Please enter Mobile number");
            editTextMobileno.requestFocus();
            return; // Return if mobile number is empty
        }

        if (txtEmail.isEmpty()) {
            editTextEmail.setError("Please enter Email");
            editTextEmail.requestFocus();
            return; // Return if email is empty
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            User user = new User(txtUsername,txtPassword,txtMobileno,txtEmail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(SignUpActivity.this,"User Registered Successfully",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(SignUpActivity.this,DashboardActivity.class));

                                            }
                                            else
                                            {
                                                Toast.makeText(SignUpActivity.this,"User Registration Failed",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this,"User Registration Failed,You are already an user",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
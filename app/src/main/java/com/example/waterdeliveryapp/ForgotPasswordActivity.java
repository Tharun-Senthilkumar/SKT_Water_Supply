package com.example.waterdeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editTextEmail;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editTextEmail=(EditText) findViewById(R.id.editTextFPassEmail);
        progressBar =(ProgressBar) findViewById(R.id.progressBarFPass);
        mAuth = FirebaseAuth.getInstance();
    }

    public void ResetPasswordButtonPressed(View view) {
        resetPassword();
    }
    private void resetPassword()
    {
        String email = editTextEmail.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter a valid Email");
            editTextEmail.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this,"Please check your email to reset Password ",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(ForgotPasswordActivity.this,SignInActivity.class);
                    startActivity(intent);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this,"Failed to reset Password,Try entering Registered email",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
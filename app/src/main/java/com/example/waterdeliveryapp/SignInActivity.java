package com.example.waterdeliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class SignInActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    TextView textViewForgotpass, textViewRegister;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail =(EditText) findViewById(R.id.editTextSigninEmail);
        editTextPassword =(EditText) findViewById(R.id.editTextSigninpass);

        textViewForgotpass =(TextView) findViewById(R.id.textForgotPass);
        textViewRegister =(TextView) findViewById(R.id.textRegister);

        progressBar = (ProgressBar) findViewById(R.id.progressBarSignIn);

        mAuth =FirebaseAuth.getInstance();
    }

    public void txtForgotPasswordClicked(View v)
    {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);

    }
    public void txtRegisterClicked(View v)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

    public void signInButtonClicked(View v)
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter a valid Email");
            editTextEmail.requestFocus();
        }

        if (password.length()<6)
        {
            editTextPassword.setError("Please enter password containing 6 characters");
            editTextPassword.requestFocus();
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignInActivity.this,"User has Signed In successfully",Toast.LENGTH_LONG).show();

                            startActivity(new Intent(SignInActivity.this,DashboardActivity.class));
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignInActivity.this,"User SignIn Failed,Enter valid credentials",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
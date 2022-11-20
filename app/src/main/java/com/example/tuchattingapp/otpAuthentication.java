package com.example.tuchattingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otpAuthentication extends AppCompatActivity {

    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverifyotp;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarofotpauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_authentication);

        mchangenumber=findViewById(R.id.changenumber);
        mverifyotp=findViewById(R.id.verifyotp);
        mgetotp=findViewById(R.id.getotp);
        mprogressbarofotpauth=findViewById(R.id.progressbarofotpAuth);

        firebaseAuth=FirebaseAuth.getInstance();

        mchangenumber.setOnClickListener(view -> {
            Intent intent=new Intent(otpAuthentication.this,MainActivity.class);

            startActivity(intent);
        });

        mverifyotp.setOnClickListener(view -> {
            enteredotp=mgetotp.getText().toString();
            if(enteredotp.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Enter OTP Code",Toast.LENGTH_SHORT).show();
            }
            else

            {
                mprogressbarofotpauth.setVisibility(View.VISIBLE);
                String codereceived=getIntent().getStringExtra("otp");
                PhoneAuthCredential credential= PhoneAuthProvider.getCredential(codereceived,enteredotp);
                signInWithPhoneAuthCredential(credential);

            }
        });


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                mprogressbarofotpauth.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(otpAuthentication.this,setProfile.class);
                startActivity(intent);
                finish();
            }
        });


    }


}
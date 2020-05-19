package com.example.android.panicbutton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,mUserDatabase;
    private FirebaseUser mCurrentUser;
    private EditText fullName;
    private EditText phoneNumber;
    private EditText password;
    private EditText email;
    private Button registerButton;
    private ProgressDialog registrationProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        registrationProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        fullName = (EditText) findViewById(R.id.register_fullname);

        phoneNumber = (EditText) findViewById(R.id.register_phoneNumber);
        password = (EditText) findViewById(R.id.register_password);
        email = (EditText) findViewById(R.id.register_email);
        registerButton = (Button) findViewById(R.id.register_btn);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sFullName = fullName.getText().toString();
                String sPhoneNumber = phoneNumber.getText().toString();
                String sPassword = password.getText().toString();
                String sEmail = email.getText().toString();
                register_user(sFullName,sPhoneNumber,sPassword,sEmail);

                if(!TextUtils.isEmpty(sFullName) || !TextUtils.isEmpty(sPhoneNumber) || !TextUtils.isEmpty(sPassword) || !TextUtils.isEmpty(sEmail) ){
                    registrationProgress.setTitle("Registration User");
                    registrationProgress.setMessage("Please Wait");
                    registrationProgress.setCanceledOnTouchOutside(false);
                    registrationProgress.show();
                }

            }
        });


    }

    private void register_user(final String sFullName, final String sPhoneNumber, String sPassword, String sEmail) {
        mAuth.createUserWithEmailAndPassword(sEmail,sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("full name",sFullName);
                    userMap.put("phone",sPhoneNumber);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                registrationProgress.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });


                }else{
                    registrationProgress.hide();
                    Toast.makeText(RegisterActivity.this, "Cannot sign in. Please check the form and try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
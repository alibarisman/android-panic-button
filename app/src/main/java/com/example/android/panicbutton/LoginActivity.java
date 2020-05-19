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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText lEmail;
    private EditText lPassword;
    private Button lLoginButton;
    private ProgressDialog LoginProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginProgress = new ProgressDialog(this);
        lEmail = (EditText) findViewById(R.id.login_email);
        lPassword = (EditText) findViewById(R.id.login_password);
        lLoginButton = (Button) findViewById(R.id.login_btn);

        lLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = lEmail.getText().toString();
                String password = lPassword.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    LoginProgress.setTitle("Logging in");
                    LoginProgress.setMessage("Please Wait");
                    LoginProgress.setCanceledOnTouchOutside(false);
                    LoginProgress.show();
                    loginUser(email,password);
                }
            }
        });

    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                LoginProgress.dismiss();
                //anaEkranı yaptıktan sonra buraya anaEkran Activity sini ekle.
                Intent anaEkran = new Intent(LoginActivity.this, HomeActivity.class);
                anaEkran.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(anaEkran);
                finish();
//                if(task.isSuccessful()){
//                    LoginProgress.dismiss();
//                    //anaEkranı yaptıktan sonra buraya anaEkran Activity sini ekle.
//                    Intent anaEkran = new Intent(LoginActivity.this, HomeActivity.class);
//                    anaEkran.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(anaEkran);
//                    finish();
//                }else{
//                    LoginProgress.hide();
//                    Toast.makeText(LoginActivity.this, "Cannot sign in. Please check the form and try again", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }
}

package com.example.android.panicbutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //buraya login olmadığı zaman hangi class a, login olduğun da hangi class a gideceğini yazın.
        /*if (currentUser == null){
            Intent notLogin = new Intent(this, StartActivity.class);
            startActivity(notLogin);
            finish();
        }*/
    }

    public void login(View view) {
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login);
    }

    public void register(View view) {
        Intent register = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(register);
    }
}

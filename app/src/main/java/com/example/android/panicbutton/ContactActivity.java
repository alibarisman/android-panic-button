package com.example.android.panicbutton;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {

    Button btnFirstNumber,btnSecondNumber,btnSaveNumber;
    TextView tvFirstNumber,tvSecondNumber;
    private final int PICK_CONTACT = 99;
    private final int PICK_CONTACT2 = 2017;

    private FirebaseAuth mAuth;
    public FirebaseUser mCurrenUser;
    private DatabaseReference mUserDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        btnFirstNumber = (Button) findViewById(R.id.btnfirstContact);
        btnSecondNumber = (Button) findViewById(R.id.btnSecondContact);
        btnSaveNumber = (Button) findViewById(R.id.btnSave);
        tvFirstNumber = (TextView) findViewById(R.id.tvFirstContact);
        tvSecondNumber = (TextView) findViewById(R.id.tvSecondContact);


        btnFirstNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(i, PICK_CONTACT);


            }
        });

        btnSecondNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent z = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(z, PICK_CONTACT2);
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            //int name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            tvFirstNumber.setText(cursor.getString(number));





            //Toast.makeText(getActivity(), "phone number is " + cursor.getString(name), Toast.LENGTH_LONG).show();
        }
        else if (requestCode == PICK_CONTACT2 && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            tvSecondNumber.setText(cursor.getString(column));

            //Toast.makeText(ContactActivity.this, "current" + current_uid, Toast.LENGTH_LONG).show();

            //Toast.makeText(getActivity(), "phone number is " + cursor.getString(column), Toast.LENGTH_LONG).show();
        }
    }

    public void savaData(View view) {



        String tvFN = tvFirstNumber.getText().toString();
        String tvSN = tvSecondNumber.getText().toString();
        //String current_uid = mCurrenUser.getUid();

       // mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

       /* mUserDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue("phone1");


        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("first Number",tvFN);
        userMap.put("second Number", tvSN);

        mUserDatabase.child("first contact").setValue(userMap);*/


       if(FirebaseAuth.getInstance().getCurrentUser() == null){
           Log.e("muhammet", "null çıkıyor burada ...");
       }
       else{
           String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

           mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("contact");

           HashMap<String, String> userMap = new HashMap<>();
           userMap.put("first Number",tvFN);
           userMap.put("second Number", tvSN);

           mUserDatabase.setValue(userMap);

           Log.e("mehmet", "null degil :"+ uid);

       }



    }
}

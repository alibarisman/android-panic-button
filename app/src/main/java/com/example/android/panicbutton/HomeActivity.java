package com.example.android.panicbutton;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    Button getLocationBtn,send;
    TextView locationText, tvLoc;

    LocationManager locationManager;
    private FirebaseAuth mAuth;
    public FirebaseUser mCurrenUser;
    private DatabaseReference mUserDatabase;
    public  String FirstNumber;

String s ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getLocationBtn = (Button)findViewById(R.id.getLocationBtn);
        locationText = (TextView)findViewById(R.id.locationText);
        send = (Button) findViewById(R.id.sendSms);
        //tvLoc = (TextView) findViewById(R.id.tvKonum);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("contact").child("first Number");
                        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                 FirstNumber = dataSnapshot.getValue(String.class);
                                //String second = mUserDatabase.child("second Number").toString();

                                //locationText.setText(FirstNumber);
                                Intent sms = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",FirstNumber,null));



                                String yazı = "hello";
                                sms.putExtra("sms_body"," acil"+locationText.getText());
                                startActivity(sms);


                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


            }

        });

    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0));

        }catch(Exception e)
        {

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(HomeActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     menu.add("Profile");
     menu.add("Contact");
     menu.add("Exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String title = item.getTitle().toString();

        if(title.equals("Exit")){
            finish();
        }
        if(title.equals("Contact")){
            Intent contactIntent = new Intent(HomeActivity.this,ContactActivity.class);
            startActivity(contactIntent);
        }

        if(title.equals("Profile")){
            // Intent ile profil sayfasına gidecek.
            Toast.makeText(HomeActivity.this,"Profile",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}

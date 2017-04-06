package com.example.lenovo.wss;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
ImageButton b1,b2,b3;
    EditText t1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(ImageButton)findViewById(R.id.imageButton6);
        b2=(ImageButton)findViewById(R.id.imageButton7);
        b3=(ImageButton)findViewById(R.id.imageButton9);
        t1=(EditText)findViewById(R.id.editText4);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().detectAll().build();
        StrictMode.setThreadPolicy(policy);
        final MediaPlayer mp=MediaPlayer.create(MainActivity.this,R.raw);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.102:3306/wss","root","root");
                Statement st = con.createStatement();
                String query = "select * from saveddetails where pin='" + t1.getText().toString() + "' ";
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    SmsManager s=SmsManager.getDefault();
                    s.sendTextMessage(rs.getString(2),null,"This is "+rs.getString(1)+"please save me"+"http://maps.google.com/?q="+location.getLatitude()+","+location.getLongitude(),null,null);
                }}
            catch (Exception e)
            {

            }




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                return;
            }
        } else {
            Configure();
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent i=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 10:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Configure();
                return;
        }

    }

    public void Configure()
    {
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps",3000,0,locationListener);

            }
            });}




    }



package com.example.lenovo.wss;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static android.R.attr.name;

public class Main2Activity extends AppCompatActivity {
EditText t1,t2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        t1=(EditText)findViewById(R.id.editText);
        t2=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button2);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().detectAll().build();
        StrictMode.setThreadPolicy(policy);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.102:3306/wss", "root", "root");
                    Statement st = con.createStatement();
                    String query = "insert into saveddetails values('" + t1.getText().toString() + "','" + t2.getText().toString() + "')";

                    st.executeUpdate(query);
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

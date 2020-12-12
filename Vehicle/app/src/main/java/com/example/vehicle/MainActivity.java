package com.example.vehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBClient dbClient;
   TextView vno,vdet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vno =(TextView)findViewById(R.id.vno);
        vdet =(TextView)findViewById(R.id.vdet);
        dbClient = new DBClient(this);
        dbClient.open();
        dbClient.open();
        dbClient.addUser("Vehicleno","details");
        ArrayList<String> users;
        users = dbClient.getuser();
        if(users==null)
        {
        }else{
            vno.setText(users.get(0));
            vdet.setText(users.get(1));
        }
    }
}
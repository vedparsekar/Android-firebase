package com.example.vehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddVehicleinfo extends AppCompatActivity {
    DBClient dbClient;
    EditText vehicalno,vehicalinfo;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicleinfo);
        vehicalno = (EditText)findViewById(R.id.input_vehicle_no);
        vehicalinfo = (EditText)findViewById(R.id.input_vehicleinfo);
        add = (Button) findViewById(R.id.Add);

        dbClient = new DBClient(this);
        dbClient.open();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbClient.addUser(vehicalno.getText().toString(),vehicalinfo.getText().toString());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
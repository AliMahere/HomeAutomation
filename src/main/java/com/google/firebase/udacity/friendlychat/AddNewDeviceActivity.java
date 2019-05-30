package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddNewDeviceActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    Spinner mSpinnerId;
    Button mButtonAdd ;
    EditText mDeviceName;
    Integer mDeviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_device);

        mButtonAdd = (Button) findViewById(R.id.bt_add_new_device);
        mDeviceName = (EditText) findViewById(R.id.et_device_name);
        mSpinnerId = (Spinner) findViewById(R.id.spinner_device_id_number);
        if (mSpinnerId != null) {
            mSpinnerId.setOnItemSelectedListener(this);
        }
        Intent intent = getIntent();
        String room_id= intent.getStringExtra("ROOM_ID");
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("ROOMS")
                .child(room_id).child("Devices");

        //get non avilable id
        final ArrayList<Integer> nonAvialbleDeviceIds =new ArrayList<>();
        ArrayList<Integer> avilableDevieceIds = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot deviceItem:dataSnapshot.getChildren()){
                    Integer deviceId = deviceItem.child("index").getValue(Integer.class);
                    nonAvialbleDeviceIds.add(deviceId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // get available Ids
        for(Integer i =1; i <=8 ;i++){
            if(!nonAvialbleDeviceIds.contains(i)){
               avilableDevieceIds.add(i);
            }
        }

        ArrayAdapter<Integer> spinnerAdapter =new  ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,avilableDevieceIds);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(mSpinnerId !=null){
            mSpinnerId.setAdapter(spinnerAdapter);
        }



        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = mDeviceName.getText().toString();
                Device device= new Device(deviceName, mDeviceId,0,false);
                myRef.push().setValue(device);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mDeviceId = (Integer) parent.getItemAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}

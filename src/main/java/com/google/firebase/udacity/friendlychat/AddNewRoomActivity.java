package com.google.firebase.udacity.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewRoomActivity extends AppCompatActivity {
    EditText mRoomName;
    EditText mRoomIp;
    EditText mRoomId;
    Button mButtonAdd;

    DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference().child("ROOMS");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_room);
        mRoomName = (EditText) findViewById(R.id.et_room_name);
        mRoomIp = (EditText) findViewById(R.id.et_room_ip_address);
        mRoomId = (EditText) findViewById(R.id.et_room_id);
        mButtonAdd =(Button) findViewById(R.id.bt_add_new_room);

        mRoomIp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(hasFocus || isValidIP(mRoomIp.getText().toString()))) {
                    Toast.makeText(AddNewRoomActivity.this, "not Valid Ip address ", Toast.LENGTH_SHORT).show();
                    mButtonAdd.setEnabled(false);
                }
                else{
                    mButtonAdd.setEnabled(true);
                }
            }
        });

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRoomId=mRoomId.getText().toString();
                roomsReference.child(strRoomId).setValue(new Room(mRoomName.getText().toString(),mRoomIp.getText().toString(),strRoomId));
                finish();
            }
        });


    }
    public static boolean isValidIP(String ipAddr){

        Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ipAddr);
        return mtch.find();
    }


}

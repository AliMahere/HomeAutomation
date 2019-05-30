package com.google.firebase.udacity.friendlychat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomActiviy extends AppCompatActivity {
    String roomIpAddress;
    String roomId;
    String roomName;
    FloatingActionButton fab;
    DevicesAdapterLocal localAdapter;
    DevicesAapterCloud cloudadApter;
    DatabaseReference roomDevices;
    ArrayList<Device> devices;
    ArrayList<String> listKeys;
    ListView listView;
    private static final int LOCAL_DATA_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        fab = (FloatingActionButton) findViewById(R.id.fab_add_device);
        Intent intent = getIntent();
        roomIpAddress = intent.getStringExtra("ROOM_IP_Address");
        roomId = intent.getStringExtra("ROOM_ID");
        roomName = intent.getStringExtra("ROOM_NAME");
        // Room ID equals room database path
        roomDevices = FirebaseDatabase.getInstance().getReference().child("ROOMS")
                .child(roomId).child("Devices");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RoomActiviy.this, AddNewDeviceActivity.class);
                intent1.putExtra("ROOM_ID", roomId);
                startActivity(intent1);
            }
        });
        devices = new ArrayList<>();
        listKeys = new ArrayList<>();

        listView = findViewById(R.id.listview_devices);
        //


        if (RoomsFragment.roomData.length() != 0) {
            //   Toast.makeText(this, "the data is  " +RoomsFragment.roomData.toString(), Toast.LENGTH_SHORT).show();
            localAdapter = new
                    DevicesAdapterLocal(this, devices, roomIpAddress, RoomsFragment.roomData.toString());
            listView.setAdapter(localAdapter);
        } else {
            //  Toast.makeText(this, "local connection cant happen ", Toast.LENGTH_SHORT).show();

            cloudadApter = new DevicesAapterCloud(this, devices, roomId);
            listView.setAdapter(cloudadApter);
        }


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomActiviy.this);
                builder.setTitle("Remove Device");
                builder.setMessage("Are you sure u want to remove this device ?");

                // add the buttons
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roomDevices.child(listKeys.get(position)).setValue(null);
                        devices.remove(position);
                        listKeys.remove(position);

                        if (localAdapter != null) {
                            localAdapter.notifyDataSetChanged();
                        } else if (cloudadApter != null) {
                            cloudadApter.notifyDataSetChanged();
                        }

                        Toast.makeText(RoomActiviy.this, "Device Removed ", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton("Cancel", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(RoomActiviy.this, R.color.colorPrimary));
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(RoomActiviy.this, R.color.colorPrimary));

                return true;
            }
        });


        roomDevices.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                devices.clear();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                devices.clear();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                devices.clear();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        roomDevices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot deviceItem : dataSnapshot.getChildren()) {
                    Device device = deviceItem.getValue(Device.class);
                    devices.add(device);
                    listKeys.add(deviceItem.getKey());

                    if (localAdapter != null) {
                        localAdapter.notifyDataSetChanged();
                    } else if (cloudadApter != null) {
                        cloudadApter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}






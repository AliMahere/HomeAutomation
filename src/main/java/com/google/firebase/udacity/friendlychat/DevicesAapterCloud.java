package com.google.firebase.udacity.friendlychat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DevicesAapterCloud extends ArrayAdapter<Device> {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference();

    String roomId ;
    public DevicesAapterCloud(Context context, ArrayList<Device> objects, String roomId) {
        super(context, 0, objects);
        this.roomId = roomId;
    }

    StringBuilder strData ;

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        final DatabaseReference data = myRef.child("ROOMS")
                .child(roomId).child("data");
        View deviceView = convertView;
        if(deviceView == null){
            deviceView = LayoutInflater.from(getContext()).inflate(R.layout.decive_item,parent,false);
        }
        //get currnt device
        final Device currentDevice = getItem(position);
        final Switch deviceSwitch = (Switch) deviceView.findViewById(R.id.device_switch);
        //set Device name text Veiw valu
        TextView deviceName = (TextView) deviceView.findViewById(R.id.device_name_text_view);
        deviceName.setText(currentDevice.getName());

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.getValue(String.class) != null) {
                    strData = new StringBuilder(dataSnapshot.getValue(String.class));

                    if (strData.charAt(currentDevice.getIndex()) == '1')
                        deviceSwitch.setChecked(true);
                    else
                        deviceSwitch.setChecked(false);
                    Log.d("file", "Value is: " + strData);

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("file", "Failed to read value.", error.toException());
            }
        });

        // set Device Switch
        deviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    strData.setCharAt(currentDevice.getIndex(),'1');
                    data.setValue(strData.toString());
                    currentDevice.setStatus(true);
                }else {
                    strData.setCharAt(currentDevice.getIndex(),'0');
                    data.setValue(strData.toString());
                    currentDevice.setStatus(false);

                }
            }
        });



        return deviceView;


    }

}

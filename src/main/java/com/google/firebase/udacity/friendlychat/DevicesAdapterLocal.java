package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class DevicesAdapterLocal extends ArrayAdapter<Device> {

    final String LOG_TAG= "DevicesAdapterLocal";
    String roomIp;
    StringBuilder localStatus;
    String current;
    public DevicesAdapterLocal(Context context, ArrayList<Device> objects, String roomIP,String current ) {
        super(context, 0, objects);
        this.roomIp= roomIP;
        this.current = current;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        //get initial status


        Log.d(LOG_TAG, "Value is: " + localStatus);
        if(current.charAt(currentDevice.getIndex())== '1')
            deviceSwitch.setChecked(true);
        else
            deviceSwitch.setChecked(false);

        Log.d(LOG_TAG, "Value is: " + localStatus);
        localStatus= new StringBuilder(current);
        deviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    localStatus.setCharAt(currentDevice.getIndex(),'1');
                   setStaus(roomIp, localStatus.toString());
                    currentDevice.setStatus(true);
                }else {
                    localStatus.setCharAt(currentDevice.getIndex(),'0');
                    setStaus(roomIp, localStatus.toString());
                    currentDevice.setStatus(false);

                }
            }
        });



        return deviceView;
    }


    public void setStaus(String roomIp , String newData){
        String runCommand = "http://"+roomIp+"/set/"+newData;
        new Asynutiles().execute(runCommand);
    }
}

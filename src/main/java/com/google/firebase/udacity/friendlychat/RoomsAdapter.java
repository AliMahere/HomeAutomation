package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomsAdapter extends ArrayAdapter<Room> {
    public RoomsAdapter( Context context, ArrayList<Room> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View roomView = convertView;
        if(roomView == null){
            roomView = LayoutInflater.from(getContext()).inflate(R.layout.room_item, parent,false);
        }
        final Room current = getItem(position);

        TextView roomName = roomView.findViewById(R.id.tv_item_room_name);
        roomName.setText(current.getRoomName());

        TextView roomIpAdress = roomView.findViewById(R.id.tv_item_room_ip_address);
        roomIpAdress.setText(current.getRoomIpAddress());

        return roomView;
    }
}

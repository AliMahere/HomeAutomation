package com.google.firebase.udacity.friendlychat;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomsFragment extends Fragment {
    FloatingActionButton fab;
    DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference().child("ROOMS");
    static StringBuilder roomData = new StringBuilder("");
    Asynutiles tasl = new Asynutiles(roomData);



    public RoomsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);
        fab = rootView.findViewById(R.id.fab_add_room);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewRoomActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<Room> rooms = new ArrayList<>();
        final ArrayList<String> roomkeys = new ArrayList<>();
        ListView listView = rootView.findViewById(R.id.listview_rooms);
        final RoomsAdapter adapter = new RoomsAdapter(getActivity(), rooms);
        listView.setAdapter(adapter);

        roomsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("dsf", "onChildAdded");
                rooms.clear();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                rooms.clear();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                rooms.clear();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        roomsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot roomItem : dataSnapshot.getChildren()) {
                    Room room = roomItem.getValue(Room.class);
                    rooms.add(room);
                    roomkeys.add(roomItem.getKey());
                    adapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room current = rooms.get(position);

                Intent intent = new Intent(getContext(), RoomActiviy.class);
                intent.putExtra("ROOM_NAME", current.getRoomName());
                intent.putExtra("ROOM_LOCAL_DATA", current.getRoomName());
                intent.putExtra("ROOM_IP_Address", current.getRoomIpAddress());
                intent.putExtra("ROOM_ID", current.getRoomId());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Remove room");
                builder.setMessage("Are you sure u want to remove this room ?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roomsReference.child(roomkeys.get(position)).removeValue();
                        roomkeys.remove(position);
                        rooms.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Device Removed ", Toast.LENGTH_LONG).show();
                    }

                });
                builder.setNegativeButton("Cancel",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

                return true;
            }
        });

        return rootView;
    }

}

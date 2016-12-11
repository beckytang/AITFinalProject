package com.example.beckytang.finalproject.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumList {

    private static List<Album> albumList = new ArrayList<>();

    public static List<Album> getList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("albums");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                albumList.add(dataSnapshot.child("AIT").getValue(Album.class));
                albumList.add(dataSnapshot.child("Metro").getValue(Album.class));
                albumList.add(dataSnapshot.child("Budapest").getValue(Album.class));
                albumList.add(dataSnapshot.child("Hungary").getValue(Album.class));
                albumList.add(dataSnapshot.child("Greenwich").getValue(Album.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return albumList;
    }
}

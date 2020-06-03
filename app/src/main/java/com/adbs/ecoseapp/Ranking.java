package com.adbs.ecoseapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ranking extends Fragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextView rankingName, rankingActions;
    FirebaseDatabase firebaseDatabase;
    String uid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference("Users");
        rankingName = view.findViewById(R.id.ranking_user);
        rankingActions = view.findViewById(R.id.ranking_user_actions);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameOfPerson = dataSnapshot.child(uid).child("name").getValue().toString();
                String actionsOfPerson = dataSnapshot.child(uid).child("actions").getValue().toString();
                rankingName.setText(nameOfPerson);
                rankingActions.setText(actionsOfPerson);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}

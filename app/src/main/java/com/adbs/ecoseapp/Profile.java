package com.adbs.ecoseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;
    TextView name,actions,points;
    ImageView logout;
    String uid;

    private Menu menu;

    public Profile(Menu menu) {
        this.menu = menu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseAuth.getUid();
        users = firebaseDatabase.getInstance().getReference("Users");
        name = view.findViewById(R.id.profile_name);
        actions = view.findViewById(R.id.profile_actions);
        points = view.findViewById(R.id.profile_points);
        logout = view.findViewById(R.id.profile_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this.menu,Main.class));
            }
        });

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameOfPerson = dataSnapshot.child(uid).child("name").getValue().toString();
                String pointsOfPerson = dataSnapshot.child(uid).child("points").getValue().toString();
                String actionsOfPerson = dataSnapshot.child(uid).child("actions").getValue().toString();

                name.setText(nameOfPerson);
                points.setText(pointsOfPerson);
                actions.setText(actionsOfPerson);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}

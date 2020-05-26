package com.adbs.ecoseapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends Fragment {

    private CardView actions,rewards,ods,map;
    private BottomNavigationView bottom;
    private Menu menu;

    public CardView getActions() {
        return actions;
    }

    public void setActions(CardView actions) {
        this.actions = actions;
    }

    public CardView getRewards() {
        return rewards;
    }

    public void setRewards(CardView rewards) {
        this.rewards = rewards;
    }

    public CardView getOds() {
        return ods;
    }

    public void setOds(CardView ods) {
        this.ods = ods;
    }

    public CardView getMap() {
        return map;
    }

    public void setMap(CardView map) {
        this.map = map;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public BottomNavigationView getBottom() {
        return bottom;
    }

    public void setBottom(BottomNavigationView bottom) {
        this.bottom = bottom;
    }

    public Home(Menu menu) {
        this.menu = menu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        actions = view.findViewById(R.id.actions);

        actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(Home.this.menu,Actions.class));

            }
        });

        rewards = view.findViewById(R.id.rewards);

        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this.menu,Rewards.class));
            }
        });

        map = view.findViewById(R.id.maps);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this.menu,MapsLocations.class));
            }
        });

        ods = view.findViewById(R.id.ods);

        ods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this.menu,Ods.class));
            }
        });



        return view;
    }
}

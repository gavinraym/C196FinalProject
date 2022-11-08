package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createNewTerm(Bundle bundle) {
        Log.d("MainActivity","Create new term");
        DBManager.getInstance(this).addTerm(bundle);
        Log.d("MainActivity","Create new term finished");

    }

    public void receiveNewTerm(Bundle bundle) {
        Log.d("MainActivity", "Receive new term.");
        Log.d("MainActivity", "Received term id is :" + bundle.getString("id"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.snapshotLinearLayout, SnapshotFragment.class, bundle)
                .commit();
        Log.d("MainActivity", "Receive new term finished.");


    }


    public void deleteTerm(View view) {
        Log.d("MainActivity","Delete term");


    }

    public void detailTerm(View view) {
        Log.d("MainActivity","Detail term");
    }




}
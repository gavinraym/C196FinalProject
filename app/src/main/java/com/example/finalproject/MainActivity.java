package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static HashMap<Long, TermFragment> fragments = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager.getInstance(this).setMainActivity(this);
        if (savedInstanceState == null) {
            DBManager.getInstance(this).getAllTerms();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Get all terms and display snapshots

    }

    public void createNewTerm(Bundle bundle) {
        Log.d("MainActivity","Create new term");
        DBManager.getInstance(this).addTerm(bundle);
        Log.d("MainActivity","Create new term finished");
    }

    public void receiveNewTerm(Bundle bundle) {
        Log.d("MainActivity", "Receive new term.");
        String tag = "term_snapshot_" + String.valueOf(bundle.getLong("id"));

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.snapshotLinearLayout, SnapshotFragment.class, bundle, tag)
                .commit();
        Log.d("MainActivity", "Receive new term finished.");

    }

    public void deleteTerm(Long id) {
        Log.d("MainActivity","Delete term");
        DBManager.getInstance(this).deleteTerm(id);
    }

    public void confirmDelete(String id) {
        String tag = "term_snapshot_" + id;
        ((SnapshotFragment) getSupportFragmentManager().findFragmentByTag(tag)).deleteSelf();
    }

    public void detailTerm(View view) {
        Log.d("MainActivity","Detail term");
    }




}
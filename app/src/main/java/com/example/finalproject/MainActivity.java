package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static HashMap<Long, TermFragment> fragments = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBManager.getInstance(this).setMainActivity(this);

        CharSequence name = "Main Channel";
        String description = "One channel to rule them all.";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("Main Channel", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LinearLayout) findViewById(R.id.snapshotLinearLayout)).removeAllViews();
        DBManager.getInstance(this).getAllTerms();
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

    public void showTermDetails(Long id) {
        Intent detailIntent = new Intent(this, TermActivity.class);
        detailIntent.putExtra("com.example.finalproject.term_id",id);
        Log.d("MainActivity","Show term details, id = " + String.valueOf(id));
        startActivity(detailIntent);
    }

    public void alertNoTermDelete() {
        Log.d("MainActivity","Alert no term delete.");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This term still contains courses, and therefore " +
                "cannot be deleted. Please delete all courses and try again.");
        builder.create().show();
    }
}
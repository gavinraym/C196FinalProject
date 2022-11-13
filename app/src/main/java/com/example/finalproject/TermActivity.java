package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finalproject.databinding.ActivityTermBinding;

public class TermActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTermBinding binding;
    private Long termId;
    private TermDetailFragment termDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TermActivity","On create.");
        binding = ActivityTermBinding.inflate(getLayoutInflater());
        Log.d("TermActivity","Layout inflated.");
        setContentView(binding.getRoot());
        Log.d("TermActivity","Content view set.");
        DBManager.getInstance(this).setTermActivity(this);

        Bundle extras = getIntent().getExtras();
        this.termId = extras.getLong("com.example.finalproject.term_id");
        if (savedInstanceState == null) {
            DBManager.getInstance(this).getTermData(this.termId);
            DBManager.getInstance(this).getAllCoursesData(this.termId);
        }
        Log.d("TermActivity","On create finished.");
    }



    public void setTermDetailFragment(TermDetailFragment fragment) {
        this.termDetailFragment = fragment;
        Log.d("TermActivity","Set term detail fragment finished.");
    }

    public void requestTermData() {
        Log.d("TermActivity","Request term data.");
        // Put in database call for term data here
        Bundle bundle = new Bundle();
        bundle.putString("title","updated title");
        bundle.putString("start","2022-12-23");
        bundle.putString("end","2022-12-24");
        receiveTermData(bundle);
    }

    public void receiveTermData(Bundle bundle) {
        Log.d("TermActivity","Receive term data.");
        termDetailFragment.updateTermData(bundle);
    }

    public void saveTermData(Bundle bundle) {
        Log.d("TermActivity","Save term data.");
        bundle.putString("id",String.valueOf(this.termId));
        DBManager.getInstance(this).updateTermData(bundle);
    }

    public void verifySave(int affectedRows) {
        Log.d("TermActivity","Verifying save.");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (affectedRows == 1) {
            Log.d("TermActivity", "Term data saved successfully.");
            builder.setMessage("Term data saved successfully");
        }
        else {
            Log.d("TermActivity", "Term data did not save.");
            builder.setMessage("Ut oh! Term data was not saved." +
                    " Please ensure all entries are correct. Dates should use" +
                    " this format: yyyy-dd-mm (2022-01-07)");
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void backButtonClick(View view) {
        Log.d("TermActivity","Back button click.");
        Intent detailIntent = new Intent(this,MainActivity.class);
        startActivity(detailIntent);
    }

    public void requestAddCourseToTerm() {
        Log.d("TermActivity","Request add course to term");
        DBManager.getInstance(this).addCourseToTerm(this.termId);
    }

    public void receiveNewCourse(Bundle bundle) {
        Log.d("TermActivity", "Receive new course. id:" + bundle.getLong("id"));
        String tag = "course_snapshot_" + String.valueOf(bundle.getLong("id"));

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.snapshotLinearLayout, CourseSnapshotFragment.class, bundle, tag)
                .commit();
        Log.d("MainActivity", "Receive new term finished.");
    }

    public void deleteCourse(Long id) {
        Log.d("TermActivity","Delete course.");
        DBManager.getInstance(this).deleteCourse(id);
    }

    public void confirmDelete(String id) {
        String tag = "course_snapshot_" + id;
        ((CourseSnapshotFragment) getSupportFragmentManager().findFragmentByTag(tag))
                .deleteSelf();
    }

    public void showCourseDetails(Long id) {
        Intent detailIntent = new Intent(this, CourseActivity.class);
        detailIntent.putExtra("com.example.finalproject.course_id",id);
        Log.d("TermActivity","Show Course details, id = " + String.valueOf(id));
        startActivity(detailIntent);
    }
}
package com.example.finalproject;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finalproject.databinding.ActivityCourseBinding;

public class CourseActivity extends AppCompatActivity {

    private ActivityCourseBinding binding;
    private Long courseId;
    private CourseDetailFragment courseDetailFragment;
    private CourseAdditionsFragment courseAdditionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CourseActivity","On create.");
        binding = ActivityCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DBManager.getInstance(this).setCourseActivity(this);
        Bundle extras = getIntent().getExtras();
        this.courseId = extras.getLong("com.example.finalproject.course_id");
        if (savedInstanceState == null) {
            DBManager.getInstance(this).getCourseData(this.courseId);
//            DBManager.getInstance(this).getAllAssessmentsData(this.courseId);
//            DBManager.getInstance(this).getAllNotesData(this.courseId);
        }
    }

    public void setCourseDetailFragment(CourseDetailFragment fragment) {
        this.courseDetailFragment = fragment;
    }

    public void setCourseAdditionsFragment(CourseAdditionsFragment fragment) {
        this.courseAdditionsFragment = fragment;
    }

    public void receiveCourseDataForDB(Bundle bundle) {
        Log.d("CourseActivity","Receive course for Db.");
        bundle.putLong("id", this.courseId);
        DBManager.getInstance(this).updateCourseData(bundle);
    }

    public void updateCourseDetailFragment(Bundle bundle) {
        Log.d("CourseActivity", "Update course detail fragment");
        courseDetailFragment.refreshCourseData(bundle);
    }
}
package com.example.finalproject;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.finalproject.databinding.ActivityCourseBinding;

public class CourseActivity extends AppCompatActivity {

    private ActivityCourseBinding binding;
    private Long courseId;
    private CourseDetailFragment courseDetailFragment;
    private AssessmnentFragment assessmnentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CourseActivity","On create.");
        binding = ActivityCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DBManager.getInstance(this).setCourseActivity(this);
        Bundle extras = getIntent().getExtras();
        this.courseId = extras.getLong("com.example.finalproject.course_id");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        DBManager.getInstance(this).getCourseData(this.courseId);
        DBManager.getInstance(this).getAllAssessmentsData(this.courseId);
        DBManager.getInstance(this).getAllNotesData(this.courseId);
        Log.d("CourseActivity","On create finished,");
    }


    public void setCourseDetailFragment(CourseDetailFragment fragment) {
        this.courseDetailFragment = fragment;
    }

    public void receiveCourseDataForDB(Bundle bundle) {
        Log.d("CourseActivity","Receive course for Db.");
        bundle.putLong("id", this.courseId);
        DBManager.getInstance(this).updateCourseData(bundle);
    }

    public void updateCourseDetailFragment(Bundle bundle) {
        Log.d("CourseActivity", "Update course detail fragment.");
        courseDetailFragment.refreshCourseData(bundle);
    }

    public void courseDataSaveReturnResults(Integer affectedRows) {
        Log.d("CourseActivity","Course data save return results.");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (affectedRows == 1) {
            Log.d("CourseActivity", "Course data saved successfully.");
            builder.setMessage("Course data saved successfully");
        }
        else {
            Log.d("CourseActivity", "Course data did not save.");
            builder.setMessage("Ut oh! Course data was not saved." +
                    " Please ensure all entries are correct. Dates can use" +
                    " this format: yyyy-dd-mm (2022-01-07)");
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createNewNote() {
        Log.d("CourseActivity", "Create new Note.");
        Bundle bundle = new Bundle();
        bundle.putLong("courseId", this.courseId);
        DBManager.getInstance(this).createNewNote(bundle);
    }

    public void receiveNewNoteData(Bundle bundle) {
        Log.d("CourseActivity","Recieve note data.");
        String tag = "note_snapshot_" + String.valueOf(bundle.getLong("id"));
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentNoteView, NoteFragment.class, bundle, tag)
                .commit();
        Log.d("MainActivity", "Receive new term finished.");
    }

    public void receiveNoteDataForDB(Bundle bundle) {
        Log.d("CourseActivity","Receive note save data for db.");
        bundle.putLong("courseId",this.courseId);
        DBManager.getInstance(this).updateNote(bundle);
    }

    public void deleteNote(long id) {
        DBManager.getInstance(this).deleteNote(id);
    }

    public void confirmNoteDelete(String id) {
        Log.d("CourseActivity","Confirm note delete.");
        String tag = "note_snapshot_" + id;
        Log.d("CourseActivity","Tag = "+ tag);
        ((NoteFragment) getSupportFragmentManager()
                .findFragmentByTag(tag))
                .deleteSelf();
    }

    public void createNewAssmnt() {
        Log.d("CourseActivity", "Create new Assessment.");
        Bundle bundle = new Bundle();
        bundle.putLong("courseId", this.courseId);
        DBManager.getInstance(this).createNewAssessment(bundle);
    }

    public void receiveNewAssmntData(Bundle bundle) {
        Log.d("CourseActivity","Recieve assment data.");
        String tag = "assessment_snapshot_" + String.valueOf(bundle.getLong("id"));
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentAssmntView, AssessmnentFragment.class, bundle, tag)
                .commit();
        Log.d("MainActivity", "Receive new term finished.");
    }

    public void receiveAssmentDataForDB(Bundle bundle) {
        Log.d("CourseActivity","Receive assessment save data for db.");
        bundle.putLong("courseId",this.courseId);
        DBManager.getInstance(this).updateAssessment(bundle);
    }

    public void deleteAssmnt(long id) {
        DBManager.getInstance(this).deleteAssessment(id);
    }

    public void confirmAssmntDelete(String id) {
        Log.d("CourseActivity","Confurm assessmnent delete.");
        String tag = "assessment_snapshot_" + id;
        Log.d("CourseActivity","Tag = "+ tag);
        ((AssessmnentFragment) getSupportFragmentManager()
                .findFragmentByTag(tag))
                .deleteSelf();
    }
}
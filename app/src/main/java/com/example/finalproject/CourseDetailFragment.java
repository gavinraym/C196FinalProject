package com.example.finalproject;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.finalproject.databinding.FragmentCourseDetailBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class CourseDetailFragment extends Fragment {

    private FragmentCourseDetailBinding binding;

    private EditText titleEditText;
    private EditText startEditText;
    private EditText endEditText;
    private Spinner statusSpinner;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCourseDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CourseActivity)getActivity()).setCourseDetailFragment(this);
        titleEditText = view.findViewById(R.id.editTitleText);
        startEditText = view.findViewById(R.id.editStartText);
        endEditText = view.findViewById(R.id.editEndText);
        statusSpinner = view.findViewById(R.id.editStatusSpinner);
        nameEditText = view.findViewById(R.id.editNameText);
        phoneEditText = view.findViewById(R.id.editPhoneText);
        emailEditText = view.findViewById(R.id.editEmailText);
        binding.saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { saveCourseButtonClick(view); }
        });
        binding.addAssmntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CourseActivity)getActivity()).createNewAssmnt();
            }
        });
        binding.addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CourseActivity)getActivity()).createNewNote();
            }
        });
        binding.courseAlertButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlert();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void saveCourseButtonClick(View view) {
        Log.d("CourseDetailFragment","Save course button click.");
        Bundle bundle = new Bundle();
        bundle.putString("title", titleEditText.getText().toString());
        bundle.putString("start",startEditText.getText().toString());
        bundle.putString("end",endEditText.getText().toString());
        bundle.putInt("status",statusSpinner.getSelectedItemPosition());
        bundle.putString("name",nameEditText.getText().toString());
        bundle.putString("phone",phoneEditText.getText().toString());
        bundle.putString("email",emailEditText.getText().toString());
        ((CourseActivity)getActivity()).receiveCourseDataForDB(bundle);
    }

    public void refreshCourseData(Bundle bundle) {
        Log.d("CourseDetailFragment","Refresh course data:" + bundle.getString("title"));
        titleEditText.setText(bundle.getString("title"));
        startEditText.setText(bundle.getString("start"));
        endEditText.setText(bundle.getString("end"));
        try {
            statusSpinner.setSelection(bundle.getInt("status"));
        } catch (Exception e) {
            Log.d("CourseDetailFragment","Refresh course data exception caught.");
            statusSpinner.setSelection(0);
        }
        nameEditText.setText(bundle.getString("name"));
        phoneEditText.setText(bundle.getString("phone"));
        emailEditText.setText(bundle.getString("email"));
    }

    private boolean sendAlertRequest(String dateString, String title, String message) {
        Long notifyAt = 0L;
        try {
            LocalDate date = LocalDate.parse(dateString);
            LocalTime time = LocalTime.now().plusMinutes(2);
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            ZonedDateTime zoneDateTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
            notifyAt = zoneDateTime.toInstant().toEpochMilli();
            Log.d("AssessmentFragment","Alert button click.");
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("There was an issue with the date. Please format " +
                    "as yyyy-mm-dd");
            builder.create().show();
            return false;
        }

        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("message",message);
        bundle.putLong("notifyAt",notifyAt);

        DBManager.getInstance(getContext()).scheduleNotification(bundle);
        return true;
    }

    public void setAlert() {
        Log.d("CourseDetailFragment", "Set alert.");
        if (sendAlertRequest(
                binding.editStartText.getText().toString(),
                "Your course is starting:",
                binding.editTitleText.getText().toString()
        ) &
        sendAlertRequest(
                binding.editEndText.getText().toString(),
                "Your course is ending:",
                binding.editTitleText.getText().toString()
        )) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Your alerts were scheduled successfully.");
            builder.create().show();
        }



    }


}
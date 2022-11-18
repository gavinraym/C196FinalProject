package com.example.finalproject;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.finalproject.databinding.FragmentAssessmnentBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssessmnentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssessmnentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "type";
    private static final String ARG_PARAM3 = "title";
    private static final String ARG_PARAM4 = "date";

    // TODO: Rename and change types of parameters
    private @NonNull FragmentAssessmnentBinding binding;
    private long id;
    private Integer type;
    private String title;
    private String date;
    private AssessmnentFragment selfRef;

    public AssessmnentFragment() {
        // Required empty public constructor
    }

    public static AssessmnentFragment newInstance(Long id,
                  Integer type, String title, String date) {
        AssessmnentFragment fragment = new AssessmnentFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, id);
        args.putInt(ARG_PARAM2, type);
        args.putString(ARG_PARAM3, title);
        args.putString(ARG_PARAM4, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.selfRef = this;
        if (getArguments() != null) {
            this.id = getArguments().getLong(ARG_PARAM1);
            this.type = getArguments().getInt(ARG_PARAM2);
            this.title = getArguments().getString(ARG_PARAM3);
            this.date = getArguments().getString(ARG_PARAM4);
        }
        Log.d("AssessmentFragment","On create, " + String.valueOf(this.id) + this.title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAssessmnentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("AssessmentFragment","On view create:" + this.title);
        binding.assmntTypeSpinner.setSelection(this.type);
        binding.editAssmntTitle.setText(this.title);
        binding.editAssmntDate.setText(this.date);
        binding.saveAssmntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {saveButtonClick(view); }
        });
        binding.assmntAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {alertButtonClick(view); }
        });
        binding.assmntDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {deleteButtonClick(view); }
        });
    }

    public void saveButtonClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong("id",this.id);
        bundle.putInt("type",binding.assmntTypeSpinner.getSelectedItemPosition());
        bundle.putString("title",binding.editAssmntTitle.getText().toString());
        bundle.putString("end",binding.editAssmntDate.getText().toString());
        Log.d("AssmntFragment", "Save button click.");
        DBManager.getInstance(getActivity()).updateAssessment(bundle);
    }

    public void alertButtonClick(View view) {
        Log.d("AssmntFragment", "Alert button click.");
        Long notifyAt = 0L;
        try {
            LocalDate date = LocalDate.parse(binding.editAssmntDate.getText().toString());
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
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("title","An assignment is due:");
        bundle.putString("message",binding.editAssmntTitle.getText().toString());
        bundle.putLong("notifyAt",notifyAt);

        DBManager.getInstance(getContext()).scheduleNotification(bundle);
    }

    public void deleteButtonClick(View view) {
        Log.d("AssmntFragment", "Delete button click.");
        ((CourseActivity)getActivity()).deleteAssmnt(this.id);
    }


    public void deleteSelf() {
        Log.d("AssessmentFragment","Delete self.");
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(this.selfRef)
                .commit();
    }



}
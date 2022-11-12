package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.finalproject.databinding.FragmentTermBinding;
import com.example.finalproject.databinding.FragmentTermDetailBinding;

public class TermDetailFragment extends Fragment {

    private Long termId;
    private FragmentTermDetailBinding binding;
    private EditText titleEditText;
    private EditText startEditText;
    private EditText endEditText;
    private Button createCourseButton;
    private Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_term_detail, container, false);
      return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TermActivity)getActivity()).setTermDetailFragment(this);
        titleEditText = view.findViewById(R.id.editTextTermTitle);
        startEditText = view.findViewById(R.id.editTextTermStart);
        endEditText = view.findViewById(R.id.editTextTermEnd);
        saveButton = view.findViewById(R.id.saveButton);
        createCourseButton = view.findViewById(R.id.createCourseButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTermData();
            }
        });
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createCourseButtonClick();
            }
        });
        binding = FragmentTermDetailBinding.inflate(getLayoutInflater());
        ((TermActivity)getActivity()).requestTermData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateTermData(Bundle bundle) {
        titleEditText.setText(bundle.getString("title"));
        startEditText.setText(bundle.getString("start"));
        endEditText.setText(bundle.getString("end"));

    }

    public void saveTermData() {
        Log.d("TermDetailFragment", "Save term data.");
        Bundle termData = new Bundle();
        termData.putString("title",titleEditText.getText().toString());
        termData.putString("start",startEditText.getText().toString());
        termData.putString("end",endEditText.getText().toString());
        ((TermActivity)getActivity()).saveTermData(termData);
    }

    public void createCourseButtonClick() {
        Log.d("TermDetailFragment", "Create course.");
        ((TermActivity)getActivity()).requestAddCourseToTerm();
    }

}
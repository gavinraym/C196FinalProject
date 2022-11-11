package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.databinding.FragmentTermBinding;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TermFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TermFragment extends Fragment {

    private FragmentTermBinding binding;

    //UI elements
    private EditText titleEditText;
    private EditText startEditText;
    private EditText endEditText;
    private Button createTermButton;

    public TermFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TermFragment newInstance() {
        TermFragment fragment = new TermFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_term, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleEditText = view.findViewById(R.id.editTextTermTitle);
        createTermButton = view.findViewById(R.id.createTermButton);
        createTermButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createTermButtonClick();
            }
        });
        binding = FragmentTermBinding.inflate(getLayoutInflater());
    }

    public void createTermButtonClick() {
        Log.d("Term Fragment Activity", "Create term button clicked.");
        Bundle bundle = new Bundle();
        bundle.putString("title", titleEditText.getText().toString());
        ((MainActivity)getActivity()).createNewTerm(bundle);
        Log.d("Term Fragment Activity", "Create term Finished.");
    }
}
package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.finalproject.databinding.FragmentCourseSnapshotBinding;
import com.example.finalproject.databinding.FragmentTermDetailBinding;

public class CourseSnapshotFragment extends Fragment {

    private FragmentCourseSnapshotBinding binding;
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "title";

    // TODO: Rename and change types of parameters
    public Long id;
    private String title;
    private Fragment selfRef;

    public CourseSnapshotFragment() {
        // Required empty public constructor
    }

    public static  CourseSnapshotFragment newInstance(String id, String title) {
        CourseSnapshotFragment fragment = new CourseSnapshotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selfRef = this;
        Log.d("Course Snapshot Fragment","On create.");
        if (getArguments() != null) {
            this.id = getArguments().getLong(ARG_PARAM1);
            Log.d("SnapshotFragment","On Create: id = " + String.valueOf(this.id));
            this.title = getArguments().getString(ARG_PARAM2);
        }
        Log.d("Course Snapshot Fragment","Args set.");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCourseSnapshotBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button itemButton = view.findViewById(R.id.ItemButton);
        itemButton.setText(this.title);

        itemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                detailRedirect(); }; } );

        // deleteButton set onclick
        Button deleteButton = view.findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteButtonClick(v);
            }; } );

    }


    public void deleteButtonClick(View v) {
        ((TermActivity)getActivity()).deleteCourse(this.id);
    }

    public void deleteSelf() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(selfRef)
                .commit();
    }

    private void detailRedirect() {
        ((TermActivity)getActivity()).showCourseDetails(this.id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
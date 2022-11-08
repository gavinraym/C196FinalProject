package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject.databinding.FragmentSnapshotBinding;


public class SnapshotFragment extends Fragment {

    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "title";

    // TODO: Rename and change types of parameters
    private String id;
    private String title;
    private Fragment selfRef;

    public SnapshotFragment() {
        // Required empty public constructor
    }

    public static  SnapshotFragment newInstance(String id, String title) {
        SnapshotFragment fragment = new SnapshotFragment();
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
        Log.d("Snapshot Fragment","On create.");
        if (getArguments() != null) {
            id = getArguments().getString(ARG_PARAM1);
            title = getArguments().getString(ARG_PARAM2);
        }
        Log.d("Snapshot Fragment","Args set.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Snapshot Fragment","On create view.");
        View view = inflater.inflate(R.layout.fragment_snapshot, container, false);

        // Set item button title and onclick.
        Button itemButton = view.findViewById(R.id.ItemButton);
        itemButton.setText(this.title);

        itemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).detailTerm(v); }; } );

        // deleteButton set onclick
        Button deleteButton = view.findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).deleteTerm(v);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(selfRef)
                        .commit();
            }; } );


        Log.d("Snapshot Fragment","On create view finished.");
        return view;
    }
}
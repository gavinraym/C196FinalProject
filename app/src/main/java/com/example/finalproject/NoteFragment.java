package com.example.finalproject;

import static java.net.Proxy.Type.HTTP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.databinding.FragmentAssessmnentBinding;
import com.example.finalproject.databinding.FragmentNoteBinding;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentNoteBinding binding;
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "title";

    // TODO: Rename and change types of parameters
    private Long id;
    private String title;
    private Fragment selfRef;


    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(String param1 ) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.selfRef = this;
        if (getArguments() != null) {
            id = getArguments().getLong(ARG_PARAM1);
            title = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("AssessmentFragment","On view create:" + this.title);
        binding.editNoteText.setText(this.title);

        binding.saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {saveButtonClick(view); }
        });
        binding.sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {smsButtonClick(view); }
        });
        binding.deleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {deleteButtonClick(view); }
        });
    }

    public void saveButtonClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong("id",this.id);
        bundle.putString("title",binding.editNoteText.getText().toString());
        Log.d("NoteFragment", "Save button click.");
        ((CourseActivity)getActivity()).receiveNoteDataForDB(bundle);
    }

    public void smsButtonClick(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.putExtra("sms_body",this.title);
        startActivity(
                Intent.createChooser(
                sendIntent, "Which app should be used?"));
    }

    public void deleteButtonClick(View view) {
        Log.d("AssmntFragment", "Delete button click.");
        ((CourseActivity)getActivity()).deleteNote(this.id);
    }

    public void deleteSelf() {
        Log.d("AssessmentFragment","Delete self.");
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(this.selfRef)
                .commit();
    }


}
package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class BrowserControlFragment extends Fragment {

    View view;
    ImageButton ButtonCreate;
    changeViewPager ParentActivity;

    public BrowserControlFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BrowserControlFragment.changeViewPager) {
            ParentActivity = (BrowserControlFragment.changeViewPager) context;
        } else {
            throw new RuntimeException("You must implement passInfoInterface to attach this fragment");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static BrowserControlFragment newInstance(String param1, String param2) {
        BrowserControlFragment fragment = new BrowserControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_browser_control, container, false);

        ButtonCreate = (ImageButton) view.findViewById(R.id.ButtonCreate);

        ButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentActivity.connectPagerView();
            }
        });


        return view;

    }

    interface changeViewPager{
        void connectPagerView();
    }

}
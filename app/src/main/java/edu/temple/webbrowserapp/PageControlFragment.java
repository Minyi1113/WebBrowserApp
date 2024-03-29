package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class PageControlFragment extends Fragment {
    ImageButton ButtonGo;
    ImageButton ButtonNext;
    ImageButton ButtonBack;
    EditText editText;
    View view;
    ReadURL ParentActivity;

    public PageControlFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ReadURL) {
            ParentActivity = (ReadURL) context;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_page_control, container, false);

        ButtonGo = (ImageButton) view.findViewById(R.id.ButtonGo);
        ButtonBack = (ImageButton) view.findViewById(R.id.ButtonBack);
        ButtonNext = (ImageButton) view.findViewById(R.id.ButtonNext);
        editText = (EditText) view.findViewById(R.id.URL);

        ButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URLText =  editText.getText().toString();
                ParentActivity.PutURLinWebView(URLText);
            }
        });

        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentActivity.GobackToWeb();
            }
        });

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentActivity.GoForwordToWeb();
            }
        });
        return view;
    }

    public void updateURL(String url){
        editText.setText(url);
    }

    public interface ReadURL{
        void PutURLinWebView(String URL);
        void GobackToWeb();
        void GoForwordToWeb();
    }

    public String getURL(){
        String stringTmp = editText.getText().toString();
        return stringTmp;
    }

}
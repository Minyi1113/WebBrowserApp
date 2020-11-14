package edu.temple.webbrowserapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BrowserControlFragment extends Fragment {

    ImageButton ButtonCreat;
    View view;

    
    //interface
    public void addNewButtonListener(BrowserControlFragment.OnNewButtonClickListener listener){
        this.listener = listener;}

    public interface OnNewButtonClickListener{
        void OnNewButtonClick();
    }
    private BrowserControlFragment.OnNewButtonClickListener listener;

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    public static BrowserControlFragment newInstance() {
        return new BrowserControlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_browser_control, container, false);

        ButtonCreat = view.findViewById(R.id.ButtonCreate);

        ButtonCreat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnNewButtonClick();
            }
        });
        return view;
    }
}
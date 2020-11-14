package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PageListFragment extends Fragment {

    private ArrayList<String> ListWebTitle;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    View view;

    public PageListFragment() {
        // Required empty public constructor
    }

    public void addSelectListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int iID);
    }

    private OnItemSelectedListener listener;

    public static PageListFragment newInstance(ArrayList<String> listWebTitle) {
        PageListFragment fragment = new PageListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("WebTitle",listWebTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_page_list, container, false);
        listView =(ListView) view.findViewById(R.id.pageList);
        ListWebTitle =new ArrayList<>();
        ArrayList<String> arrayTemp = new ArrayList<>();

        if (getArguments()!=null) {
            arrayTemp = getArguments().getStringArrayList("WebTitle");

            for (int i = 0; i < arrayTemp.size(); i++){
                if (ListWebTitle.size() <= i){
                    ListWebTitle.add(arrayTemp.get(i));
                }
                else{
                    ListWebTitle.set(i, arrayTemp.get(i));
                }
            }
        }

        if (ListWebTitle ==null) {
            ListWebTitle = new ArrayList<>();
            ListWebTitle.add("");
        }

        arrayAdapter = new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_1, ListWebTitle);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null){
                    listener.onItemSelected(position);
                }
            }
        });

        return  view;
    }

    public void UpdateList(ArrayList<String> arrWebTitle){
            for (int i = 0; i < arrWebTitle.size(); i++){
                if (ListWebTitle.size() <= i){
                    ListWebTitle.add(arrWebTitle.get(i));
                }
                else{
                    ListWebTitle.set(i, arrWebTitle.get(i));
                }
            }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
}
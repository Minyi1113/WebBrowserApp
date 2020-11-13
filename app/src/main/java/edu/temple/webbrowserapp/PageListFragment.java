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
import android.widget.Toast;

import java.util.ArrayList;

public class PageListFragment extends Fragment {

    public void addSelectListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int iID);
    }

    private OnItemSelectedListener listener;

    private ArrayList<String> lstgWebTitle;
    private ListView lstPage;
    private ArrayAdapter adpList;

    public PageListFragment() {
        // Required empty public constructor
    }


    public static PageListFragment newInstance(ArrayList<String> lstWebTitle) {
        PageListFragment fragment = new PageListFragment();

        Bundle args = new Bundle();
        args.putStringArrayList("WebTitle",lstWebTitle);
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
        // Inflate the layout for this fragment
        final View myFragmentView =inflater.inflate(R.layout.fragment_page_list, container, false);
        lstPage=(ListView) myFragmentView.findViewById(R.id.pageList);

         if (savedInstanceState!=null){
            lstgWebTitle=savedInstanceState.getStringArrayList("lstgWebTitle");
         }
         else {
             lstgWebTitle = getArguments().getStringArrayList("WebTitle");
         }

        if (getArguments()!=null) {
            lstgWebTitle = getArguments().getStringArrayList("WebTitle");
            // Toast.makeText(getContext(), "getArguments()", Toast.LENGTH_LONG).show();
        }

        if (lstgWebTitle==null) {
            //  Toast.makeText(getContext(),"newArray",Toast.LENGTH_LONG).show();
            lstgWebTitle = new ArrayList<>();
            lstgWebTitle.add("");

        }

        adpList=new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_1,lstgWebTitle);
        lstPage.setAdapter(adpList);
        lstPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null){
                    listener.onItemSelected(position);
                }
            }
        });

        return  myFragmentView;
    }

    public void UpdateList(ArrayList<String> lstWebTitle){
        lstgWebTitle=lstWebTitle;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putStringArrayList("lstgWebTitle", lstgWebTitle);
    }

//    public void UpdateList(int position, String sTitle){
//        while (position>=lstgWebTitle.size())
//            lstgWebTitle.add("");
//        lstgWebTitle.set(position,sTitle);
//        adpList.notifyDataSetChanged();
//    }

//    public void AddATitle(String sTitle){
//        lstgWebTitle.add(sTitle);
//        adpList.notifyDataSetChanged();
//    }
//
//    public void UpdateList(ArrayList<String> arrWebTitle){
//        if (!arrWebTitle.isEmpty()) {
//            //
//            lstgWebTitle.clear();
//
//
//            while (arrWebTitle.size()>lstgWebTitle.size())
//                lstgWebTitle.add("");
//            for (int i=0;i<arrWebTitle.size();i++){
//                lstgWebTitle.add(arrWebTitle.get(i));
//            }
//            //lstgWebTitle=lstWebTitle;
//
//        }
//
//        String sTmp="ArrWeb-- ";
//        for (int i=0;i<arrWebTitle.size();i++){
//            sTmp=sTmp+" --- "+arrWebTitle.get(i);
//        }
//
//        //Toast.makeText(getContext(),"P - "+Integer.toString(position)+" FID - "+Integer.toString(arrgWeb.get(position).getFID()),Toast.LENGTH_LONG).show();
//        //Toast.makeText(getContext(),sTmp,Toast.LENGTH_LONG).show();
//        adpList.notifyDataSetChanged();
//        //lstPage.notifyAll();
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        //outState.putStringArrayList("lstgWebTitle", lstgWebTitle);
//        //super.onSaveInstanceState(outState);
//        //outState.putAll(outState);
//    }
}
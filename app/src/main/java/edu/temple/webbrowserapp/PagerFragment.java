package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;



public class PagerFragment extends Fragment implements PageViewerFragment.PageViewerInterface {

    ViewPager2 viewPager;
    ViewPagerFragmentStateAdapter viewPagerAdapter;
    FragmentManager fm;

    ArrayList<String> arrayWebTitle;
    ArrayList<PageViewerFragment> arrgWeb;


    private PagerFragment.OnChangeListener listener;

    public void addOnChangeListener(PagerFragment.OnChangeListener listener){
        this.listener = listener;}

    public interface OnChangeListener{
        void OnPagerPageChangeURL(int currentItem, String sURL);
        void OnPagerPageFinish(int currentItem, String sTitle);

        void OnPagerChanged(int position, String webTitle, String url);
    }


    public PagerFragment() {

    }

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
   //     Bundle args = new Bundle();
   //     fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = view.findViewById(R.id.viewpager);

        if (savedInstanceState!=null){
            arrayWebTitle=savedInstanceState.getStringArrayList("arrgWebTitle");
        }
        else {
            arrgWeb = new ArrayList<>();
            arrgWeb.add(new PageViewerFragment());
            PageViewerFragment pvfCurrent = arrgWeb.get(arrgWeb.size()-1);
            pvfCurrent.addPageListener(this);

            arrayWebTitle=new ArrayList<>();
            arrayWebTitle.add("");
        }


        viewPagerAdapter = new ViewPagerFragmentStateAdapter (this.getActivity());

        viewPager.setAdapter(viewPagerAdapter);


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (listener!=null){

                    listener.OnPagerChanged(position,
                            viewPagerAdapter.getFragment(position).getWebTitle(),
                            viewPagerAdapter.getFragment(position).getUrl());
                }
            }
        });

        return view;
    }


    public void OnPageChangeURL(String sURL) {
        if (listener!=null){
            listener.OnPagerPageChangeURL(viewPager.getCurrentItem(),sURL);
        }
    }

    @Override
    public void updateURL(String url) {

    }

    @Override
    public void OnPageFinish(String sTitle) {
        arrayWebTitle.set(viewPager.getCurrentItem(),sTitle);
        if (listener!=null){listener.OnPagerPageFinish(viewPager.getCurrentItem(),sTitle);}
    }



    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        ArrayList<PageViewerFragment> arrMyWeb;

        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            arrMyWeb = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
    //        if (position >= arrMyWeb.size())
     //           arrMyWeb.add(PageViewerFragment.newInstance(position));         //here may need to delete
            return arrMyWeb.get(position);

        }

        public PageViewerFragment getFragment(int position) {
            return arrMyWeb.get(position);
        }


        @Override
        public int getItemCount() {
            return arrMyWeb.size();
        }


    }

    public ArrayList<String> getWebTitleList() {
        return arrayWebTitle;
    }

    public void LoadPageFromURL(String sURL) {
        PageViewerFragment pvfCurrent;
        pvfCurrent = viewPagerAdapter.getFragment(viewPager.getCurrentItem());

        try {
            pvfCurrent.LoadPageFromURL(sURL);
        } catch (MalformedURLException q) {
            q.printStackTrace();
        }
    }

    //go back or next
    public void BackNext(int iBtn) {
        PageViewerFragment pvfCurrent;
        pvfCurrent = arrgWeb.get(viewPager.getCurrentItem());
         pvfCurrent.BackNext(iBtn);
    }

    public void AddFragment() {
        arrgWeb.add(new PageViewerFragment());
        arrayWebTitle.add("");
        viewPagerAdapter.createFragment(arrayWebTitle.size() - 1);
        viewPagerAdapter.notifyItemInserted(arrayWebTitle.size() - 1);
        viewPager.setCurrentItem(arrayWebTitle.size());
    }

    //set current fragment
    public void setCurrentFragment(int position) {
        viewPager.setCurrentItem(position);
    }



    //change current web title
    public void setCurrentWebTitle(String sTitle){
        arrayWebTitle.set(viewPager.getCurrentItem(),sTitle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putStringArrayList("arrgWebTitle",arrayWebTitle);


    }

  //      public void getViewPagerAdapter() {
  //          pager.add(new PageViewerFragment());
  //          viewPager.getAdapter().notifyDataSetChanged();
  //      }


    }


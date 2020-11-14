package edu.temple.webbrowserapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class PagerFragment extends Fragment implements PageViewerFragment.OnPageChangeURLListener {

    View view;
    ViewPager2 viewPager;
    ViewPagerFragmentStateAdapter viewPagerAdapter;
    ArrayList<PageViewerFragment> webArray;

    public PagerFragment() {

    }

    //interface


    public void addOnChangeListener(PagerFragment.OnChangeListener listener){
        this.listener = listener;}

    public interface  OnChangeListener{
        void OnPagerChangeURL(int position, String sURL);
        void OnPagerFinish(int position, String sTitle);
        void OnPagerChanged(int position,String sTitle,String sURL);
    }

    private PagerFragment.OnChangeListener listener;

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
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
        view = inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = view.findViewById(R.id.viewpager);

        webArray = new ArrayList<>();
        webArray.add(new PageViewerFragment());

        PageViewerFragment CurrentPageViewerFragment = webArray.get(webArray.size()-1);
        CurrentPageViewerFragment.addOnPageChangeURListener(this);

        viewPagerAdapter =new ViewPagerFragmentStateAdapter(this.getActivity(), webArray);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (listener!=null){
                    listener.OnPagerChanged(position,
                            webArray.get(position).getWebTitle(),
                            webArray.get(position).getUrl());
                }
            }
        });

        return  view;
    }

    public int getCurrentItemPosition(){
        return viewPager.getCurrentItem();
    }

    public String getCurrentTitle(){
        return webArray.get(viewPager.getCurrentItem()).getWebTitle();
    }

    public String getCurrentURL(){
        return webArray.get(viewPager.getCurrentItem()).getUrl();
    }

    @Override
    public void OnPageChangeURL(String sURL) {
        if (listener!=null){
            listener.OnPagerChangeURL(viewPager.getCurrentItem(),sURL);
        }
    }

    @Override
    public void OnPageFinish(String sTitle) {
        if (listener!=null){
            listener.OnPagerFinish(viewPager.getCurrentItem(),sTitle);
        }
    }


    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        ArrayList<PageViewerFragment> arrMyWeb;
        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<PageViewerFragment> arrWeb) {
            super(fragmentActivity);
            this.arrMyWeb=arrWeb;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return arrMyWeb.get(position);
        }
        @Override
        public int getItemCount() {
            return arrMyWeb.size();
        }
    }

    public ArrayList<String> getWebListTitle(){
        ArrayList<String> arrayWebTitle=new ArrayList<>();

        for (int i = 0; i< webArray.size(); i++){
            arrayWebTitle.add(webArray.get(i).getWebTitle());
        }
        return arrayWebTitle;
    }

    //load a website from URL
    public void LoadPageFromURL(String URL) {
        PageViewerFragment CurrentPageViewerFragment;
        CurrentPageViewerFragment = webArray.get(viewPager.getCurrentItem());

        try {
            CurrentPageViewerFragment.LoadPageFromURL(URL);
        }
        catch(MalformedURLException q) {
            q.printStackTrace();
        }
    }

    //go back or next
    public void BackNext(int itemBotton){
        PageViewerFragment CurrentPageViewerFragment;
        CurrentPageViewerFragment = webArray.get(viewPager.getCurrentItem());
        CurrentPageViewerFragment.BackNext(itemBotton);
    }

    //Add a new WebView fragment
    public void AddFragment(){
        webArray.add(new PageViewerFragment());
        PageViewerFragment CurrentPageViewerFragment = webArray.get(webArray.size()-1);
        CurrentPageViewerFragment.addOnPageChangeURListener(this);


        viewPagerAdapter.notifyItemInserted(webArray.size()- 1);
        viewPager.setCurrentItem(webArray.size()-1);

    }

    //set current fragment
    public void setCurrentFragment(int position){
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
}

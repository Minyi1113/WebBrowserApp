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

    //interface
    private PagerFragment.OnChangeListener listener;

    public void addOnChangeListener(PagerFragment.OnChangeListener listener){
        this.listener = listener;}

    public interface  OnChangeListener{
        void OnPagerPageChangeURL(int position, String sURL);
        void OnPagerPageFinish(int position,String sTitle);
        void OnPagerChanged(int position,String sTitle,String sURL);
    }

    private ViewPager2 viewPager;
    private ViewPagerFragmentStateAdapter viewPagerAdapter;
    ArrayList<PageViewerFragment> webArray;

    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance() {
        return new PagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = view.findViewById(R.id.viewpager);

        if (savedInstanceState==null){
            webArray = new ArrayList<>();
            webArray.add(new PageViewerFragment());
            PageViewerFragment pvfCurrent = webArray.get(webArray.size()-1);
            pvfCurrent.addOnPageChangeURListener(this);
        }


        viewPagerAdapter=new ViewPagerFragmentStateAdapter(this.getActivity(),webArray);
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

    public int getCurItemPosition(){return viewPager.getCurrentItem();}

    public String getCurItemTitle(){return webArray.get(viewPager.getCurrentItem()).getWebTitle();}

    public String getCurItemURL(){return webArray.get(viewPager.getCurrentItem()).getUrl();}

    @Override
    public void OnPageChangeURL(String sURL) {
        if (listener!=null){listener.OnPagerPageChangeURL(viewPager.getCurrentItem(),sURL);}
    }

    @Override
    public void OnPageFinish(String sTitle) {
        if (listener!=null){listener.OnPagerPageFinish(viewPager.getCurrentItem(),sTitle);}
    }

    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        ArrayList<PageViewerFragment> MyarrayWeb;
        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<PageViewerFragment> arrWeb) {
            super(fragmentActivity);
            this.MyarrayWeb=arrWeb;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return MyarrayWeb.get(position);
        }
        @Override
        public int getItemCount() {
            return MyarrayWeb.size();
        }
    }

    public ArrayList<String> getWebTitleList(){
        ArrayList<String> arrayWebTitle=new ArrayList<>();

        for (int i=0;i<webArray.size();i++){
            arrayWebTitle.add(webArray.get(i).getWebTitle());
        }
        return arrayWebTitle;
    }

    //load a website from URL
    public void LoadPageFromURL(String sURL) {
        PageViewerFragment pvfCurrent;
        pvfCurrent = webArray.get(viewPager.getCurrentItem());

        try {
            pvfCurrent.LoadPageFromURL(sURL);
        }
        catch(MalformedURLException q) {
            q.printStackTrace();
        }
    }

    //go back or next
    public void BackNext(int iBtn){
        PageViewerFragment pvfCurrent;
        pvfCurrent = webArray.get(viewPager.getCurrentItem());
        pvfCurrent.BackNext(iBtn);
    }

    //Add a new WebView fragment
    public void AddFragment(){
        webArray.add(new PageViewerFragment());
        PageViewerFragment pvfCurrent = webArray.get(webArray.size()-1);
        pvfCurrent.addOnPageChangeURListener(this);

        viewPagerAdapter.notifyItemInserted(webArray.size()- 1);
        viewPager.setCurrentItem(webArray.size()-1);

    }

    //set current fragment
    public void setCurrentFragment(int position){
        viewPager.setCurrentItem(position);
    }

}

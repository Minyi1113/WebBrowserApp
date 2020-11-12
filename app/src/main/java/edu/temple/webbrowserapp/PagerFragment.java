package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;


public class PagerFragment extends Fragment {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    ArrayList<PageViewerFragment> pager = new ArrayList<PageViewerFragment>();


    public PagerFragment() {

    }

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = view.findViewById(R.id.viewpager);

        pagerAdapter = new PagerAdapter(getChildFragmentManager(), pager);

        viewPager.setAdapter(pagerAdapter);

        return view;
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {

        private final ArrayList<PageViewerFragment> pager;

        public PagerAdapter(@NonNull FragmentManager fm, ArrayList<PageViewerFragment> pager) {
            super(fm);
            this.pager = pager;
        }

        @Override
        public Fragment getItem(int position) {
            return pager.get(position);
        }

        @Override
        public int getCount() {
            return pager.size();
        }

    }

    public void getViewPagerAdapter() {
        pager.add(new PageViewerFragment());
        viewPager.getAdapter().notifyDataSetChanged();
    }
}
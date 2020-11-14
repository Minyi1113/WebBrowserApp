package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,
            BrowserControlFragment.OnNewButtonClickListener,
            PageListFragment.OnItemSelectedListener,
            PagerFragment.OnChangeListener
{
    private FragmentManager fm;
    private PageControlFragment pageControlFragment;
    private BrowserControlFragment browserControlFragment;
    private PageListFragment pageListFragment;
    private PagerFragment pagerFragment;
    private int CurrentPagerID;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            CurrentPagerID = savedInstanceState.getInt("CurrentPagerID",0);
        }
        else{
            CurrentPagerID = 0;
        }

        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        Fragment tempFragment;

        //BrowserCtrl -> Add New page Button
        if ((tempFragment = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            browserControlFragment = (BrowserControlFragment) tempFragment;
        else {
            browserControlFragment = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .commit();
        }
        browserControlFragment.addNewButtonListener(this);


        //Url text box, go, back , next Button
        if ((tempFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) tempFragment;
        else {
            pageControlFragment = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }
        pageControlFragment.addButtonClickListener(this);


        //ViewPager and all webpager inside.
        if ((tempFragment = fm.findFragmentById(R.id.page_viewer)) instanceof PagerFragment)
            pagerFragment = (PagerFragment) tempFragment;
        else {
            pagerFragment = new PagerFragment();
            fm.beginTransaction()
                    .add(R.id.page_viewer, pagerFragment)
                    .commit();
        }
        pagerFragment.addOnChangeListener(this);


        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            //landscape
            if ((tempFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment) {
                pageListFragment = (PageListFragment) tempFragment;
            }
            else {
                pageListFragment = PageListFragment.newInstance(pagerFragment.getWebListTitle());
                fm.beginTransaction()
                        .add(R.id.page_list, pageListFragment)
                        .commit();
            }
            pageListFragment.addSelectListener(this);
        }
    }

    //ControlFragment Button Click
    @Override
    public void OnClick(int ButtonID){
         //click go
        if (ButtonID==R.id.ButtonGo) {
            pagerFragment.LoadPageFromURL(pageControlFragment.getURL());
        }else{
            pagerFragment.BackNext(ButtonID);
        }
    }

    @Override
    public void OnPagerChangeURL(int position, String URL) {
        pageControlFragment.setURL(URL);
    }

    @Override
    public void OnPagerFinish(int position, String stringTitle) {
        if (position== pagerFragment.getCurrentItemPosition()){
            getSupportActionBar().setTitle(pagerFragment.getCurrentTitle());
            pageControlFragment.setURL(pagerFragment.getCurrentURL());
        }
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            pageListFragment.UpdateList(pagerFragment.getWebListTitle());
        }
    }

    @Override
    public void OnPagerChanged(int position,String sTitle,String sURL){

        CurrentPagerID = pagerFragment.getCurrentItemPosition();
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            pageListFragment.UpdateList(pagerFragment.getWebListTitle());
        }
        pageControlFragment.setURL(pagerFragment.getCurrentURL());
        getSupportActionBar().setTitle(pagerFragment.getCurrentTitle());

    }

    //Add a new web page
    @Override
    public void OnNewButtonClick() {
        getSupportActionBar().setTitle("");
        pagerFragment.AddFragment();

    }

    @Override
    public void onItemSelected(int iID) {
        pagerFragment.setCurrentFragment(iID);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentPagerID", CurrentPagerID);
    }

}
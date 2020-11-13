package edu.temple.webbrowserapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.PageControlInterface,
                    PageViewerFragment.PageViewerInterface,
                    BrowserControlFragment.OnNewButtonClickListener,
                    PageListFragment.OnItemSelectedListener,
                    PagerFragment.OnChangeListener
        {

    private PageControlFragment pageControlFragment;
    private PageViewerFragment pageViewerFragment;
    private BrowserControlFragment browserControlFragment;
    private PageListFragment pageListFragment;
    private PagerFragment pagerFragment;

    private ArrayList<PageViewerFragment> arrViewer=new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrTest=new ArrayList<>();

        FragmentManager fm = getSupportFragmentManager();

        Fragment tmpFragment;

        pageControlFragment = new PageControlFragment();
        fm.beginTransaction()
                .add(R.id.page_control,pageControlFragment)
                .addToBackStack(null)
                .commit();


        pageViewerFragment = new PageViewerFragment();
        fm.beginTransaction()
                .add(R.id.page_viewer,pageViewerFragment)
                .addToBackStack(null)
                .commit();

        pageViewerFragment.addPageListener(this);

        browserControlFragment = new BrowserControlFragment();
        fm.beginTransaction()
                .add(R.id.browser_control,browserControlFragment)
                .addToBackStack(null)
                .commit();

        pageListFragment = new PageListFragment();
        fm.beginTransaction()
                .add(R.id.page_list,pageListFragment)
                .addToBackStack(null)
                .commit();

        pagerFragment = new PagerFragment();
        fm.beginTransaction()
                .add(R.id.viewpager,pagerFragment)
                .addToBackStack(null)
                .commit();

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //portrait
        } else {
            //landscape
            pageListFragment=(PageListFragment) fm.findFragmentById(R.id.pageList);

            if(pageListFragment == null){
                pageListFragment = PageListFragment.newInstance(arrTest);
                fm.beginTransaction().add(R.id.pageList,pageListFragment).commit();
                pageListFragment.addSelectListener(this);
            }
        }
    }

    //ControlFragment Button Click
    @Override
    public void OnClick(int btnID){
        //click go
        if (btnID==R.id.btnGo) {
            frPager.LoadPageFromURL(frPageControl.getURL());
        }else{
            frPager.BackNext(btnID);
        }
    }

    @Override
    public void PutURLinWebView(String URL) {
        pageViewerFragment.FullURL(URL);
        pageControlFragment.getURL();
    }

    public void GobackToWeb(){
        pageViewerFragment.gobackview();
    }

    public void GoForwordToWeb(){
        pageViewerFragment.goforwardView();
    }

    @Override
    public void updateURL(String url){
        pageControlFragment.updateURL(url);
    }

    @Override
    public void OnPageFinish(String title) {

    }


    public void OnPagerPageChangeURL(int position, String sURL) {
        pageControlFragment.updateURL(sURL);
    }

    public void OnPagerPageFinish(int position,String sTitle) {

        //getSupportActionBar().setTitle(sTitle);
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            //frPageList.UpdateList(position,sTitle);
            //Toast.makeText(getApplicationContext(),"OnPageFinish",Toast.LENGTH_LONG).show();
//            if (frPageList!=null)
//            frPageList.UpdateList(frPager.getWebTitleList());
        }
    }

    public void OnPagerChanged(int position,String sTitle,String sURL){

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            //frPageList.UpdateList(position,sTitle);
//            if (frPageList!=null)
//            frPageList.UpdateList(frPager.getWebTitleList());
        }


        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle(sTitle);
        actionBar.show();


        pageControlFragment.updateURL(sURL);
    }


    public void OnNewButtonClick() {
        pagerFragment.AddFragment();


        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){


        }
    }

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

    }


    public void onItemSelected(int iID) {
        pagerFragment.setCurrentFragment(iID);
    }





    //  public void connectPagerView(){
  //      pagerFragment.getViewPagerAdapter();
  //  }


}
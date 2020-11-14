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

    FragmentManager fm;
    int igCurPagerID;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){
            igCurPagerID=savedInstanceState.getInt("igCurPagerID",0);
        }
        else{
            igCurPagerID=0;
        }

        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        Fragment tmpFragment;

        //BrowserCtrl -> Add New page Button
        if ((tmpFragment = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            browserControlFragment = (BrowserControlFragment) tmpFragment;
        else {
            browserControlFragment = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .commit();
        }
        browserControlFragment.addNewButtonListener(this);

        //Url text box, go, back , next Button
        if ((tmpFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) tmpFragment;
        else {
            pageControlFragment = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }
        pageControlFragment.addButtonClickListener(this);


        //ViewPager and all webpager inside.
        if ((tmpFragment = fm.findFragmentById(R.id.viewpager)) instanceof PagerFragment)
            pagerFragment = (PagerFragment) tmpFragment;
        else {
            pagerFragment = new PagerFragment();
            fm.beginTransaction()
                    .add(R.id.viewpager, pagerFragment)
                    .commit();

        }
        pagerFragment.addOnChangeListener(this);

 //      pageControlFragment = new PageControlFragment();
 //       fm.beginTransaction()
 //               .add(R.id.page_control,pageControlFragment)
 //               .addToBackStack(null)
 //               .commit();


        pageViewerFragment = new PageViewerFragment();
        fm.beginTransaction()
                .add(R.id.page_viewer,pageViewerFragment)
                .addToBackStack(null)
                .commit();

        pageViewerFragment.addPageListener(this);

  //      browserControlFragment = new BrowserControlFragment();
   //     fm.beginTransaction()
  //              .add(R.id.browser_control,browserControlFragment)
  //              .addToBackStack(null)
  //              .commit();

        pageListFragment = new PageListFragment();
        fm.beginTransaction()
                .add(R.id.page_list,pageListFragment)
                .addToBackStack(null)
                .commit();

 //       pagerFragment = new PagerFragment();
  //      fm.beginTransaction()
  //              .add(R.id.viewpager,pagerFragment)
  //              .addToBackStack(null)
    //            .commit();





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
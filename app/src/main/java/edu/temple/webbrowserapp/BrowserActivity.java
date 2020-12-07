package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,
            BrowserControlFragment.OnNewButtonClickListener,
            PageListFragment.OnItemSelectedListener,
            PagerFragment.OnChangeListener
{
    private final int REQUEST_CODE=111;
    private FragmentManager fm;
    private PageControlFragment pageControlFragment;
    private BrowserControlFragment browserControlFragment;
    private PageListFragment pageListFragment;
    private PagerFragment pagerFragment;
    private int CurrentPagerID;
    private ArrayList<BookmarkList> bkgBookmark;
    private static int igClickID=-1;

    public static void ToBookmark(int iClick){
        igClickID=iClick;
    }
    
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null){
            CurrentPagerID=savedInstanceState.getInt("CurrentPagerID",0);
        }
        else{
            CurrentPagerID=0;
        }

        bkgBookmark=LoadBookmark();
        fm = getSupportFragmentManager();
        Fragment tmpFragment;

        //BrowserCtrl -> Add New page Button
        if ((tmpFragment = fm.findFragmentById(R.id.browser_control_FM)) instanceof BrowserControlFragment)
            browserControlFragment = (BrowserControlFragment) tmpFragment;
        else {
            browserControlFragment = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.browser_control_FM, browserControlFragment)
                    .commit();
        }
        browserControlFragment.addNewButtonListener(this);

        //Url text box, go, back , next Button
        if ((tmpFragment = fm.findFragmentById(R.id.page_control_FM)) instanceof PageControlFragment)
            pageControlFragment = (PageControlFragment) tmpFragment;
        else {
            pageControlFragment = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control_FM, pageControlFragment)
                    .commit();
        }
        pageControlFragment.addButtonClickListener(this);

        //ViewPager and all webpager inside.
        if ((tmpFragment = fm.findFragmentById(R.id.page_viewer_FM)) instanceof PagerFragment)
            pagerFragment = (PagerFragment) tmpFragment;
        else {
            pagerFragment = new PagerFragment();
            fm.beginTransaction()
                    .add(R.id.page_viewer_FM, pagerFragment)
                    .commit();
        }
        pagerFragment.addOnChangeListener(this);

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            //landscape
            if ((tmpFragment = fm.findFragmentById(R.id.page_list_FM)) instanceof PageListFragment) {
                pageListFragment = (PageListFragment) tmpFragment;
            }
            else {
                pageListFragment = PageListFragment.newInstance(pagerFragment.getWebTitleList());
                fm.beginTransaction()
                        .add(R.id.page_list_FM, pageListFragment)
                        .commit();
            }
            pageListFragment.addSelectListener(this);
        }
    }

    //ControlFragment Button Click
    @Override
    public void OnClick(int btnID){
         //click go
        if (btnID==R.id.ButtonGo) {
            pagerFragment.LoadPageFromURL(pageControlFragment.getURL());
        }else{
            pagerFragment.BackNext(btnID);
        }
    }

    @Override
    public void OnPagerPageChangeURL(int position, String sURL) {
        pageControlFragment.setURL(sURL);
    }

    @Override
    public void OnPagerPageFinish(int position,String sTitle) {
        if (position==pagerFragment.getCurItemPosition()){
            getSupportActionBar().setTitle(pagerFragment.getCurItemTitle());
            pageControlFragment.setURL(pagerFragment.getCurItemURL());
        }
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            pageListFragment.UpdateList(pagerFragment.getWebTitleList());
        }
    }

    @Override
    public void OnPagerChanged(int position,String sTitle,String sURL){
        CurrentPagerID=pagerFragment.getCurItemPosition();
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            pageListFragment.UpdateList(pagerFragment.getWebTitleList());
        }
        pageControlFragment.setURL(pagerFragment.getCurItemURL());
        getSupportActionBar().setTitle(pagerFragment.getCurItemTitle());
    }

    //Add a new web page
    @Override
    public void OnNewButtonClick() {
        getSupportActionBar().setTitle("");
        pagerFragment.AddFragment();
    }

    @Override
    public void OnBookmark(){
        Intent MyInform=new Intent (BrowserActivity.this,BookmarksActivity.class);
        MyInform.putExtra("myID",1);
        startActivityForResult(MyInform,REQUEST_CODE);
    }

    //save a new bookmark
    @Override
    public void OnSave(){
        String sURL= pagerFragment.getCurItemURL();
        String sTitle=pagerFragment.getCurItemTitle();

        if ((sURL!=null) && (sTitle!=null)){
            Log.v("KKK","canSave");
            for (int i=0;i<bkgBookmark.size();i++){
                String sTmp=bkgBookmark.get(i).getURL();
                if (sTmp.equals(sURL)){
                    Toast.makeText(getApplicationContext(),"Bookmark already exist.",Toast.LENGTH_LONG).show();
                    return;
                }
            }

            BookmarkList bkTmp=new BookmarkList();
            bkTmp.setVal(bkgBookmark.size(),sTitle,sURL);
            bkgBookmark.add(bkTmp);
            SaveBookmark();
            Toast.makeText(getApplicationContext(),"Bookmark save success.",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"No Title or URL \nBookmark not save",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(int iID) {
        pagerFragment.setCurrentFragment(iID);
    }

    //load the bookmark
    private ArrayList<BookmarkList> LoadBookmark(){
        ArrayList<BookmarkList> arrTemp=new ArrayList<>();

        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);

        int itotalBookmark=pref.getInt("TotalBookmark" , 0);

        for (int i=0; i<itotalBookmark;i++){
            BookmarkList bkTmp=new BookmarkList();
            int iTmpID=pref.getInt("B_ID_"+i,-1);
            String iTmpTitle=pref.getString("B_Title_"+i,"");
            String iTmpURL=pref.getString("B_URL_"+i,"");
            bkTmp.setVal(iTmpID,iTmpTitle,iTmpURL);
            arrTemp.add(bkTmp);
        }
        return arrTemp;
    }

    //save the bookmark
    private int SaveBookmark(){
        //SharedPreferences pref = getSharedPreferences("MyAppInfo" , MODE_MULTI_PROCESS);
        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("TotalBookmark" ,bkgBookmark.size());

        for (int i=0; i<bkgBookmark.size();i++){
            editor.putInt("B_ID_"+i,bkgBookmark.get(i).getID());
            editor.putString("B_Title_"+i,bkgBookmark.get(i).getTitle());
            editor.putString("B_URL_"+i,bkgBookmark.get(i).getURL());
        }

        editor.apply();
        return 0;
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        bkgBookmark=LoadBookmark();
        if (hasFocus && (igClickID>=0)){
            pagerFragment.LoadPageFromURL(bkgBookmark.get(igClickID).getURL());
            igClickID=-1;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentPagerID",CurrentPagerID);
    }
}
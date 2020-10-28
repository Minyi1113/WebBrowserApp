package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.ReadURL, PageViewerFragment.updateURL{

    private PageControlFragment pageControlFragment;
    private PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageControlFragment = new PageControlFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.page_control,pageControlFragment).addToBackStack(null).commit();

        pageViewerFragment = new PageViewerFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.page_viewer,pageViewerFragment).addToBackStack(null).commit();

        pageViewerFragment.addPageListener(this);
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
    public void SetURL(String url){
        pageControlFragment.updateURL(url);
    }

}
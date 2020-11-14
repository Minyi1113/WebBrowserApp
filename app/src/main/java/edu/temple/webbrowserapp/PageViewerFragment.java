package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;

public class PageViewerFragment extends Fragment {

    View view;
    private WebView webView;
    private WebSettings webSettings;
    private int FID;

    //interface


    public void addOnPageChangeURListener(PageViewerFragment.OnPageChangeURLListener listener){
        this.listener = listener;}

    public interface OnPageChangeURLListener{
        void OnPageChangeURL(String sURL);
        void OnPageFinish(String sTitle);
    }

    private PageViewerFragment.OnPageChangeURLListener listener;


    public PageViewerFragment() {

    }

    public static PageViewerFragment newInstance(int param1) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putInt("FID", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_page_viewer, container, false);

        if (getArguments()!=null) {
            FID = getArguments().getInt("FID");
        }

        webView =(WebView)view.findViewById(R.id.WebView);

        webView.addJavascriptInterface(this,"android");
        webView.setWebViewClient(webViewClient);

        webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        if(savedInstanceState != null){
            webView.restoreState(savedInstanceState);
        }
        return  view;
    }


    private WebViewClient webViewClient = new WebViewClient(){
        //finished
        @Override
        public void onPageFinished(WebView view, String url) {
            if (listener!=null){
                listener.OnPageFinish(view.getTitle());
            }
        }

        //Load page
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (listener!=null){
                listener.OnPageChangeURL(url);
            }
        }
    };

    public String getWebTitle(){
        String stringTitle="";
        if (webView !=null)
            stringTitle= webView.getTitle();
        return stringTitle;
    }

    public String getUrl(){
        String stringURL="";
        if (webView !=null)
            stringURL= webView.getUrl();
        return stringURL;
    }

    public void LoadPageFromURL(String URL) throws MalformedURLException {
        if (webView !=null)
            webView.loadUrl(URL);
    }

    public void BackNext(int itemBotton){
        if (itemBotton==R.id.ButtonBack) {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        }
        else if (itemBotton==R.id.ButtonNext){
            if (webView.canGoForward()) {
                webView.goForward();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        webView.saveState(outState);
    }
}
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
    //var
    private WebView webView;
    private WebSettings webSettings;

    //interface
    private PageViewerFragment.OnPageChangeURLListener listener;

    public void addOnPageChangeURListener(PageViewerFragment.OnPageChangeURLListener listener){
        this.listener = listener;}

    public interface  OnPageChangeURLListener{
        void OnPageChangeURL(String sURL);
        void OnPageFinish(String sTitle);
    }

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance() {
        return new PageViewerFragment();
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
        // Inflate the layout for this fragment
        final View myFragmentView =inflater.inflate(R.layout.fragment_page_viewer, container, false);
        webView=(WebView)myFragmentView.findViewById(R.id.WebView);
        webView.addJavascriptInterface(this,"android");
        webView.setWebViewClient(webViewClient);
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        if(savedInstanceState != null){
            webView.restoreState(savedInstanceState);
        }
        return  myFragmentView;
    }

    private WebViewClient webViewClient=new WebViewClient(){
        //finished
        @Override
        public void onPageFinished(WebView view, String url) {
            if (listener!=null){listener.OnPageFinish(view.getTitle());}
        }

        //Load page
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (listener!=null){listener.OnPageChangeURL(url);}
        }
    };

    public String getWebTitle(){
        String sRtn="";
        if (webView!=null)
            sRtn=webView.getTitle();
        return sRtn;
    }

    public String getUrl(){
        String sRtn="";
        if (webView!=null)
            sRtn=webView.getUrl();
        return sRtn;
    }

    public void LoadPageFromURL(String sURL) throws MalformedURLException {
        if (webView!=null)
            webView.loadUrl(sURL);
    }

    public void BackNext(int iBtn){
        if (iBtn==R.id.ButtonBack) {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        }
        else if (iBtn==R.id.ButtonNext){
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
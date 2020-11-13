package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;


public class PageViewerFragment extends Fragment {

    View view;
    WebView webView;
    PageViewerInterface browserActivity;
    int FID;

    public PageViewerFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageViewerInterface) {
            browserActivity = (PageViewerInterface) context;
        } else {
            throw new RuntimeException("You must implement the required interface");
        }
    }

    PageViewerFragment.PageViewerInterface updatelistener;

    public void addPageListener(PageViewerInterface updatelistener){
        this.updatelistener = updatelistener;
    }

    public interface PageViewerInterface{
        void updateURL(String url);

        void OnPageFinish(String title);
    }

    public static PageViewerFragment newInstance(int param1) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putInt("FID", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = (WebView) view.findViewById(R.id.WebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);

        // Restore WebView session
        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);

        return view;
    }

    private WebViewClient webViewClient = new WebViewClient(){

        public void onPageFinished(WebView view, String url) {
            if (updatelistener!=null){
                updatelistener.OnPageFinish(view.getTitle());
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            updatelistener.updateURL(url);

        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    public void FullURL(String url){

        webView.loadUrl(url);

        if(url.contains("http")) {
            webView.loadUrl(url);
            webView.getSettings().setJavaScriptEnabled(true);

        }else if (url.contains(".com")){
            webView.loadUrl("http://"+url);
            webView.getSettings().setJavaScriptEnabled(true);

        }else{
            webView.loadUrl("http://"+url+".com/");
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

    public void gobackview(){
        webView.goBack();
    }

    public void goforwardView(){
        webView.goForward();
    }

    public String getWebTitle(){
        String sRtn="";
        if (webView!=null)
            sRtn = webView.getTitle();
        return sRtn;
    }
    public String getUrl(){
        String sRtn="";
        if (webView!=null)
            sRtn = webView.getUrl();
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


}
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


public class PageViewerFragment extends Fragment {

    View view;
    WebView webView;
    PageViewerInterface browserActivity;

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

    private PageViewerFragment.updateURL updatelistener;

    public void addPageListener(updateURL updatelistener){
        this.updatelistener = updatelistener;
    }
    public interface updateURL{
        void SetURL(String url);
    }

    public static PageViewerFragment newInstance(String param1, String param2) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            updatelistener.SetURL(url);

        }
    };

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

    interface PageViewerInterface {
        void updateUrl(String url);
    }

}
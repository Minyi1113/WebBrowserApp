package edu.temple.webbrowserapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class PageViewerFragment extends Fragment {

    View view;
    WebView webView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public PageViewerFragment() {
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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = (WebView) view.findViewById(R.id.WebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);

        return view;
    }

    private WebViewClient webViewClient = new WebViewClient(){

        public void onPageFinished(WebView view, String url) {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (updatelistener!=null){
                updatelistener.SetURL(url);
            }
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

    public  void goforwardView(){
        webView.goForward();
    }

}
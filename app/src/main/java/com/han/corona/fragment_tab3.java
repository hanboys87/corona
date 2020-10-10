package com.han.corona;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class fragment_tab3 extends Fragment {


    private WebView mWebView;
    private WebSettings mWebSettings; //웹뷰세팅
    Context mContext;

    public fragment_tab3() {
        // Required empty public constructor
    }

    public fragment_tab3(Context context) {
        mContext = context;
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        InitWebview(view);
        return view;
    }

    void InitWebview(View view){

        mWebView = (WebView)view.findViewById(R.id.webView);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(mContext);
        }

        mWebView.setWebViewClient(new WebClient());
        WebSettings set = mWebView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        set.setDomStorageEnabled( true );
        mWebView.loadUrl("https://qr.naver.com/");


    }

    class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        public void onPageFinished(WebView view, String url) {
            CookieSyncManager.getInstance().sync();
        }
    }
}
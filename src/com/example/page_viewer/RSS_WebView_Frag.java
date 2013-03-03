package com.example.page_viewer;


import com.example.rssfeeder.R;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
/**
 *  This is a fragment with a embedded browser, 
 * @author Jachu
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class RSS_WebView_Frag extends Fragment{
	
	private WebView webView;
	private ProgressBar progressBar;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
		View view = inflater.inflate(R.layout.webview_layout, container, false);
		Bundle arg1 = this.getArguments();
		String url = arg1.getString("uri");
		
		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		webView = (WebView) 		view.findViewById(R.id.web_view);
		webView.setWebViewClient(new MyWebViewClient());
		webView.getSettings().setBuiltInZoomControls(true); 
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setAllowFileAccess(true); 
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
		
        webView.loadUrl(url); 
        
		return view;
	}
	
	 public class MyWebViewClient extends WebViewClient {        
		 public void onPageStarted(WebView view, String url, Bitmap favicon) {
		       super.onPageStarted(view, url, favicon);
		 }
		 public boolean shouldOverrideUrlLoading(WebView view, String url) {
              return super.shouldOverrideUrlLoading(view, url);
            }
		 public void onPageFinished(WebView view, String url) {
	            super.onPageFinished(view, url);
	            progressBar.setVisibility(View.GONE);
	        }
     }
	 
	
	 
}
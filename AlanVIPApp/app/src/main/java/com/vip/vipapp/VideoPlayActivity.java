package com.vip.vipapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class VideoPlayActivity extends Activity {

    private WebView myWebView;
    private int position = 0;
    private MediaController mediaControls;
    private Button btn;
    private ProgressBar progressBar;
    private String url;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        if (mediaControls == null) {
            mediaControls = new MediaController(VideoPlayActivity.this);
        }
        myWebView = (WebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        btn = (Button) findViewById(R.id.btn_cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent videotomain=new Intent(VideoPlayActivity.this,MainActivity.class);
                startActivity(videotomain);
                 VideoPlayActivity.this.finish();


            }
        });
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        myWebView.setWebChromeClient(new WebChromeClient());

        progressBar.setVisibility(View.VISIBLE);

        Intent i = getIntent();
        if (i != null) {
            url = i.getStringExtra("videoUrl");
        }
        myWebView.loadUrl(url);
        progressBar.setVisibility(View.GONE);
//      myWebView.loadUrl("https://www.youtube.com/embed/0fAD-4K87dY?rel=0&amp;controls=0&amp;showinfo=0");
//      myWebView.loadUrl("https://player.vimeo.com/video/200325511?color=82c99a");

    }
}
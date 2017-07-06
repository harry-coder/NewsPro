package com.example.harpreet.okhlee;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

public class news_web extends AppCompatActivity {

    private WebView  newsWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Slidr.attach(this);

        newsWeb= (WebView) findViewById(R.id.newsWeb);
        newsWeb.getSettings().setJavaScriptEnabled(true);
        newsWeb.getSettings().getLoadsImagesAutomatically();
        newsWeb.setWebViewClient(new webClient());

       savedInstanceState=getIntent().getExtras();
       // Toast.makeText(this, ""+savedInstanceState.getString("NewTitle"), Toast.LENGTH_SHORT).show();
        newsWeb.loadUrl(savedInstanceState.getString("NewTitle"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    class webClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

           view.loadUrl(url);
            return true;
        }
    }

}

package com.example.harpreet.okhlee;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;
import com.squareup.picasso.Picasso;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

public class News_Description extends AppCompatActivity {

    private String title,description,image,url;

    private GyroscopeObserver gyroscopeObserver;
    private PanoramaImageView panoramaImageView;
    private TextView mainTitle,bodyDescription;
   private WebView newsWeb;

    private FABRevealLayout reveal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news__description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gyroscopeObserver=new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI/9);
        panoramaImageView = (PanoramaImageView) findViewById(R.id.panorama_image_view);

        newsWeb= (WebView) findViewById(R.id.news_Web);

       reveal= (FABRevealLayout) findViewById(R.id.fab_reveal_layout);


        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        savedInstanceState=getIntent().getExtras();

        newsWeb.getSettings().setJavaScriptEnabled(true);
        newsWeb.getSettings().getLoadsImagesAutomatically();
        newsWeb.setWebViewClient(new webClient());

        title=savedInstanceState.getString("Title");
        url=savedInstanceState.getString("Url");
        description=savedInstanceState.getString("Description");
        image=savedInstanceState.getString("Image");


        newsWeb.loadUrl(url);

        mainTitle= (TextView) findViewById(R.id.main_Heading);
        bodyDescription= (TextView) findViewById(R.id.body);
        DisplayImage(image);

      /*  reveal.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {

            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {

                *//*Button bt= (Button) secondaryView.findViewById(R.id.reveal);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(News_Description.this, "Hello sir", Toast.LENGTH_SHORT).show();
                        reveal.revealMainView();

                    }
                });*//*
            }
        });
*/

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 *//*newsWeb.getSettings().setJavaScriptEnabled(true);
        newsWeb.getSettings().getLoadsImagesAutomatically();
        newsWeb.setWebViewClient(new webClient());


                newsWeb.loadUrl(url);

*//*

            }
        });*/
    }

    public  void DisplayImage(final String url)
    {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                Picasso.with(News_Description.this).load(url).error(R.drawable.place)
                        .placeholder(R.drawable.place).into(panoramaImageView);

                mainTitle.setText(title);
                bodyDescription.setText(description);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
      gyroscopeObserver.register(this);

    }
    @Override
    protected void onPause() {
        super.onPause();
        // Unregister GyroscopeObserver.
        gyroscopeObserver.unregister();
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

package com.example.harpreet.okhlee;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.harpreet.okhlee.TabsFragments.trending;
import com.example.harpreet.okhlee.volly.MyApplication;
import com.example.harpreet.okhlee.youtube.comments;
import com.example.harpreet.okhlee.youtube.relatedVideos;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.r0adkll.slidr.Slidr;

public class Youtube_Comment extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private String id, videoTitle;


    private TextView title;
    private EditText userComment;
    private Handler handle = new Handler();
    String androidId;
    DialogPlus dialog;
    private ScrollView commentScrollView;
    ObjectAnimator anim;

    private BottomSheetLayout bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        title = (TextView) findViewById(R.id.specific_title);
        findViewById(R.id.whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(sendIntent);


                    }
                });


            }
        });

        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Youtube_Comment.this, "Facebook", Toast.LENGTH_SHORT).show();
            }
        });


        Slidr.attach(this, Color.WHITE, Color.WHITE);

        Intent intent = getIntent();
        id = intent.getStringExtra("video id");

        videoTitle = intent.getStringExtra("title");
        title.setText(videoTitle);

        android.app.Fragment comment = comments.newInstance(id, null);
        Fragment relatedVideoSection = relatedVideos.newInstance(id, videoTitle);

        getFragmentManager().beginTransaction().add(R.id.commentSection, comment).commit();
        getFragmentManager().beginTransaction().add(R.id.relatedVideos, relatedVideoSection, null).commit();

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        initializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(id);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Toast.makeText(Youtube_Comment.this, "Please Download Youtube app first", Toast.LENGTH_SHORT).show();
            }
        };
        youTubePlayerView.initialize("AIzaSyCDVqkvSQpLf9NQTfUAHvs9FS-0vp0J7aI", initializedListener);

        /*bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Add Comment", R.drawable.comment);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Share", R.drawable.share);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.redheart);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setAccentColor(Color.parseColor("#075e54"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(2);

        createNotification(String.valueOf(comments.jsonYoutubeComment.size()));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(final int position, boolean wasSelected) {


                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (position == 0) {

                              androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                            //bottomSheet.showWithSheetView(LayoutInflater.from(Youtube_Comment.this).inflate(R.layout.comment_box, bottomSheet, false));
                            final InputMethodManager imm = (InputMethodManager) getSystemService(Youtube_Comment.INPUT_METHOD_SERVICE);

                            //new comments().showview(Youtube_Comment.this);
                             dialog = DialogPlus.newDialog(Youtube_Comment.this)
                                    .setContentHolder(new ViewHolder(R.layout.comment_box)).setGravity(Gravity.TOP)
                                    .setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(DialogPlus dialog, View view) {
                                            if (view.getId() == R.id.commentButton)
                                          //  new comments().publishComment("Nice Video man!!",id,androidId);
                                                    userComment= (EditText) findViewById(R.id.commentBox);
                                                 String data=userComment.getText().toString();

                                                    new comments().publishComment(data,id,androidId);



                                            dialog.dismiss();
                                           imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);

                                        }
                                    }).setPadding(20, 20, 20, 20).setCancelable(false).setMargin(20, 20, 20, 20).setOnBackPressListener(new OnBackPressListener() {
                                        @Override
                                        public void onBackPressed(DialogPlus dialogPlus) {


                                            dialogPlus.dismiss();



                                        }
                                    }).setCancelable(true)
                                    // This will enable the expand feature, (similar to android L share dialog)
                                    .create();
                            dialog.show();


                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                        } else if (position == 1) {

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                            shareIntent.putExtra(Intent.EXTRA_TEXT, id);
                            // startActivity(Intent.createChooser(shareIntent,"Share Via.."));


                            setShare(shareIntent);
                        }
                    }
                });

                return true;
            }
        });


    }

    public void createNotification(final String totalComment) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText(totalComment)
                        .setBackgroundColor(Color.parseColor("#09d261"))
                        .setTextColor(Color.BLACK)
                        .build();
                // Adding notification to last item.

                bottomNavigation.setNotification(notification, 0);


            }
        });
    }

    public void setShare(final Intent shareIntent) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(getApplicationContext(), shareIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
                    @Override
                    public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                        bottomSheet.dismissSheet();
                        startActivity(activityInfo.getConcreteIntent(shareIntent));

                    }
                });
                bottomSheet.showWithSheetView(intentPickerSheet);
            }
        });


    }*/
    }

    /*public void getAnyFileView()
    {
        LayoutInflater inflater=getLayoutInflater();

        View view=inflater.inflate(R.layout.content_youtube__comment,null);
        commentScrollView = (ScrollView) view.findViewById(R.id.commentScroll);

        Toast.makeText(this, ""+commentScrollView, Toast.LENGTH_SHORT).show();

        //commentScrollView.fling(5000);

          anim = ObjectAnimator.ofInt(commentScrollView, "scrollY", 10);
        anim.setDuration(2000);
        anim.start();

    }*/
}

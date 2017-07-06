package com.example.harpreet.okhlee.TabsFragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.harpreet.okhlee.MainActivity;
import com.example.harpreet.okhlee.NewsList;
import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.Youtube_Comment;
import com.example.harpreet.okhlee.paginator.YoutubePaginator;
import com.example.harpreet.okhlee.pojo.youtubeItems;
import com.example.harpreet.okhlee.volly.MyApplication;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.example.harpreet.okhlee.youtube.comments;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.harpreet.okhlee.pojo.youtubeItemNames.yId;
import static com.example.harpreet.okhlee.pojo.youtubeItemNames.yTitile;
import static com.example.harpreet.okhlee.volly.MyApplication.youtubeApiKey;

/**
 * A simple {@link Fragment} subclass.
 */


public class trending extends Fragment {

    int count = 0;
    private PullToLoadView pullToLoadView;
    ListView listView;
    MainActivity activity;

    private BottomSheetLayout bottomSheet;
   // DatabaseReference mref = FirebaseDatabase.getInstance().getReference();

    private YouTubePlayer.OnInitializedListener playerlistener;

    private comments commentFrag;
    ArrayList<youtubeItems> youtubeJsonDataList = new ArrayList<>();


    public trending() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.trending, container, false);


        bottomSheet = (BottomSheetLayout) view.findViewById(R.id.flipboard);

        // bottomSheet.showWithSheetView(LayoutInflater.from(getActivity()).inflate(R.layout.comments, bottomSheet, false));
        pullToLoadView = (PullToLoadView) view.findViewById(R.id.pullToLoadView);

        new YoutubePaginator(getActivity(), pullToLoadView, bottomSheet, activity);
        return view;
    }


    public class myAdapter extends RecyclerView.Adapter<myAdapter.myHolder> {

        ArrayList<youtubeItems> youtubeItemsList;

        Context context;

        MainActivity activity;

        LayoutInflater inflater;
        private BottomSheetLayout bottomSheet1;

        public myAdapter(Context context, ArrayList<youtubeItems> youtubeItemsList, BottomSheetLayout bottomSheet, MainActivity activity) {

            inflater = LayoutInflater.from(context);
            this.context = context;
            this.youtubeItemsList = youtubeItemsList;
            this.bottomSheet1 = bottomSheet;
            this.activity = activity;


        }

        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.trending_singleitem, parent, false);
            myHolder holder = new myHolder(view);
            return holder;
        }


        public void ThumbNailLoader(YouTubeThumbnailLoader youTubeThumbnailLoader, YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener, int position) {
            youTubeThumbnailLoader.setVideo(youtubeItemsList.get(position).getId());
            youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);

        }

        /*public void traverseServer(final myHolder holder, final int position) {
            // String likes;
            // String value;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mref = mref.child(youtubeItemsList.get(position).getId());
                    Toast.makeText(context, "Inside " + mref.getKey(), Toast.LENGTH_SHORT).show();
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterator<DataSnapshot> data = dataSnapshot.getChildren().iterator();
                            while (data.hasNext()) {
                                holder.likes.setText(data.next().getKey());
                                if (data.next().getValue().toString().equals("true")) {
                                    holder.change_Heart.setImageResource(R.drawable.redheart);
                                } else {
                                    holder.change_Heart.setImageResource(R.drawable.blackheart);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }).start();

        }*/

        @Override
        public void onBindViewHolder(final myHolder holder, final int position) {


            holder.youtubeTitle.setText(youtubeItemsList.get(position).getTitle());

            //here v will check whether the database contains the video id or not
            //if yes retrieve the hashmap associated with it..the hashmap will contain the likes and type of image to show..like
            //false for black heart and true for red heart..


            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.overYoutubePlayer.setVisibility(View.VISIBLE);


                }

                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {


                }
            };
            holder.youTubeThumbnailView.initialize(youtubeApiKey, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {


                    ThumbNailLoader(youTubeThumbnailLoader, onThumbnailLoadedListener, position);

                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {


                }
            });
        }

        public void add(youtubeItems item) {
            youtubeItemsList.add(item);
            notifyDataSetChanged();
            youtubeJsonDataList.add(item);
        }

        public void clear() {
            youtubeItemsList.clear();
            youtubeJsonDataList.clear();
        }

        @Override
        public int getItemCount() {
            //      Toast.makeText(context, "" + youtubeItemsList.size(), Toast.LENGTH_SHORT).show();
            return youtubeItemsList.size();
        }


        public class myHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            //   YouTubePlayer.OnInitializedListener yListener;

            RelativeLayout overYoutubePlayer;
            ImageView playButton, change_Heart, comment, share;
            YouTubeThumbnailView youTubeThumbnailView;
            TextView youtubeTitle;
            TextView likes;


            public myHolder(View itemView) {
                super(itemView);


                overYoutubePlayer = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
                playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
                youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
                youtubeTitle = (TextView) itemView.findViewById(R.id.youtubeTitle);
                change_Heart = (ImageView) itemView.findViewById(R.id.dislikeHeart);
                comment = (ImageView) itemView.findViewById(R.id.comment);
                share = (ImageView) itemView.findViewById(R.id.share);
                // like_heart= (ImageView) itemView.findViewById(R.id.likeHeart);
                likes = (TextView) itemView.findViewById(R.id.likes_Data);
                change_Heart.setOnClickListener(this);
                comment.setOnClickListener(this);
                share.setOnClickListener(this);
                playButton.setOnClickListener(this);


                // new youtubeView(itemView);

            }

            @Override
            public void onClick(View v) {
                int totalLikes;
                youtubeItems data = null;
                //    int id1=R.drawable.dislike;
                //  int id2=R.drawable.like;

                if (v.getId() == R.id.btnYoutube_player) {
                    data = youtubeJsonDataList.get(getAdapterPosition());
                    //  Toast.makeText(getActivity(), "" + data.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent(activity.getActivityf(), youtubeApiKey, data.getId());
                    context.startActivity(intent);
                } else if (v.getId() == R.id.share) {

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                    shareIntent.putExtra(Intent.EXTRA_TEXT, youtubeItemsList.get(getAdapterPosition()).getId());
                    // startActivity(Intent.createChooser(shareIntent,"Share Via.."));

                    setShare(shareIntent);


                } else if (v.getId() == R.id.dislikeHeart) {

                    //mref=mref.child(data.getId());
                    //mref.child();
                    likes.setText("0");
                    boolean thr = true;
                    //here on pressing the black heart image, the image will change to red heart and likes get increased
                    // and immediately after that video id with likes and type of image will get stored to the database..


                    if (count == 0) {
                        totalLikes = 0;
                        //   Toast.makeText(getActivity(), ""+R.drawable.dislike, Toast.LENGTH_SHORT).show();
                        // totalLikes=totalLikes+1;
                        // Toast.makeText(getActivity(), "inside love", Toast.LENGTH_SHORT).show();
                        YoYo.with(Techniques.RotateIn).playOn(change_Heart);
                        //  totalLikes = Integer.parseInt(likes.getText().toString());
             /*       if(totalLikes==1)
                    {
                        totalLikes=0;
                    }*/


                        //for comments use android sliding up panel
                        change_Heart.setImageResource(R.drawable.redheart);
                        likes.setText(String.valueOf(totalLikes + 1));

                        Toast.makeText(context, "In count 0 " + totalLikes, Toast.LENGTH_SHORT).show();

                        count++;
                    } else if (count == 1) {

                        // totalLikes = Integer.parseInt(likes.getText().toString());

                        totalLikes = 1;
                        // totalLikes=totalLikes-1;
                        YoYo.with(Techniques.RotateIn).playOn(change_Heart);
                    /*if(totalLikes==0)
                    {
                        totalLikes=1;
                    }*/

                        change_Heart.setImageResource(R.drawable.blackheart);
                        likes.setText(String.valueOf((totalLikes - 1)));
                        Toast.makeText(context, "In count 1 " + totalLikes, Toast.LENGTH_SHORT).show();
                        count--;
                    }


                } else if (v.getId() == R.id.comment)

                {
                    Intent intent = new Intent(context, Youtube_Comment.class);
                    intent.putExtra("video id", youtubeItemsList.get(getAdapterPosition()).getId());
                    intent.putExtra("title",youtubeItemsList.get(getAdapterPosition()).getTitle());
                    context.startActivity(intent);


                }

            }

           /* public View setList() {
                System.out.println("Inside this");
                View view = LayoutInflater.from(context).inflate(R.layout.comments, bottomSheet1, false);
                listView = (ListView) view.findViewById(R.id.listView);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("comments");

                FirebaseListAdapter<String> adapter = new FirebaseListAdapter<String>(getActivity(), String.class, android.R.layout.simple_list_item_1, databaseReference) {
                    @Override
                    protected void populateView(View v, String model, int position) {

                        TextView text = (TextView) v.findViewById(android.R.id.text1);
                        System.out.println(model);
                        text.setText(model);

                    }
                };
                listView.setAdapter(adapter);


                return view;
            }
*/
            public void setShare(final Intent shareIntent) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(context, shareIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
                            @Override
                            public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                                bottomSheet1.dismissSheet();
                                context.startActivity(activityInfo.getConcreteIntent(shareIntent));

                            }
                        });
                        bottomSheet1.showWithSheetView(intentPickerSheet);
                    }
                });


            }
        }
    }
}



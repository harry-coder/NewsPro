package com.example.harpreet.okhlee.youtube;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harpreet.okhlee.NewsList;
import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.Youtube_Comment;
import com.example.harpreet.okhlee.news_web;
import com.example.harpreet.okhlee.pojo.NewsItems;
import com.example.harpreet.okhlee.pojo.youtubeItems;
import com.example.harpreet.okhlee.volly.MyApplication;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.harpreet.okhlee.pojo.categoryNames.jsonImage;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonTitle;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonUrl;


public class relatedVideos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private vollySingleton singleton;
    private RequestQueue request;
    private String fetchedVideoId;
    private String fetchedVideoTitle;
    private String fetchedVideoImage;

    Handler handler = new Handler();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView relatedVideoRecycleView;
    MyVideoAdapter adapter;

    ArrayList<youtubeItems> RelatedVideoList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public relatedVideos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment relatedVideos.
     */
    // TODO: Rename and change types and number of parameters
    public static relatedVideos newInstance(String param1, String param2) {
        relatedVideos fragment = new relatedVideos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        singleton = vollySingleton.getInstance();
        request = singleton.getRequestQueue();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.related_videos, container, false);
        getJsonData(mParam1);
        adapter = new MyVideoAdapter(getActivity());
        relatedVideoRecycleView = (RecyclerView) view.findViewById(R.id.relatedVideo);


        relatedVideoRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));


        relatedVideoRecycleView.setAdapter(adapter);


        return view;
    }


    public void getJsonData(String id) {

        //  System.out.println("youtube id "+id);

        //Toast.makeText(getActivity(), "youtube id "+id, Toast.LENGTH_SHORT).show();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, MyApplication.getRelatedYoutubeVideos(id),

                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response == null && response.length() == 0) {
                    return;
                } else {
                    try {

                        RelatedVideoList = processRequest(response);

                        adapter.setSource(RelatedVideoList);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(pagination.this, "This is sad!! No Data Or No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        request.add(jsonRequest);

    }


    public ArrayList<youtubeItems> processRequest(JSONObject response) {

        ArrayList<youtubeItems> demo = new ArrayList<>();


        try {


            JSONArray VideoArray = response.getJSONArray("items");


            for (int i = 0; i < VideoArray.length(); i++) {

                JSONObject currentVideoItem = VideoArray.getJSONObject(i);


                JSONObject idObject = currentVideoItem.getJSONObject("id");
                fetchedVideoId = idObject.getString("videoId");


                JSONObject titleObject = currentVideoItem.getJSONObject("snippet");
                fetchedVideoTitle = titleObject.getString("title");


                JSONObject imageObject = titleObject.getJSONObject("thumbnails");

                JSONObject defaultImageObject = imageObject.getJSONObject("default");


                if (defaultImageObject.has("url")) {

                    fetchedVideoImage = defaultImageObject.getString("url");

                } else {
                    fetchedVideoImage = "";

                }

                //YOU HAVE TO UNCOMMENT THIS.....
                youtubeItems yitems = new youtubeItems();
                yitems.setTitle(fetchedVideoTitle);
                yitems.setImage(fetchedVideoImage);
                yitems.setId(fetchedVideoId);
                demo.add(yitems);


            }


        } catch (Exception e) {

            System.out.println("Error pooped sorry " + e);
            Toast.makeText(getActivity(), "Error pooped sorry " + e, Toast.LENGTH_SHORT).show();
        }


        return demo;
    }


    class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.myHolder> {
        Context context;
        ArrayList<youtubeItems> youtubeItem = new ArrayList<>();
        LayoutInflater inflater;


        public void setSource(ArrayList<youtubeItems> list) {
            this.youtubeItem = list;

            notifyItemChanged(0, list.size());
        }

        public MyVideoAdapter(Context context) {
            this.context = context;


            inflater = LayoutInflater.from(context);


        }


        // displaying the image in circle shape
        public void displayImage(String ImageUrl, myHolder holder) {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.GRAY)
                    .borderWidthDp(2).cornerRadiusDp(30).oval(false).build();

            Picasso.with(context).load(ImageUrl).error(R.drawable.place)
                    .placeholder(R.drawable.place).resize(250, 250).centerCrop().transform(transformation).into(holder.image);

        }

        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = inflater.inflate(R.layout.singlelistitem, parent, false);

            myHolder holder = new myHolder(view);


            return holder;
        }

        @Override
        public void onBindViewHolder(final myHolder holder, final int position) {

            handler.post(new Runnable() {
                @Override
                public void run() {

                    youtubeItems items = youtubeItem.get(position);

                    holder.title.setText(items.getTitle());
                    displayImage(items.getImage(), holder);
                }
            });

        }

        @Override
        public int getItemCount() {


            return youtubeItem.size();
        }


        class myHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title;
            // Bundle newsChoice = new Bundle();

            public myHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.SingleImage);
                title = (TextView) itemView.findViewById(R.id.SingleTitle);
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(context, Youtube_Comment.class);
                        String id = RelatedVideoList.get(getAdapterPosition()).getId();
                        String title = RelatedVideoList.get(getAdapterPosition()).getTitle();
                        //  Toast.makeText(, "" + data, Toast.LENGTH_SHORT).show();

                        intent.putExtra("video id", id);
                        intent.putExtra("title", title);


                        context.startActivity(intent);
                    }
                });
            }
        }


    }
}

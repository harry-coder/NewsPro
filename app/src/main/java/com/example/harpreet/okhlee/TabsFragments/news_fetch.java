package com.example.harpreet.okhlee.TabsFragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.pojo.NewsItems;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class news_fetch extends Fragment {

    RecyclerView newsRecycleList;

    private ArrayList<NewsItems> jsonItemsList = new ArrayList<>();

    private MyAdapter newsAdapter;

    vollySingleton vollysingleton;
    RequestQueue request;

    String passedCategory;

    private String fetchedNewsUrl, fetchedTitle, fetchedImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fetch,container,false);


      //  savedInstanceState = getActivity().getIntent().getExtras();

        Intent intent=getActivity().getIntent();
        if(intent.getExtras()!=null)
        {
            passedCategory=intent.getStringExtra("categoryItem");
        }

        //Taking the volly instance here....
        vollysingleton = vollySingleton.getInstance();
        request = vollysingleton.getRequestQueue();

        getJsonData(passedCategory);

        newsAdapter = new MyAdapter(getActivity());
        newsRecycleList = (RecyclerView) view.findViewById(R.id.recycler);


        newsRecycleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsRecycleList.setAdapter(newsAdapter);


        return view;
    }

/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.news_list);

        //Declaring the toolbar
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        //getting the category item from category class.....

        savedInstanceState = getActivity().getIntent().getExtras();

        //Taking the volly instance here....
        vollysingleton = vollySingleton.getInstance();
        request = vollysingleton.getRequestQueue();

        getJsonData(savedInstanceState);

        newsAdapter = new MyAdapter(getActivity());
        newsRecycleList = (RecyclerView) findViewById(R.id.recycler);


        newsRecycleList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        newsRecycleList.setAdapter(newsAdapter);


        // newsRecycleList.setAdapter(new AlphaInAnimationAdapter(newsAdapter));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
*/


    //This method will return the json data
    public void getJsonData(String categoryName) {
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, MyApplication.url(categoryName),

                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response == null && response.length() == 0) {
                    return;
                } else {

                    jsonItemsList = processRequest(response);
                    Toast.makeText(getActivity(), "" + jsonItemsList.size(), Toast.LENGTH_SHORT).show();
                    newsAdapter.setSource(jsonItemsList);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "This is sad!! No Data Or No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        request.add(jsonRequest);

    }

    //this will process the json request and response
    public ArrayList<NewsItems> processRequest(JSONObject response) {
        ArrayList<NewsItems> demoList = new ArrayList<>();

        if (response == null && response.length() == 0) {

            return null;
        } else {

            try {


                JSONArray newsArray = response.getJSONArray("posts");
                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject currentNewsItem = newsArray.getJSONObject(i);


                    JSONObject newsObject = currentNewsItem.getJSONObject("thread");

                    fetchedNewsUrl = newsObject.getString(jsonUrl);
                    fetchedTitle = newsObject.getString(jsonTitle);



                    if (newsObject.has(jsonImage)) {

                        fetchedImage = newsObject.getString(jsonImage);

                    } else {
                        fetchedImage = "";

                    }



                    //YOU HAVE TO UNCOMMENT THIS.....
                    NewsItems items = new NewsItems();
                    items.setUrl(fetchedNewsUrl);
                    items.setMain_image(fetchedImage);
                    items.setTitle_full(fetchedTitle);


                    demoList.add(items);


                }
            //    Toast.makeText(this, "" + fetchedImage, Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //  Toast.makeText(NewsList.this, "Fetching is done!! " + jsonItemsList.size(), Toast.LENGTH_SHORT).show();


        }
        return demoList;
    }


    public void displayImage(String ImageUrl, myHolder holder) {
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.GRAY)
                .borderWidthDp(2).cornerRadiusDp(30).oval(false).build();

        Picasso.with(getActivity()).load(ImageUrl).error(R.drawable.place)
                .placeholder(R.drawable.place).resize(250,250).centerCrop().transform(transformation).into(holder.image);

    }


    class MyAdapter extends RecyclerView.Adapter<myHolder> {
        Context context;
        ArrayList<NewsItems> newsItems = new ArrayList<>();
        LayoutInflater inflater;

        public void setSource(ArrayList<NewsItems> list) {
            this.newsItems = list;
            //  System.out.println(newsItems.size());
            notifyItemChanged(0, list.size());
        }

        MyAdapter(Context context) {
            this.context = context;

            //  System.out.println(newsItems.size());

        }

        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.singlelistitem, parent, false);


            myHolder holder = new myHolder(view);


            return holder;
        }


        @Override
        public void onBindViewHolder(final myHolder holder, int position) {

            NewsItems item = newsItems.get(position);


            //Setting the title
            String data = item.getTitle_full();
            if (data.length() != 0) {
                holder.title.setText(item.getTitle_full());
            }

            //Setting the image using ImageLoader
            if (item.getMain_image() != null &&item.getMain_image().length()!=0) {
                //  System.out.println(item.getMain_image());

                displayImage(item.getMain_image(),holder);

            }


        }


        @Override
        public int getItemCount() {

            return newsItems.size();
        }


    }

    class myHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        Bundle newsChoice = new Bundle();

        public myHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.SingleImage);
            title = (TextView) itemView.findViewById(R.id.SingleTitle);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                  /*  Intent intent = new Intent(NewsList.this, news_web.class);
                    String data = jsonItemsList.get(getAdapterPosition()).getUrl();
                    Toast.makeText(NewsList.this, "" + data, Toast.LENGTH_SHORT).show();

                    newsChoice.putString("NewTitle", data);

                    intent.putExtras(newsChoice);
                    startActivity(intent);*/
                }
            });
            title.setTextColor(getResources().getColor(R.color.colorPrimaryText));
        }
    }
}

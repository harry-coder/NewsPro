package com.example.harpreet.okhlee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.harpreet.okhlee.paginator.Paginator;
import com.example.harpreet.okhlee.pojo.NewsItems;
import com.example.harpreet.okhlee.volly.MyApplication;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

import static com.example.harpreet.okhlee.pojo.categoryNames.jsonImage;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonTitle;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonUrl;


public class NewsList extends AppCompatActivity {


    //RecyclerView newsRecycleList;


    PullToLoadView pullLoad;
    private ArrayList<NewsItems> jsonItemsList = new ArrayList<>();

    private MyAdapter newsAdapter;

    vollySingleton vollysingleton;
    RequestQueue request;

    String categoryName;

    // private String fetchedNewsUrl, fetchedTitle, fetchedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        //Declaring the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Latest News");
        //Slidr.attach(this);
        Slidr.attach(this, Color.WHITE, Color.WHITE);

        //getting the category item from category class.....

        savedInstanceState = getIntent().getExtras();
        categoryName = savedInstanceState.getString("categoryItem");


        //Taking the volly instance here....
        vollysingleton = vollySingleton.getInstance();
        request = vollysingleton.getRequestQueue();

        pullLoad = (PullToLoadView) findViewById(R.id.pullToLoadView);
        new Paginator(getApplicationContext(), pullLoad, categoryName);

        /*getJsonData(categoryName);

        newsAdapter = new MyAdapter(getApplicationContext());
        newsRecycleList = (RecyclerView) findViewById(R.id.recycler);


        newsRecycleList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        newsRecycleList.setAdapter(newsAdapter);
*/

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


    //This method will return the json data
    /*public void getJsonData(String category) {
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, MyApplication.url(category),

                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response == null && response.length() == 0) {
                    return;
                } else {

                    jsonItemsList = processRequest(response);
                    Toast.makeText(NewsList.this, "" + jsonItemsList.size(), Toast.LENGTH_SHORT).show();
                    newsAdapter.setSource(jsonItemsList);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(NewsList.this, "This is sad!! No Data Or No Internet Connection", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "" + fetchedImage, Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //  Toast.makeText(NewsList.this, "Fetching is done!! " + jsonItemsList.size(), Toast.LENGTH_SHORT).show();


        }
        return demoList;
    }*/


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myHolder> {
        Context context;
        public ArrayList<NewsItems> newsItems;
        LayoutInflater inflater;
        CopyOnWriteArrayList<String> titleList = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> urlList = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> imageList = new CopyOnWriteArrayList<>();

        /*public void setSource(ArrayList<NewsItems> list) {
            this.newsItems = list;
            //  System.out.println(newsItems.size());
            notifyItemChanged(0, list.size());
        }*/

        public MyAdapter(Context context, ArrayList<NewsItems> newsItemses) {
            this.context = context;

            this.newsItems = newsItemses;

            //  System.out.println(newsItems.size());

        }


        public void displayImage(String ImageUrl, MyAdapter.myHolder holder) {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.GRAY)
                    .borderWidthDp(2).cornerRadiusDp(30).oval(false).build();

            Picasso.with(context).load(ImageUrl).error(R.drawable.place)
                    .placeholder(R.drawable.place).resize(250, 250).centerCrop().transform(transformation).into(holder.image);

        }

        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.singlelistitem, parent, false);


            return new myHolder(view);
        }


        @Override
        public void onBindViewHolder(final myHolder holder, int position) {

            NewsItems item = newsItems.get(position);


            if (item.getMain_image() != null && item.getMain_image().length() != 0) {

                if(item.getTitle_full().length() != 0)
                {
                    holder.title.setText(item.getTitle_full());
                    displayImage(item.getMain_image(), holder);
                }

            }

            /*//Setting the title
            String data = item.getTitle_full();
            if (data.length() != 0) {
                holder.title.setText(item.getTitle_full());
            }


            // holder.title.setText(titleList.get(position));


            //Setting the image using ImageLoader
            if (item.getMain_image() != null && item.getMain_image().length() != 0) {
                //  System.out.println(item.getMain_image());

                displayImage(item.getMain_image(), holder);

            }
            *//*String data=imageList.get(position);
            if(data.length()!=0) {

                displayImage(data, holder);
            }*//*
*/
        }


        @Override
        public int getItemCount() {

            return newsItems.size();
        }

        public void addTitle(String title) {
            titleList.addIfAbsent(title);
        }

        public void addImage(String imageUrl) {
            imageList.addIfAbsent(imageUrl);
        }

        public void addUrl(String url) {
            urlList.addIfAbsent(url);
        }


        public void add(NewsItems item) {
            newsItems.add(item);
            jsonItemsList.add(item);
            notifyDataSetChanged();
        }

        public void clear() {
            newsItems.clear();
            notifyDataSetChanged();
        }


        public class myHolder extends RecyclerView.ViewHolder {
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


/*
                        Intent intent = new Intent(context, news_web.class);
                        String data = jsonItemsList.get(getAdapterPosition()).getUrl();
                        //  Toast.makeText(, "" + data, Toast.LENGTH_SHORT).show();

                        newsChoice.putString("NewTitle", data);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtras(newsChoice);
                        context.startActivity(intent);
*/
                        Intent intent = new Intent(context, News_Description.class);
                        NewsItems data = jsonItemsList.get(getAdapterPosition());
                        String url=data.getUrl();
                        String image=data.getMain_image();
                        String headline=data.getTitle_full();
                        String description=data.getBody();


                        newsChoice.putString("Title", headline);
                        newsChoice.putString("Image",image);
                        newsChoice.putString("Url",url);
                        newsChoice.putString("Description",description);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtras(newsChoice);
                        context.startActivity(intent);




                    }
                });
                title.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
            }
        }
    }

}

package com.example.harpreet.okhlee.paginator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harpreet.okhlee.NewsList;
import com.example.harpreet.okhlee.pojo.NewsItems;
import com.example.harpreet.okhlee.volly.MyApplication;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.harpreet.okhlee.NewsList.MyAdapter;

import static android.content.Intent.getIntent;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonImage;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonTitle;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonUrl;
import static com.example.harpreet.okhlee.volly.MyApplication.getCategoryWiseNews;
import static com.example.harpreet.okhlee.volly.MyApplication.getLoadMoreCategoryWiseNews;

/**
 * Created by Harpreet on 25/04/2017.
 */

public class Paginator {


    Context c;
    vollySingleton singleton;
    RequestQueue request;
    NewsList list = new NewsList();
    private String url;
    int count = 0;
    private String categoryName;

/*
    CopyOnWriteArrayList<String> titleL=new CopyOnWriteArrayList();
    CopyOnWriteArrayList<String> imageL=new CopyOnWriteArrayList();
    CopyOnWriteArrayList<String> urlL=new CopyOnWriteArrayList();
*/

    Handler handler = new Handler();
    private String fetchedTitle;
    private String fetchedNewsUrl, fetchedImage;
    boolean isFirstTime;
    private PullToLoadView pullToLoadView;
    private MyAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    ArrayList<String> next = new ArrayList<>();

    public Paginator(Context c, PullToLoadView pullToLoadView, String categoryName) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        this.categoryName = categoryName;
        singleton = vollySingleton.getInstance();
        request = singleton.getRequestQueue();


        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
        adapter = list.new MyAdapter(c, new ArrayList<NewsItems>());
        rv.setAdapter(adapter);
        initializePaginator();
        //  System.out.println("about to start");


    }


    /*
        PAGE DATA
         */
    public void initializePaginator() {
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            //LOAD MORE DATA
            @Override
            public void onLoadMore() {

                System.out.println("inside onLoadMore ");


                //  onRefresh();
            //    getCategoryWiseNews(categoryName);
                getJsonData(categoryName);

            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                //  loadData(1);
                getJsonData(categoryName);
//                getCategoryWiseNews(categoryName);
                System.out.println("Inside on refresh");
            }

            //IS LOADING
            @Override
            public boolean isLoading() {

                System.out.println("Inside on loading " + isLoading);
                return isLoading;

            }

            //CURRENT PAGE LOADED
            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }


    public void getJsonData(String category) {
        if (next.size() == 0) {
//            Toast.makeText(this, "Inside first time", Toast.LENGTH_SHORT).show();
            System.out.println("When Arraylist Size is 0");
            url = getCategoryWiseNews(category.toLowerCase());
            System.out.println("This is url "+url);
        } else {
            //if(!isLoading)

            // {
            try {
             //   url = "https://webhose.io" + next.get(count);
             url=getLoadMoreCategoryWiseNews(category.toLowerCase(),next.get(count));
                count++;  //you can  also clear the arraylist instead of incrementing....
            } catch (IndexOutOfBoundsException excetion) {


            }


            // }

            //  Toast.makeText(this, "Inside Second Time", Toast.LENGTH_SHORT).show();
            System.out.println("when ArrayList Size is not zero");


            System.out.println("Value of count after increment " + count);

        }

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, url,

                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response == null && response.length() == 0) {
                    return;
                } else {
                    try {

                        Toast.makeText(c, "This is inside request", Toast.LENGTH_SHORT).show();
                        processRequest(response);



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


    public void processRequest(JSONObject response) {


        if (response == null && response.length() == 0) {

            return;
        } else {

            try {
                JSONObject inShortObject=response.getJSONObject("data");

                isLoading = true;

                String value = inShortObject.getString("id");
                System.out.println(value);
                next.add(value);
                // next.add(value);



                JSONArray bodyArray = inShortObject.getJSONArray("body");
                JSONArray imageArray = inShortObject.getJSONArray("image");
                JSONArray headlineArray = inShortObject.getJSONArray("headline");
                JSONArray readMoreArray = inShortObject.getJSONArray("read_more");




                for (int i = 0; i < bodyArray.length(); i++) {
                  /*  JSONObject currentBodyItem = bodyArray.getJSONObject(i);
                    JSONObject currentImageItem = imageArray.getJSONObject(i);
                    JSONObject currentHeadlineItem = headlineArray.getJSONObject(i);
*/
                  String bodyItem=bodyArray.getString(i);
                    String imageItem=imageArray.getString(i);
                    String headlineItem=headlineArray.getString(i);
                    String readMoreItem=readMoreArray.getString(i);


/*
                    JSONObject newsObject = currentNewsItem.getJSONObject("thread");

                    fetchedNewsUrl = newsObject.getString(jsonUrl);
                    fetchedTitle = newsObject.getString(jsonTitle);
*/


/*
                    if (newsObject.has(jsonImage)) {

                        fetchedImage = newsObject.getString(jsonImage);

                    } else {
                        fetchedImage = "";

                    }
*/


                    //YOU HAVE TO UNCOMMENT THIS.....


                    /*if (fetchedImage.length() != 0 && fetchedImage != null) {
                        if (search(fetchedTitle)) {
                            NewsItems items = new NewsItems();
                            items.setUrl(fetchedNewsUrl);
                            System.out.println(adapter.newsItems.size());
                            items.setMain_image(fetchedImage);
                            items.setTitle_full(fetchedTitle);

                            adapter.add(items);
                        }
                    }*/
                    NewsItems items = new NewsItems();
                    items.setUrl(readMoreItem);
                    System.out.println(adapter.newsItems.size());
                    items.setMain_image(imageItem);
                    items.setTitle_full(headlineItem);
                    items.setBody(bodyItem);


                    adapter.add(items);


                }
                pullToLoadView.setComplete();
                isLoading = false;
                //  Toast.makeText(this, "" + fetchedImage, Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //  Toast.makeText(NewsList.this, "Fetching is done!! " + jsonItemsList.size(), Toast.LENGTH_SHORT).show();


        }

    }

    public boolean search(String title) {


        for (int i = 0; i < adapter.newsItems.size(); i++) {
            if (adapter.newsItems.get(i).getTitle_full().equals(title)) {
                return false;

            }
        }
        return true;
    }

}
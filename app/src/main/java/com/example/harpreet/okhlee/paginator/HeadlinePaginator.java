package com.example.harpreet.okhlee.paginator;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harpreet.okhlee.NewsList;
import com.example.harpreet.okhlee.TabsFragments.headlines;
import com.example.harpreet.okhlee.pojo.NewsItems;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.harpreet.okhlee.pojo.categoryNames.jsonImage;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonTitle;
import static com.example.harpreet.okhlee.pojo.categoryNames.jsonUrl;
import static com.example.harpreet.okhlee.volly.MyApplication.headlinesUrl;

public class HeadlinePaginator {

    Context c;
    vollySingleton singleton;
    RequestQueue requestQueue;
    headlines headNews=new headlines();
    private String headlineUrl;
    int count = 0;

    private String headlineFetchedTitle;
    private String headlineFetchedUrl, headlineFetchedImage;

    private PullToLoadView pullToLoadView;
    private headlines.myAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    ArrayList<String> next = new ArrayList<>();

    public HeadlinePaginator(Context c, PullToLoadView pullToLoadView) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;

        singleton = vollySingleton.getInstance();
        requestQueue = singleton.getRequestQueue();


        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
        adapter = headNews.new myAdapter(c, new ArrayList<NewsItems>());
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
                System.out.println("Inside onLoadMore");

                getJsonHeadlines();

                //  onRefresh();
                // getJsonData(categoryName);

            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                //  loadData(1);

                System.out.println("Inside onRefresh");
                // getJsonData(categoryName);
                getJsonHeadlines();

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

    public void getJsonHeadlines() {
        if (next.size() == 0) {
//            Toast.makeText(this, "Inside first time", Toast.LENGTH_SHORT).show();
            System.out.println("When Arraylist Size is 0");
            headlineUrl = headlinesUrl();
            System.out.println(headlineUrl);
        }
        else {
            try {
                headlineUrl = "https://webhose.io" + next.get(count);
                count++;
            } catch (IndexOutOfBoundsException excetion) {


                System.out.println(excetion);
            }
        }


        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.GET, headlineUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response == null && response.length() == 0) {

                    return;
                } else {
                    try {
                       // System.out.println("Inside on-response");
                        requestData(response);
                        //adapter.setJsonSource(jsonHeadlineList);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });




        //request.setShouldCache(false);
        requestQueue.add(request);
        //  request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }




    public void requestData(JSONObject response) throws JSONException {

        try {
            if (response == null && response.length() == 0) {
                return;
            } else {

                isLoading = true;
                String value = response.getString("next");
                //   System.out.println(value);
                next.add(value);

                JSONArray newsArray = response.getJSONArray("posts");

                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject currentNewsItem = newsArray.getJSONObject(i);


                    JSONObject newsObject = currentNewsItem.getJSONObject("thread");

                    headlineFetchedUrl = newsObject.getString(jsonUrl);
                    headlineFetchedTitle = newsObject.getString(jsonTitle);
                   // System.out.println(headlineFetchedTitle);


                    if (newsObject.has(jsonImage)) {

                        headlineFetchedImage = newsObject.getString(jsonImage);
                        System.out.println(headlineFetchedImage);


                    } else {
                        headlineFetchedImage = "";

                    }
                    NewsItems items = new NewsItems();
                    if(headlineFetchedImage.length()!=0 && headlineFetchedImage!=null) {

                        items.setMain_image(headlineFetchedImage);
                        items.setUrl(headlineFetchedUrl);
                        items.setTitle_full(headlineFetchedTitle);
                        adapter.add(items);
                    }






                }
                pullToLoadView.setComplete();
                isLoading = false;

            }

        } catch (Exception exception) {

        }
    }
}

package com.example.harpreet.okhlee.paginator;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harpreet.okhlee.MainActivity;
import com.example.harpreet.okhlee.NewsList;
import com.example.harpreet.okhlee.TabsFragments.trending;
import com.example.harpreet.okhlee.pojo.NewsItems;
import com.example.harpreet.okhlee.pojo.youtubeItems;
import com.example.harpreet.okhlee.volly.MyApplication;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.harpreet.okhlee.pojo.youtubeItemNames.nextPageToken;
import static com.example.harpreet.okhlee.pojo.youtubeItemNames.yId;
import static com.example.harpreet.okhlee.pojo.youtubeItemNames.yTitile;

/**
 * Created by Harpreet on 27/04/2017.
 */

public class YoutubePaginator {
    Context c;

    private String url;
    int count = 0;
    private String categoryName;
    private vollySingleton volly;
    private RequestQueue yRequest;
    trending trend = new trending();


    private String fetchedYoutubeId, fetchedYoutubeTitle, fetchedNextPageToken;
    boolean isFirstTime;
    MainActivity activity;
    private PullToLoadView pullToLoadView;
    private trending.myAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    ArrayList<String> next = new ArrayList<>();
    private BottomSheetLayout bottomSheetLayout;

    public YoutubePaginator(Context c, PullToLoadView pullToLoadView, BottomSheetLayout bottomSheetLayout, MainActivity activity) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        this.bottomSheetLayout = bottomSheetLayout;
        volly = vollySingleton.getInstance();
        yRequest = volly.getRequestQueue();

        this.activity=activity;

        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
        adapter = trend.new myAdapter(c, new ArrayList<youtubeItems>(), bottomSheetLayout, activity);
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
                getYoutubeJsonData();

            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                //  loadData(1);
                getYoutubeJsonData();
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

    public void getYoutubeJsonData() {

        if (next.size() == 0) {
            url = MyApplication.getYoutubeUrl();
        } else {
            try {
                url = MyApplication.getYoutubeUrl() + "&" + next.get(count);
                count++;
            } catch (IndexOutOfBoundsException excetion) {


            }
        }

        JsonObjectRequest youtubeRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            processingRequest(response);
                        /*    Toast.makeText(getActivity(), "wen passing list size " + youtubeJsonDataList.size(), Toast.LENGTH_SHORT).show();
                            adapter.getYoutubeDataList(youtubeJsonDataList);
*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(c, "No or poor Internet Connection!!  ", Toast.LENGTH_SHORT).show();

            }
        });

        yRequest.add(youtubeRequest);


    }


    public void processingRequest(JSONObject response) throws JSONException {


        if (response == null && response.length() == 0) {
            return;
        } else {
            String nextPage = response.getString(nextPageToken);
            next.add(nextPage);


            JSONArray youtubeLinks = response.getJSONArray("items");
            isLoading = true;
            for (int i = 0; i < youtubeLinks.length(); i++) {
                JSONObject singleItem = youtubeLinks.getJSONObject(i);
                fetchedYoutubeId = singleItem.getString(yId);


                JSONObject retrieveTitle = singleItem.getJSONObject("snippet");
                fetchedYoutubeTitle = retrieveTitle.getString(yTitile);

                youtubeItems yitems = new youtubeItems();

                yitems.setId(fetchedYoutubeId);
                yitems.setTitle(fetchedYoutubeTitle);

                adapter.add(yitems);

            }
            pullToLoadView.setComplete();
            isLoading = false;

        }


    }
}

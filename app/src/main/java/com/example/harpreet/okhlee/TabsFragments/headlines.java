package com.example.harpreet.okhlee.TabsFragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.paginator.HeadlinePaginator;
import com.example.harpreet.okhlee.pojo.NewsItems;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class headlines extends Fragment {

   // RecyclerView hRecycler;

    ArrayList<NewsItems> jsonHeadlineList = new ArrayList<>();
   // myAdapter adapter;

    vollySingleton volly;
    RequestQueue requestQueue;

    private PullToLoadView pullToLoadView;
    public headlines() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volly=vollySingleton.getInstance();
        requestQueue=volly.getRequestQueue();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.headlines, container, false);

        pullToLoadView= (PullToLoadView) view.findViewById(R.id.pullToLoadView);

        new HeadlinePaginator(getActivity(),pullToLoadView);
        return view;
    }


    /*public void getJsonHeadlines() {
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.GET, headlinesUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response != null && response.length() != 0) {
                    try {
                        jsonHeadlineList = requestData(response);
                        adapter.setJsonSource(jsonHeadlineList);


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

        requestQueue.add(request);

    }

    public ArrayList<NewsItems> requestData(JSONObject response) throws JSONException {

        ArrayList<NewsItems> demoData = new ArrayList<>();
        JSONArray newsArray = response.getJSONArray("posts");
        for (int i = 0; i < newsArray.length(); i++) {
            JSONObject currentNewsItem = newsArray.getJSONObject(i);


            JSONObject newsObject = currentNewsItem.getJSONObject("thread");

            headlineFetchedUrl = newsObject.getString(jsonUrl);
            headlineFetchedTitle = newsObject.getString(jsonTitle);


            if (newsObject.has(jsonImage)) {

                headlineFetchedImage = newsObject.getString(jsonImage);

            } else {
                headlineFetchedImage = "";

            }
            NewsItems items = new NewsItems();
            items.setUrl(headlineFetchedUrl);
            items.setMain_image(headlineFetchedImage);
            items.setTitle_full(headlineFetchedTitle);

            demoData.add(items);


        }
        return demoData;
    }
*/



    public void animateView(headlinesHolder holder)
    {
        YoYo.with(Techniques.FadeInDown)
                .duration(700).playOn(holder.itemView);


    }

    public class myAdapter extends RecyclerView.Adapter<headlinesHolder> {
        Context context;
        LayoutInflater inflater;
        CopyOnWriteArrayList<NewsItems> duplicateArray=new CopyOnWriteArrayList<>();

        ArrayList<NewsItems> headlineItem;

        public myAdapter(Context context, ArrayList<NewsItems> headlineItem) {


            this.context = context;
            this.headlineItem=headlineItem;
            inflater= LayoutInflater.from(context);
        }
        public void displayImage(String imageUrl, headlinesHolder holder) {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.GRAY)
                    .borderWidthDp(2)
                    .cornerRadiusDp(50)
                    .oval(false)
                    .build();

            Picasso.with(context).load(imageUrl).error(R.drawable.place)
                    .placeholder(R.drawable.place).resize(250,250).centerCrop().transform(transformation).into(holder.image);

        }
        public void displayImage(int res, headlinesHolder holder) {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.GRAY)
                    .borderWidthDp(2)
                    .cornerRadiusDp(50)
                    .oval(false)
                    .build();

            Picasso.with(context).load(res).error(R.drawable.place)
                    .placeholder(R.drawable.place).resize(250,250).centerCrop().transform(transformation).into(holder.image);

        }

        @Override
        public headlinesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.singlelistitem, parent, false);
            headlinesHolder holder = new headlinesHolder(view);


            return holder;
        }

    /*    public void setJsonSource(ArrayList<NewsItems> items) {
            this.headlineData = items;
            notifyItemChanged(0, headlineData.size());
        }
*/
        @Override
        public void onBindViewHolder(headlinesHolder holder, int position) {
            NewsItems items = headlineItem.get(position);


                //their you have had made changes
             if (items.getMain_image() != null && items.getMain_image().length() != 0) {

                 if(items.getTitle_full().length() != 0)
                 {
                     holder.title.setText(items.getTitle_full());
                     displayImage(items.getMain_image(), holder);
                 }

            }


            animateView(holder);


        }

        public void add(NewsItems item)
        {

            headlineItem.add(item);
            jsonHeadlineList.add(item);
            notifyDataSetChanged();
        }
        public void clear()
        {
            headlineItem.clear();
            jsonHeadlineList.clear();
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return headlineItem.size();
        }
    }

    class headlinesHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public headlinesHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.SingleImage);
            title = (TextView) itemView.findViewById(R.id.SingleTitle);
        }
    }

}

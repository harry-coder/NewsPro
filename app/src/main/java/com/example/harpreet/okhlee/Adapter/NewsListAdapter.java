/*
package com.example.harpreet.okhlee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harpreet.okhlee.NewsList;
import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.news_web;
import com.example.harpreet.okhlee.pojo.NewsItems;

import java.util.ArrayList;




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


                Intent intent = new Intent(NewsList.this, news_web.class);
                String data = jsonItemsList.get(getAdapterPosition()).getUrl();
                Toast.makeText(NewsList.this, "" + data, Toast.LENGTH_SHORT).show();

                newsChoice.putString("NewTitle", data);

                intent.putExtras(newsChoice);
                startActivity(intent);
            }
        });
        title.setTextColor(getResources().getColor(R.color.colorPrimaryText));
    }
*/

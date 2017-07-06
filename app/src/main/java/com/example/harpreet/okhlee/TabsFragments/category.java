package com.example.harpreet.okhlee.TabsFragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harpreet.okhlee.NewsList;
import com.example.harpreet.okhlee.R;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class category extends Fragment  {


    ArrayList<String> listData;
    myRecycleAdapter adapter;


    final String newsOptions[] = {"National", "Business", "Sports", "Politics", "World", "Technology", "Startup", "Entertainment",
            "Miscellaneous", "Hatke","Science","Automobile"};

    public category() {
        // Required empty public constructor

    }


    public ArrayList<String> getList() {
        listData = new ArrayList<>();
        Collections.addAll(listData, newsOptions);
        return listData;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState1) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category, container, false);

        RecyclerView recycle = (RecyclerView) view.findViewById(R.id.recycle);

        adapter = new myRecycleAdapter(getActivity(), getList());


        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));

       recycle.setAdapter(adapter);
       /* recycle.addOnItemTouchListener(new RecycleViewItemListener(getActivity(), recycle, new ClickListener() {
            @Override
            public void OnSingleClickListener(View v, int position) {

                Intent intent = new Intent(getActivity(), NewsList.class);
                //    intent.putExtra("CategoryItem",listData.get(position));
                Bundle savedInstanceState = new Bundle();


                savedInstanceState.putString("categoryItem", listData.get(position));

                intent.putExtras(savedInstanceState);

                startActivity(intent);

                //set the animation  v.animate();
            }

            @Override
            public void OnLongClickListener(View v, int position) {

            }
        }));
*/
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(CreateItemTouch());
        itemTouchHelper.attachToRecyclerView(recycle);
        return view;
    }

    private ItemTouchHelper.Callback CreateItemTouch() {

        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,

                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                return;
                //listData.remove(viewHolder.getAdapterPosition());
                //adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        };

    }

    public void moveItem(int oldPos, int newPos) {

        String item = listData.get(oldPos);
        listData.remove(oldPos);
        listData.add(newPos, item);
        adapter.notifyItemMoved(oldPos, newPos);


    }




    class myRecycleAdapter extends RecyclerView.Adapter<myHolder> {
        LayoutInflater inflater;
        Context context;
        ArrayList<String> itemList;
        // List<information> data = Collections.emptyList();

        public myRecycleAdapter(Context context, ArrayList<String> list) {
            inflater = LayoutInflater.from(context);
            this.itemList = list;
            this.context = context;
        }

        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.singleheading, parent, false);



            return new myHolder(view, context);
        }

        @Override
        public void onBindViewHolder(myHolder holder, int position) {


            holder.heading.setText(itemList.get(position));



        }

        @Override
        public int getItemCount() {
            //  Toast.makeText(context, data.size(), Toast.LENGTH_SHORT).show();
//        System.out.println(data.size());
            return newsOptions.length;
        }
    }

    class myHolder extends RecyclerView.ViewHolder  {
        Switch option;
        TextView heading;

        public myHolder(View itemView, final Context context) {
            super(itemView);
            option = (Switch) itemView.findViewById(R.id.option);
            heading = (TextView) itemView.findViewById(R.id.heading);

            // heading.setOnClickListener( this);

            if(!option.isChecked())
            {
                heading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, NewsList.class);
                            intent.putExtra("CategoryItem",listData.get(getAdapterPosition()));

                        Bundle savedInstanceState = new Bundle();



//                        Fragment newsFetch= new news_fetch();
                        savedInstanceState.putString("categoryItem", listData.get(getAdapterPosition()));
  ///                      newsFetch.setArguments(savedInstanceState);

     //                   getFragmentManager().beginTransaction().replace(R.id.container,newsFetch).commit();


                       // Toast.makeText(context, "You Clicked "+listData.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();


                       intent.putExtras(savedInstanceState);

                        startActivity(intent);

                    }
                });
            }

            option.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                    if (b) {
                        heading.setEnabled(false);

                    } else {
                        heading.setEnabled(true);


                    }

                }
            });
        }


    }


    //creating custom OnItemClickListener for recycle View
    /*static class RecycleViewItemListener implements RecyclerView.OnItemTouchListener {


        Context context;
        GestureDetector gestireDetector;

        RecycleViewItemListener(final Context context, final RecyclerView recycle, final ClickListener clickListner) {
            gestireDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View child = recycle.findChildViewUnder(e.getX(), e.getY());

                    clickListner.OnSingleClickListener(child, recycle.getChildAdapterPosition(child));


                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                }
            }


            );


        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            gestireDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public interface ClickListener {

        void OnSingleClickListener(View v, int position);

        void OnLongClickListener(View v, int position);
    }*/
}

package com.example.harpreet.okhlee.youtube;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.harpreet.okhlee.MainActivity;
import com.example.harpreet.okhlee.R;
import com.example.harpreet.okhlee.Youtube_Comment;
import com.example.harpreet.okhlee.pojo.youtubeComment;
import com.example.harpreet.okhlee.pojo.youtubeItems;
import com.example.harpreet.okhlee.volly.MyApplication;
import com.example.harpreet.okhlee.volly.vollySingleton;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link comments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class comments extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Handler handler = new Handler();
    private String androidId;
    private EditText userComment;
    private AHBottomNavigation bottomNavigation;

    public static ArrayList<youtubeComment> jsonYoutubeComment = new ArrayList<>();

    private RecyclerView commentRecycler;
    private vollySingleton singleton;
    private RequestQueue request;
    private Button fire;
    private EditText commentByUser;
    Youtube_Comment activity;
    DialogPlus dialog;
    private BottomSheetLayout bottomSheet;


    MyCommentAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public comments() {
        // Required empty public constructor


        singleton = vollySingleton.getInstance();
        request = singleton.getRequestQueue();


    }


    // TODO: Rename and change types and number of parameters
    public static comments newInstance(String param1, String param2) {
        comments fragment = new comments();
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

     /*   singleton = vollySingleton.getInstance();
        request = singleton.getRequestQueue();
     */

        sendAndroidId();
        sendVideoId(mParam1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.comments, container, false);

        bottomNavigation = (AHBottomNavigation) getActivity().findViewById(R.id.bottom_navigation);
        bottomSheet = (BottomSheetLayout) getActivity().findViewById(R.id.bottomsheet);

        commentRecycler = (RecyclerView) view.findViewById(R.id.commentRecycle);

        commentRecycler.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())


                        .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                        .build());


        adapter = new MyCommentAdapter(getActivity());
        commentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentRecycler.setAdapter(adapter);
        bottomNavigationBar();

        //  dialog=DialogPlus.newDialog(container);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Youtube_Comment) activity;
    }


    public void getSingleComment(JSONObject response) {
        String holderName, holderComment;
        youtubeComment commentsItem = null;
        try {

            if (response != null && response.length() != 0) {

                JSONArray commentArray = response.getJSONArray("comments");


                for (int i = 0; i < commentArray.length(); i++) {

                    JSONObject currentVideoItem = commentArray.getJSONObject(i);


                    holderName = currentVideoItem.getString("name");
                    holderComment = currentVideoItem.getString("comment");


                    //YOU HAVE TO UNCOMMENT THIS.....
                    commentsItem = new youtubeComment();
                    commentsItem.setName(holderName);
                    commentsItem.setComment(holderComment);


                }
                adapter.add(commentsItem);
            }


        } catch (Exception e) {

            System.out.println("Error pooped sorry " + e);
//            Toast.makeText(getActivity(), "Error pooped sorry " + e, Toast.LENGTH_SHORT).show();
        }


    }


    public void publishComment(final String userComment, final String videoId, final String androidDeviceId) {

        handler.post(new Runnable() {
            @Override
            public void run() {

                JsonObjectRequest commentRequest = new JsonObjectRequest(JsonObjectRequest.Method.POST, "http://capp-api.okhlee.com/api/video/postComment", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        getSingleComment(response);


                        //  Toast.makeText(MyApplication.getContext(),  response.toString(), Toast.LENGTH_SHORT).show();


                        //System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(getActivity(), "Please check " + error, Toast.LENGTH_SHORT).show();
                        System.out.println("Please check " + error);
                    }
                }) {


                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("videoid", videoId);
                        params.put("deviceid", androidDeviceId);
                        params.put("comment", userComment);


  /*                      Toast.makeText(getActivity(), ""+mParam1, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), ""+androidId, Toast.LENGTH_SHORT).show();
  */
                        return params;
                    }
                };
                request.add(commentRequest);
                //    sendVideoId(mParam1);
                //  adapter.notifyAdapter();


            }
        });


    }

    public void bottomNavigationBar() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Add Comment", R.drawable.comment);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Share", R.drawable.share);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.redheart);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setAccentColor(Color.parseColor("#075e54"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(2);

        createNotification(String.valueOf(comments.jsonYoutubeComment.size()));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(final int position, boolean wasSelected) {


                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (position == 0) {

                            androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

                            //bottomSheet.showWithSheetView(LayoutInflater.from(Youtube_Comment.this).inflate(R.layout.comment_box, bottomSheet, false));
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Youtube_Comment.INPUT_METHOD_SERVICE);
                            //new comments().showview(Youtube_Comment.this);


                            dialog = DialogPlus.newDialog(getActivity())
                                    .setContentHolder(new ViewHolder(R.layout.comment_box)).setGravity(Gravity.TOP)
                                    .setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(DialogPlus dialog, View view) {
                                            String data = null;
                                         /*   userComment.requestFocus();

                                            if(userComment.requestFocus()) {
                                                Toast.makeText(getActivity(), "Inside focus", Toast.LENGTH_SHORT).show();
                                                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                            }
*/
                                            if (view.getId() == R.id.commentButton)


                                                Toast.makeText(getActivity(), "Inside on button click", Toast.LENGTH_SHORT).show();
                                            userComment = (EditText) getActivity().findViewById(R.id.commentBox);

                                            data = userComment.getText().toString();


                                            publishComment(data, mParam1, androidId);


                                            dialog.dismiss();
                                            imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);

                                        }
                                    }).setPadding(20, 20, 20, 20).setCancelable(false).setMargin(20, 20, 20, 20).setOnBackPressListener(new OnBackPressListener() {
                                        @Override
                                        public void onBackPressed(DialogPlus dialogPlus) {


                                            dialogPlus.dismiss();


                                        }
                                    }).setCancelable(true)
                                    // This will enable the expand feature, (similar to android L share dialog)
                                    .create();
                            dialog.show();
                            createDialog(dialog);


                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                        } else if (position == 1) {

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                            shareIntent.putExtra(Intent.EXTRA_TEXT, mParam1);
                            // startActivity(Intent.createChooser(shareIntent,"Share Via.."));


                            setShare(shareIntent);
                        }
                    }
                });

                return true;
            }
        });

    }

    public void createDialog(DialogPlus dialog) {

        View view = dialog.getHolderView();
        userComment = (EditText) view.findViewById(R.id.commentBox);
        if (userComment.requestFocus()) {
            Toast.makeText(getActivity(), "Inside focus", Toast.LENGTH_SHORT).show();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }


    }


    public void createNotification(final String totalComment) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText(totalComment)
                        .setBackgroundColor(Color.parseColor("#09d261"))
                        .setTextColor(Color.BLACK)
                        .build();
                // Adding notification to last item.

                bottomNavigation.setNotification(notification, 0);


            }
        });
    }

    public void setShare(final Intent shareIntent) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(getActivity(), shareIntent, "Share with...", new IntentPickerSheetView.OnIntentPickedListener() {
                    @Override
                    public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                        bottomSheet.dismissSheet();
                        startActivity(activityInfo.getConcreteIntent(shareIntent));

                    }
                });
                bottomSheet.showWithSheetView(intentPickerSheet);
            }
        });


    }


    public void sendAndroidId() {

        androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        JsonObjectRequest commentRequest = new JsonObjectRequest(JsonObjectRequest.Method.POST, "http://capp-api.okhlee.com/api/user/update", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Please check " + error, Toast.LENGTH_SHORT).show();
                System.out.println("Please check " + error);
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("deviceid", androidId);
                return params;
            }
        };
        request.add(commentRequest);
    }


    public void sendVideoId(final String videoId) {

        JsonObjectRequest commentRequest = new JsonObjectRequest(JsonObjectRequest.Method.POST, "http://capp-api.okhlee.com/api/video/getComment", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Toast.makeText(getActivity(), "Request Has been send....", Toast.LENGTH_SHORT).show();


                jsonYoutubeComment = processRequest(response);
                adapter.setSource(jsonYoutubeComment);

                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();


                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Please check " + error, Toast.LENGTH_SHORT).show();

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("videoid", videoId);
                return params;
            }
        };
        request.add(commentRequest);


    }


    public ArrayList<youtubeComment> processRequest(JSONObject response) {

        String holderName, holderComment;
        ArrayList<youtubeComment> demo = new ArrayList<>();


        try {


            JSONArray commentArray = response.getJSONArray("comments");


            for (int i = 0; i < commentArray.length(); i++) {

                JSONObject currentVideoItem = commentArray.getJSONObject(i);


                holderName = currentVideoItem.getString("name");
                holderComment = currentVideoItem.getString("comment");


                //YOU HAVE TO UNCOMMENT THIS.....
                youtubeComment commentsItem = new youtubeComment();
                commentsItem.setName(holderName);
                commentsItem.setComment(holderComment);

                demo.add(commentsItem);


            }


        } catch (Exception e) {

            System.out.println("Error pooped sorry " + e);
//            Toast.makeText(getActivity(), "Error pooped sorry " + e, Toast.LENGTH_SHORT).show();
        }


        return demo;
    }


    class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.myHolder> {
        Context context;
        ArrayList<youtubeComment> youtubeCommentItem = new ArrayList<>();
        LayoutInflater inflater;


        public void setSource(ArrayList<youtubeComment> list) {
            this.youtubeCommentItem = list;

            notifyItemChanged(0, list.size());
        }

        public MyCommentAdapter(Context context) {
            this.context = context;


            inflater = LayoutInflater.from(context);


        }


        // displaying the image in circle shape
        public void displayImage(char c, myHolder holder) {

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
            int color1 = generator.getRandomColor();
// generate color based on a key (same key returns the same color), useful for list/grid views
            //  int color2 = generator.getColor("user@gmail.com");

// declare the builder object once.
           /* TextDrawable.IBuilder builder = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .rect();
*/
// reuse the builder specs to create multiple drawables
            /*TextDrawable ic1 = TextDrawable.builder.build(""+c, color1);*/
            //TextDrawable ic2 = builder.build("B", color2);
            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRound("" + c, color1); // radius in px

            holder.image.setImageDrawable(drawable1);
        }

        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = inflater.inflate(R.layout.single_comment, parent, false);

            myHolder holder = new myHolder(view);


            return holder;
        }

        @Override
        public void onBindViewHolder(final myHolder holder, final int position) {


            handler.post(new Runnable() {
                @Override
                public void run() {


                    youtubeComment comment = youtubeCommentItem.get(position);

                    String setComment = comment.getComment();


                    if (setComment.length() != 0) {
                        holder.name.setText(comment.getName());
                        displayImage(setComment.charAt(0), holder);
                        holder.comment.setText(setComment);
                    }


                }
            });

        }

        @Override
        public int getItemCount() {


            return youtubeCommentItem.size();

        }

        public void notifyAdapter() {
            notifyDataSetChanged();
        }

        public void add(youtubeComment commentItem) {
            youtubeCommentItem.add(commentItem);
            notifyDataSetChanged();

        }


        class myHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name, comment;
            // Bundle newsChoice = new Bundle();

            public myHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.AccountHolderImage);
                name = (TextView) itemView.findViewById(R.id.AccountHolderName);
                comment = (TextView) itemView.findViewById(R.id.AccountHolderComment);

            }
        }


    }
}

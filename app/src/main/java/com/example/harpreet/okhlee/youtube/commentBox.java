package com.example.harpreet.okhlee.youtube;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.harpreet.okhlee.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link commentBox#newInstance} factory method to
 * create an instance of this fragment.
 */
public class commentBox extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    comments comm=new comments();

    private Button fire;
    private EditText commentByUser;



    public commentBox() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment commentBox.
     */
    // TODO: Rename and change types and number of parameters
    public static commentBox newInstance(String param1, String param2) {
        commentBox fragment = new commentBox();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.comment_box,container,false);
        /*fire= (Button) view.findViewById(R.id.commentButton);
        commentByUser= (EditText) view.findViewById(R.id.commentBox);

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("inside on click");
               comm.publishComment();

            }
        });
*/
        return view;
    }

}

package com.warlock31.badcontroller.fragments;


import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.warlock31.badcontroller.MyApplication;
import com.warlock31.badcontroller.R;
import com.warlock31.badcontroller.adapters.AdapterXBox;
import com.warlock31.badcontroller.network.Constants;
import com.warlock31.badcontroller.network.VolleySingleton;
import com.warlock31.badcontroller.pojo.WordPressPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.warlock31.badcontroller.network.Keys.EndPointXBox.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentXbox#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentXbox extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_POSTS ="state_posts";
    private TextView text;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private ArrayList<WordPressPost> listPosts = new ArrayList<>();
    private RecyclerView listXboxPosts;

    private AdapterXBox adapterXBox;


    public FragmentXbox() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentXbox.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentXbox newInstance(String param1, String param2) {
        FragmentXbox fragment = new FragmentXbox();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_POSTS,listPosts);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




//        sendJsonRequest();

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_xbox, container, false);
        listXboxPosts = (RecyclerView) view.findViewById(R.id.listXboxPosts);
        listXboxPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterXBox = new AdapterXBox(getActivity());
        listXboxPosts.setAdapter(adapterXBox);

        if (savedInstanceState != null){
            listPosts = savedInstanceState.getParcelableArrayList(STATE_POSTS);

        }else {
           listPosts = MyApplication.getWritableDatabase().readPosts();
        }
        adapterXBox.setListPost(listPosts);

        return view;
    }

}

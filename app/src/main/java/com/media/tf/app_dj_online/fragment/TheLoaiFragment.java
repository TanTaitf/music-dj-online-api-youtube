package com.media.tf.app_dj_online.fragment;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.media.tf.app_dj_online.Model.KeyWord;
import com.media.tf.app_dj_online.Model.Video;
import com.media.tf.app_dj_online.R;
import com.media.tf.app_dj_online.adapter.Adapter_Tab_2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.media.tf.app_dj_online.Model.Config.api_search;

/**
 * Created by Nguyen Sang on 09/13/2017.
 */

public class TheLoaiFragment extends Fragment {

//    private CatLoadingView mView;
    private View view;
    //String api_search = "AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw";
    //https://www.googleapis.com/youtube/v3/search?part=snippet&q=android&type=video&maxResults=20&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw
    //private AVLoadingIndicatorView avi;
    int max = 40;
    //int count = 0;
    //String strtext = null;
    private RecyclerView myRecyclerView;
    //NestedScrollView layout_nested;
    LinearLayout ln_gif_loadhome;
    Adapter_Tab_2 adapter;
    DatabaseReference databaseReference;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_theloai,container,false);
        anhXa();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Key").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KeyWord keyWord = dataSnapshot.getValue(KeyWord.class);
                setTruyenTranh(myRecyclerView,keyWord.getKey_7(),true,false);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        try {
//            strtext = getArguments().getString("query");
//            Toast.makeText(getActivity(),"change query " + strtext,Toast.LENGTH_SHORT).show();
//
//        }catch (Exception e) {
//
//        }
        //layout_nested.setVisibility(View.GONE);
 //       if(strtext != null){

//            Toast.makeText(getActivity(),"load query",Toast.LENGTH_SHORT).show();
//            myRecyclerView.setVisibility(View.GONE);
//
//            avi.setVisibility(View.VISIBLE);
//            avi.show();
//            recycler_Search.setVisibility(View.VISIBLE);
//            setTruyenTranh(recycler_Search,strtext, true, false);

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    setTruyenTranh(recycler_Search,strtext, true, false);
//                }
//            });


//        }else {
//            recycler_Search.setVisibility(View.GONE);
//            myRecyclerView.setVisibility(View.VISIBLE);
//            Toast.makeText(getActivity(),"query null",Toast.LENGTH_SHORT).show();
//            avi.setVisibility(View.GONE);


//        mView = new CatLoadingView();
//        mView.setCancelable(true);
//        mView.show(getActivity().getSupportFragmentManager(), "");
        // tham số thứ 2 chuỗi url ,tham số thứ 3: là set auto Sroll cho recyclerView, thữ 4: true== CarouseLayout , false=Linearlayout

        //Toast.makeText(getActivity(),"Trang Tab 2 Load",Toast.LENGTH_SHORT).show();
        return view;
    }


    private void setTruyenTranh(final RecyclerView mRecyclerView , String key, final boolean action, final boolean layout) {
        key = key.replace(" ","%20");
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        if(layout==true){
//            CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL,true);
//            //layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//            mRecyclerView.setLayoutManager(layoutManager);
//            mRecyclerView.addOnScrollListener(new CenterScrollListener());
//        }else {
//            mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
//        }




//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addOnScrollListener(new CenterScrollListener());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        final ArrayList<Video>  arrayList= new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+key+"&type=video&maxResults="+max+"&key="+api_search;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                arrayList.clear();
                JSONObject item ;
                JSONObject id;
                String idvideo = "";
                JSONObject jsonSpinet ;
                String title = "";
                String dertion = "";
                String chanelTitle = "";
                JSONObject jsonthumnail;
                JSONObject jsondefault ;
                String imaview = "";
                String dayupl = "";
                JSONArray jsonitem = response.optJSONArray("items");
                for (int j = 0; j < jsonitem.length();j++)
                {
                     item = jsonitem.optJSONObject(j);
                     id = item.optJSONObject("id");
                     idvideo = id.optString("videoId");

                     jsonSpinet = item.optJSONObject("snippet");
                     title = jsonSpinet.optString("title");
                     dertion = jsonSpinet.optString("description");
                     dayupl = jsonSpinet.optString("publishedAt");

                     jsonthumnail = jsonSpinet.optJSONObject("thumbnails");
                     jsondefault = jsonthumnail.optJSONObject("medium");
                     imaview = jsondefault.optString("url");
                     chanelTitle = jsonSpinet.optString("channelTitle");
                    arrayList.add(new Video(idvideo,title,dertion,imaview, chanelTitle, dayupl));
                }
                //adapterVideo.notifyDataSetChanged();
                Adapter_Tab_2 adapter = new Adapter_Tab_2(arrayList,getActivity());
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                mView.dismiss();
//                avi.hide();
//                avi.setVisibility(View.GONE);
                //layout_nested.setVisibility(View.VISIBLE);
                ln_gif_loadhome.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.END);
        snapHelperStart.attachToRecyclerView(mRecyclerView);
    }

//    private void setAutoScroll(final RecyclerView recyclerView , final Adapter_Tab_2 adapter){
//        final int speedScroll = 2500;
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            int count = 0;
//            boolean flag = true;
//            @Override
//            public void run() {
//                if(count < adapter.getItemCount()){
//                    if(count==adapter.getItemCount()-1){
//                        flag = false;
//                    }else if(count == 0){
//                        flag = true;
//                    }
//                    if(flag) count++;
//                    else count--;
//
//                    recyclerView.smoothScrollToPosition(count);
//                    handler.postDelayed(this,speedScroll);
//                }
//            }
//        };
//        handler.postDelayed(runnable,speedScroll);
//
//    }
//

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

            final LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        private static final float MILLISECONDS_PER_INCH = 200f;

                        @Override
                        public PointF computeScrollVectorForPosition(int targetPosition) {
                            return CustomLinearLayoutManager.this
                                    .computeScrollVectorForPosition(targetPosition);
                        }

                        @Override
                        protected float calculateSpeedPerPixel
                                (DisplayMetrics displayMetrics) {
                            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }
    }

    private void anhXa() {
//        avi=view.findViewById(R.id.avi);
       // layout_nested = view.findViewById(R.id.layout_nested);
        myRecyclerView=(RecyclerView)view.findViewById(R.id.myRecyclerViewTheLoai);
//        recycler_Search=(RecyclerView)view.findViewById(R.id.myRecyclerViewSearch);
        ln_gif_loadhome = view.findViewById(R.id.ln_gif_loadhome);
    }
}

package com.media.tf.app_dj_online.fragment;

/**
 * Created by Nguyen Sang on 10/04/2017.
 */

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
import com.media.tf.app_dj_online.adapter.Adapter_Tab_3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.media.tf.app_dj_online.Model.Config.api_search;
import static com.media.tf.app_dj_online.R.id.listFeed;


public class RootFragment extends Fragment {
//    private FirebaseUser user;
//    private FragmentTransaction trans;
    ArrayList<Video> arrayList;
//    private CatLoadingView mView;
    private View view;
    LinearLayout ln_gif_loadhome;
    //https://www.googleapis.com/youtube/v3/search?part=snippet&q=android&type=video&maxResults=20&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw

    int max = 30;
    //int count = 0;

    private RecyclerView myRecyclerView;
    //com.hoanganhtuan95ptit.autoplayvideorecyclerview.AutoPlayVideoRecyclerView listFeed;

    //private FeedAdapter adapter;
    DatabaseReference databaseReference;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.root_fragment,container,false);
        anhXa();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Key").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KeyWord keyWord = dataSnapshot.getValue(KeyWord.class);
                setTruyenTranh(myRecyclerView,keyWord.getKey_6(),30,true,true);
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

//        mView = new CatLoadingView();
//        mView.setCancelable(true);
//        mView.show(getActivity().getSupportFragmentManager(), "");
        // tham số thứ 2 chuỗi url ,tham số thứ 3: là set auto Sroll cho recyclerView, thữ 4: true== CarouseLayout , false=Linearlayout

        return view;
    }

    private void setTruyenTranh(final RecyclerView mRecyclerView , String key, int max, final boolean action, boolean layout) {
		key = key.replace(" ","%20");
        mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addOnScrollListener(new CenterScrollListener());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        arrayList= new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+key+"&type=video&maxResults="+max+"&key="+api_search;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
                JSONArray jsonitem = response.optJSONArray("items");
                for (int j = 0; j < jsonitem.length();j++)
                {
                     item = jsonitem.optJSONObject(j);
                     id = item.optJSONObject("id");
                     idvideo = id.optString("videoId");


                     jsonSpinet = item.optJSONObject("snippet");
                     title = jsonSpinet.optString("title");
                     dertion = jsonSpinet.optString("description");
                     chanelTitle = jsonSpinet.optString("channelTitle");
                     jsonthumnail = jsonSpinet.optJSONObject("thumbnails");
                     jsondefault = jsonthumnail.optJSONObject("high");
                     imaview = jsondefault.optString("url");

                    arrayList.add(new Video(idvideo,title,dertion,imaview, chanelTitle));
                    //Toast.makeText(getApplicationContext(),imaview,Toast.LENGTH_SHORT).show();
                }

                //adapterVideo.notifyDataSetChanged();

                final Adapter_Tab_3 adapter = new Adapter_Tab_3(arrayList,getActivity());
                mRecyclerView.setAdapter(adapter);
//                mView.dismiss();
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
        //Toast.makeText(getActivity(),"Trang Tab 3 Load",Toast.LENGTH_SHORT).show();
    }
    public void onPause() {
        super.onPause();
        if (getActivity().getSupportFragmentManager().findFragmentByTag("RootFragment") != null)
            getActivity().getSupportFragmentManager().findFragmentByTag("RootFragment").setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getSupportFragmentManager().findFragmentByTag("RootFragment") != null)
            getActivity().getSupportFragmentManager().findFragmentByTag("RootFragment").getRetainInstance();
    }
//    private void getCountVideo(String IdVideo){
//        // https://www.googleapis.com/youtube/v3/videos?id=BBRCKcGPmhI&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw&part=snippet,contentDetails,statistics,status
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        String url = "https://www.googleapis.com/youtube/v3/videos?id="+IdVideo+"&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw&part=snippet,contentDetails,statistics,status";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
//                String dayUpload = "";
//                String viewCount = "";
//                String likeCount = "";
//                String disCount = "";
//                String commentCount = "";
//                String imaview = "";
//                String title = "";
//                String dertion = "";
//                JSONArray jsonitem = response.optJSONArray("items");
//                for (int j = 0; j < jsonitem.length();j++)
//                {
//                    JSONObject item = jsonitem.optJSONObject(j);
//
//                    JSONObject jsonSpinet = item.optJSONObject("snippet");
//                    dayUpload = jsonSpinet.optString("publishedAt");
//
//                    title = jsonSpinet.optString("title");
//                    dertion = jsonSpinet.optString("description");
//
//                    JSONObject jsonthumnail = jsonSpinet.optJSONObject("thumbnails");
//                    JSONObject jsondefault = jsonthumnail.optJSONObject("high");
//                    imaview = jsondefault.optString("url");
//
//
//                    JSONObject jsonStatic = item.optJSONObject("statistics");
//                    viewCount = jsonStatic.optString("viewCount");
//                    likeCount = jsonStatic.optString("likeCount");
//                    disCount = jsonStatic.optString("dislikeCount");
//                    commentCount = jsonStatic.optString("commentCount");
//
//                    Log.d("Video",title);
//                    Log.d("Video",dertion);
//                    Log.d("Video",imaview);
//
//                    Log.d("Video",dayUpload);
//                    Log.d("Video",viewCount);
//                    Log.d("Video",likeCount);
//                    Log.d("Video",disCount);
//                    Log.d("Video",commentCount);
//
//
//
//                }
//                arrayList.add(new Video(viewCount,title,dertion,imaview,""));
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }
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
        myRecyclerView=view.findViewById(listFeed);
        ln_gif_loadhome = view.findViewById(R.id.ln_gif_loadhome);
    }

}

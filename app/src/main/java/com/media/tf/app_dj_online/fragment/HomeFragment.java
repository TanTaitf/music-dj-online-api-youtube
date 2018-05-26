package com.media.tf.app_dj_online.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
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
import com.media.tf.app_dj_online.activity.ShowMoreList;
import com.media.tf.app_dj_online.adapter.TruyenTranhAdapter;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.media.tf.app_dj_online.Model.Config.api_search;
import static com.media.tf.app_dj_online.Model.Config.setFont;

/**
 * Created by Nguyen Sang on 09/13/2017.
 */

public class HomeFragment extends Fragment {

//    private CatLoadingView mView;
    private ImageView btnMoreTopXemNhieu,btnMoreTopTruyenHot,
            btnMoreTruyenMoiDang,btnMoreDangTienHanh;
    private Typeface typeface;
    private ShimmerTextView myShimmerTop,myShimmerMoiDang,
            myShimmerDangTienHanh,myShimmerHot,myShimmerXemNhieu;
    private String key_1="DJ Top";
    private String key_2="NSC Music";
    private String key_5="Pop Viet";
    private String key_3="Dance";
    private String key_4="EDM Remix";

    //SweetAlertDialog pDialog;
    private View view;
    LinearLayout ln_gif_loadhome;
    //    final String api_search = "AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw";
    //https://www.googleapis.com/youtube/v3/search?part=snippet&q=android&type=video&maxResults=20&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw

    //int max = 30;
    int count = 0;
    DatabaseReference databaseReference;
    private RecyclerView mRecyclerViewHot,mRecyclerViewMoiDang,mRecyclerViewTopView,mRecyclerViewDangTienHanh,myRecyclerViewXemNhieuNhat;

    // Access a Cloud Firestore instance from your Activity
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home,container,false);
        anhXa();

//        databaseReference.child("Key").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                KeyWord keyWord = dataSnapshot.getValue(KeyWord.class);
//
//                Toast.makeText(getActivity(),"-------" +keyWord.getKey_1() +keyWord.getKey_2(),Toast.LENGTH_SHORT).show();
//                arrayKey.add(keyWord.getKey_1());
//                arrayKey.add(keyWord.getKey_2());
//                arrayKey.add(keyWord.getKey_3());
//                arrayKey.add(keyWord.getKey_4());
//                arrayKey.add(keyWord.getKey_5());
//                arrayKey.add(keyWord.getKey_6());
//                arrayKey.add(keyWord.getKey_7());
//
//                //Log.d(TAG, "User name: " + user.getName() + ", email " + user.getEmail());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//        databaseReference.child("Key").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                arrayKey = new ArrayList<>();
//                KeyWord keyWord = dataSnapshot.getValue(KeyWord.class);
//                arrayKey.add(keyWord.getKey_1());
//                arrayKey.add(keyWord.getKey_2());
//                arrayKey.add(keyWord.getKey_3());
//                arrayKey.add(keyWord.getKey_4());
//                arrayKey.add(keyWord.getKey_5());
//                arrayKey.add(keyWord.getKey_6());
//                arrayKey.add(keyWord.getKey_7());
//                Toast.makeText(getActivity(),"---Size ----" +arrayKey.size() + keyWord.getKey_1().toString(),Toast.LENGTH_SHORT).show();
//                k1 = arrayKey.get(0);
//                k2 = arrayKey.get(1);
//                for (int i = 0; i < arrayKey.size(); i++){
//                    Log.d("A", arrayKey.get(i).toString());
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//        db.collection("Key")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Key").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KeyWord keyWord = dataSnapshot.getValue(KeyWord.class);
                setTruyenTranh(mRecyclerViewTopView,keyWord.getKey_1(),20,1,true,true);
                setTruyenTranh(mRecyclerViewHot,keyWord.getKey_2(),30,2,false,false);
                setTruyenTranh(myRecyclerViewXemNhieuNhat,keyWord.getKey_3(),30,3,false,false);
                setTruyenTranh(mRecyclerViewMoiDang,keyWord.getKey_4(),25,4,true,true);
                setTruyenTranh(mRecyclerViewDangTienHanh,keyWord.getKey_5(),30,5,false,false);

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
//        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Loading");
//            pDialog.setCancelable(false);
//            pDialog.show();
//        mView.setCancelable(true);
//        mView.show(getActivity().getSupportFragmentManager(), "");

        // tham số thứ 3: là set auto Sroll cho recyclerView, thữ 4: true== CarouseLayout , false=Linearlayout
        setShimmerTextView();
        btnMoreEvent();
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    @Override
    public void onPause() {
        super.onPause();
        if (getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment") != null)
            getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment").setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment") != null)
            getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment").getRetainInstance();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
//    private void setTruyen() {
//        String url="http://mangak.info/himekishi-ga-classmate/";
//        final RequestQueue resq= Volley.newRequestQueue(getActivity());
//        StringRequest stringreq= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Toast.makeText(getActivity(), "vao roi", Toast.LENGTH_SHORT).show();
//                Document document=Jsoup.parse(response);
//                if (document!=null){
//                    //Toast.makeText(getActivity(), "vao lan 2", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getActivity(), "null main", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        resq.add(stringreq);
//    }

//    private void setTruyenTopXemNhieu(final RecyclerView mRecyclerView, String url) {
//        final ArrayList<TopXemNhieu> arrayList= new ArrayList<>();
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
//        StringRequest stringQuest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Document document= Jsoup.parse(response);
//                Element elementMain= document.getElementsByClass("doc_nhieu").first();
//                Element elementMain2=null;
//                String title="",link="";
//                Elements element=null;
//                if (elementMain!=null){
//                   elementMain2 =elementMain.select("ul").first();
//                    if (elementMain2!=null){
//                       element = elementMain2.select("li");
//                        if(element!=null){
//                            for(Element e:element){
//                                Element theA= e.getElementsByTag("a").first();
//                                if (theA!=null){
//                                    title=theA.attr("title");
//                                    link=theA.attr("href");
//                                }
//                                arrayList.add(new TopXemNhieu(title,link));
//                            }
//                            TopXemNhieuAdapter adapter= new TopXemNhieuAdapter(arrayList,getActivity());
//                            //final Adapter_Tab_3 adapter = new Adapter_Tab_3(arrayList,getActivity());
//                            mRecyclerView.setAdapter(adapter);
//                            //mView.dismiss();
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(stringQuest);
//    }

    private void setTruyenTranh(final RecyclerView mRecyclerView , String key, int max, final int index, final boolean action, boolean layout) {
        key = key.replace(" ","%20");
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        if(layout==true){
            CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);
            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addOnScrollListener(new CenterScrollListener());
        }else {
            mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        final ArrayList<Video> arrayList= new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        final String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+key+"&type=video&maxResults="+max+"&key="+api_search;
        if (index == 1){
            key_1 = key;
        }
        if (index == 2){
            key_2 = key;
        }
        if (index == 5){
            key_3 = key;
        }
        if (index == 4){
            key_4 = key;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                arrayList.clear();
                JSONObject item ;
                JSONObject id ;
                String idvideo ;
                JSONObject jsonSpinet ;
                String title = "";
                String dertion = "";
                JSONObject jsonthumnail ;
                JSONObject jsondefault ;
                String imaview = "";
                String chanelTitle = "";
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

                     jsonthumnail = jsonSpinet.optJSONObject("thumbnails");
                     jsondefault = jsonthumnail.optJSONObject("medium");
                     imaview = jsondefault.optString("url");
                     chanelTitle = jsonSpinet.optString("channelTitle");
                     dayupl = jsonSpinet.optString("publishedAt");
                    arrayList.add(new Video(idvideo,title,dertion,imaview,chanelTitle, dayupl));

                }
                //adapterVideo.notifyDataSetChanged();
                final TruyenTranhAdapter adapter = new TruyenTranhAdapter(arrayList,getActivity());
                //final Adapter_Tab_3 adapter = new Adapter_Tab_3(arrayList,getActivity());
                mRecyclerView.setAdapter(adapter);
                count ++;
                if (action==true){
                    setAutoScroll(mRecyclerView,adapter);
                }
                if (count == 5){
                    ln_gif_loadhome.setVisibility(View.GONE);
//                    MainActivity.setWellcome();
                    //MainActivity.rippleBackground.stopRippleAnimation();
//                    layout_Wellcom.setVisibility(View.GONE);
//                    mView.dismiss();
                    //pDialog.dismiss();
//                    Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
//                    intent.putExtra("message", "Home");
//                    getActivity().startActivity(intent);
                }


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
    private void setAutoScroll(final RecyclerView recyclerView , final TruyenTranhAdapter adapter){
        final int speedScroll = 2500;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                if(count < adapter.getItemCount()){
                    if(count==adapter.getItemCount()-1){
                        flag = false;
                    }else if(count == 0){
                        flag = true;
                    }
                    if(flag) count++;
                    else count--;

                    recyclerView.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);

    }

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

    private void setShimmerTextView(){
        typeface=setFont(getContext(),typeface);
        Shimmer shimmer = new Shimmer();
        shimmer.start(myShimmerTop);
        myShimmerTop.setTypeface(typeface);
        shimmer.start(myShimmerHot);
        myShimmerHot.setTypeface(typeface);
        shimmer.start(myShimmerMoiDang);
        myShimmerMoiDang.setTypeface(typeface);
        shimmer.start(myShimmerDangTienHanh);
        myShimmerDangTienHanh.setTypeface(typeface);
        shimmer.start(myShimmerXemNhieu);
        myShimmerXemNhieu.setTypeface(typeface);
    }
    private void btnMoreEvent(){
        btnMoreDangTienHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowMoreList.class)
                        .putExtra("key",key_3).putExtra("Title","Top Trending"));
            }
        });
        btnMoreTopXemNhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowMoreList.class)
                        .putExtra("key",key_1).putExtra("Title","Top View"));

            }
        });
        btnMoreTruyenMoiDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowMoreList.class)
                        .putExtra("key",key_4).putExtra("Title","Top Recommenned"));
            }
        });
        btnMoreTopTruyenHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShowMoreList.class)
                        .putExtra("key",key_2).putExtra("Title","Top Hot 24h"));
            }
        });
    }
    private void anhXa() {
        ln_gif_loadhome = view.findViewById(R.id.ln_gif_loadhome);
        myShimmerXemNhieu=(ShimmerTextView)view.findViewById(R.id.shimmer_XemNhieuNhat);
        myShimmerTop=(ShimmerTextView)view.findViewById(R.id.shimmer_TopView);
        myShimmerHot=(ShimmerTextView)view.findViewById(R.id.shimmer_HOT);
        myShimmerMoiDang=(ShimmerTextView)view.findViewById(R.id.shimmer_MoiDang);
        myShimmerDangTienHanh=(ShimmerTextView)view.findViewById(R.id.shimmer_TienHanh);
        mRecyclerViewHot= (RecyclerView) view.findViewById(R.id.myRecyclerViewHot);
        mRecyclerViewTopView=(RecyclerView)view.findViewById(R.id.myRecyclerViewTopView);
        mRecyclerViewMoiDang=(RecyclerView)view.findViewById(R.id.myRecyclerViewMoiDang);
        mRecyclerViewDangTienHanh=(RecyclerView)view.findViewById(R.id.myRecyclerViewDangTienHanh);
        myRecyclerViewXemNhieuNhat=(RecyclerView)view.findViewById(R.id.myRecyclerViewXemNhieuNhat);
        btnMoreDangTienHanh= view.findViewById(R.id.btnMoreTienHanh);
        btnMoreTopTruyenHot=view.findViewById(R.id.btnMoreHot);
        btnMoreTopXemNhieu=view.findViewById(R.id.btnMoreXemNhieu);
        btnMoreTruyenMoiDang=view.findViewById(R.id.btnMoreMoiDang);
    }


}

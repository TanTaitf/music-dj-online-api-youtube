package com.media.tf.app_dj_online.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.media.tf.app_dj_online.Model.Video;
import com.media.tf.app_dj_online.R;
import com.media.tf.app_dj_online.adapter.Adapter_SearchVideo;
import com.media.tf.app_dj_online.adapter.Adapter_ViewSearch;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import static com.media.tf.app_dj_online.Model.Config.ID_ungDung;
import static com.media.tf.app_dj_online.Model.Config.api_search;
import static com.media.tf.app_dj_online.Model.Config.setFont;

public class ShowMoreList extends AppCompatActivity {
    String key_Search = "", title = "";
//    private int i=0;
    private Window window;
    private TextView txtTitle;
//    private CircleProgressBar circleProgressBar;
//    private NestedScrollView myNest;
    private RecyclerView myRecyclerView,recyclerView_Search ;
//    private AppBarLayout myAppbar;
//    private RecyclerView mRecyclerView;
    private Toolbar myToolBar;
    private SearchView searchView;
//    private String url;
//    private ArrayList<String> arrayLink= new ArrayList<>();
//    private ArrayList<TruyenTranh> arrayListTruyenTranh = new ArrayList<>();
//    private ArrayList<String> linkFavouris= new ArrayList<>();
    //private TruyenTranhTheLoaiAdapter adapter;
    private AVLoadingIndicatorView avi;
    private Typeface typeface;

    ArrayList<Video> arrayList;
    Adapter_SearchVideo adapter;
//    private CatLoadingView mView;
//    private boolean check_search = false;
    private View view;
    //String api_search = "AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw";
    //https://www.googleapis.com/youtube/v3/search?part=snippet&q=android&type=video&maxResults=20&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw
//    Context context;
    int max = 25;
//    int count = 0;
    ArrayList<Video> arraySearch;
    Adapter_ViewSearch adapterSearch ;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_list);

        typeface = setFont(this, typeface);
        arraySearch = new ArrayList<Video>();
        adapterSearch = new Adapter_ViewSearch(arraySearch, ShowMoreList.this);
        anhXa();
        avi.show();
        MobileAds.initialize(this, ID_ungDung);
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // back arrow ToolBar
        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        // set color status
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.LightBlue));
        }


        Intent intent = getIntent();
        key_Search = intent.getStringExtra("key");
        title = intent.getStringExtra("Title");
        txtTitle.setText(title);
        recyclerView_Search.setLayoutManager(new CustomLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView_Search.addOnScrollListener(new CenterScrollListener());
        recyclerView_Search.setHasFixedSize(true);
        recyclerView_Search.setNestedScrollingEnabled(false);

        if (key_Search == "" || key_Search == null) {
            key_Search = "TOP";
        }
        setTruyenTranh(myRecyclerView, key_Search, true, true);
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
    private void setTruyenTranh(final RecyclerView mRecyclerView , String key, final boolean action, boolean layout) {
        key = key.replace(" ","%20");
        mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addOnScrollListener(new CenterScrollListener());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        arrayList= new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+key+"&type=video&maxResults="+max+"&key="+api_search;
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

                    //getCountVideo(idvideo);

                    arrayList.add(new Video(idvideo,title,dertion,imaview, chanelTitle));
                    //Toast.makeText(getApplicationContext(),imaview,Toast.LENGTH_SHORT).show();
                }

                //adapterVideo.notifyDataSetChanged();

                adapter = new Adapter_SearchVideo(arrayList,ShowMoreList.this);
                mRecyclerView.setAdapter(adapter);
                avi.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

//        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.END);
//        snapHelperStart.attachToRecyclerView(mRecyclerView);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Runtime.getRuntime().gc();
                //moveTaskToBack(true);
                return true;
            case  R.id.menu_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            finish();
            super.onBackPressed();
            Runtime.getRuntime().gc();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.menu_search);
        searchView = (SearchView) myActionMenuItem.getActionView();

        EditText editText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        editText.setHintTextColor(getResources().getColor(R.color.white));
        editText.setTypeface(typeface);
        editText.setHint("Search ...");
        editText.setTextColor(getResources().getColor(R.color.white));
        editText.setTextSize(18);


        int searchImgId = android.support.v7.appcompat.R.id.search_button; // I used the explicit layout ID of searchview's ImageView
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.icon_search);
        ImageView searchCloseIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.ic_delete);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                arraySearch.clear();
                s = s.toLowerCase(Locale.getDefault());
                if(s!=null&&!s.isEmpty()){
                    myRecyclerView.setVisibility(View.GONE);
                    recyclerView_Search.setVisibility(View.VISIBLE);
                    recyclerView_Search.clearAnimation();
                    for (int i=0;i<arrayList.size();i++){

                        if(arrayList.get(i).getTitle().toLowerCase(Locale.getDefault()).toString().contains(s)){
                            arraySearch.add(new Video(arrayList.get(i).getId(),arrayList.get(i).getTitle(),arrayList.get(i).getDercption(),
                                    arrayList.get(i).getThumnail(),arrayList.get(i).getChanelTitle()));

                            adapterSearch = new Adapter_ViewSearch(arraySearch, ShowMoreList.this);
                            recyclerView_Search.setAdapter(adapterSearch);
                            adapterSearch.notifyDataSetChanged();

                        }

                    }
                }
                else {
                    recyclerView_Search.setVisibility(View.GONE);
                    myRecyclerView.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });
        super.onCreateOptionsMenu(menu);
        return true;
    }

    private void anhXa() {
        myRecyclerView = findViewById(R.id.listFeed);
        recyclerView_Search = findViewById(R.id.listFeed_Search);

        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        //searchView= (MaterialSearchView) findViewById(R.id.mySearchViewShowTheLoai);
//        myNest=(NestedScrollView)findViewById(R.id.myNestedScrollView);
//        myAppbar=(AppBarLayout)findViewById(R.id.myAppBarHanhDong);
//        circleProgressBar=(CircleProgressBar)findViewById(R.id.progress_circle);
        myToolBar=(Toolbar)findViewById(R.id.myToolBarHanhDong);
        txtTitle=(TextView)findViewById(R.id.txtTitleTheLoai);
        txtTitle.setTypeface(typeface);
    }
}

package com.media.tf.app_dj_online.fragment;


import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.media.tf.app_dj_online.Model.DataVideo;
import com.media.tf.app_dj_online.Model.MyModel;
import com.media.tf.app_dj_online.R;
import com.media.tf.app_dj_online.adapter.MyVideosAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nguyen Sang on 10/17/2017.
 */

public class NewspaperFragment extends Fragment {

//    @BindView(R.id.rv_home)
    AAH_CustomRecyclerView recyclerView;
    DatabaseReference databaseReference, databaseRef_2;
    private final List<MyModel> modelList = new ArrayList<>();
    private View view;
    ArrayList<DataVideo> videos ;
    MyVideosAdapter mAdapter;
    LinearLayout ln_gif_loadhome;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newspaper,container,false);
        ln_gif_loadhome = view.findViewById(R.id.ln_gif_loadhome);
        recyclerView = view.findViewById(R.id.rv_home);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new CenterScrollListener());

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //todo before setAdapter
        recyclerView.setActivity(getActivity());

        //optional - to play only first visible video
        recyclerView.setPlayOnlyFirstVideo(true); // false by default

        //optional - by default we check if url ends with ".mp4". If your urls do not end with mp4, you can set this param to false and implement your own check to see if video points to url
        recyclerView.setCheckForMp4(false); //true by default

        //optional - download videos to local storage (requires "android.permission.WRITE_EXTERNAL_STORAGE" in manifest or ask in runtime)
        //recyclerView.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); // (Environment.getExternalStorageDirectory() + "/Video") by default

        recyclerView.setDownloadVideos(true); // false by default

        recyclerView.setVisiblePercent(50); // percentage of View that needs to be visible to start playing

        //Toast.makeText(getActivity(), "Oncreateview", Toast.LENGTH_SHORT).show();

        //extra - start downloading all videos in background before loading RecyclerView
        List<String> urls = new ArrayList<>();
        for (MyModel object : modelList) {
            if (object.getVideo_url() != null && object.getVideo_url().contains("http"))
                urls.add(object.getVideo_url());
        }
        recyclerView.preDownload(urls);

        recyclerView.setAdapter(mAdapter);
        //call this functions when u want to start autoplay on loading async lists (eg firebase)
        recyclerView.smoothScrollBy(0,1);
        recyclerView.smoothScrollBy(0,-1);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final Picasso p = Picasso.get();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Video");
        //videos = new ArrayList<>();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //databaseRef_2 = databaseReference.child(dataSnapshot.getKey());
                DataVideo video = dataSnapshot.getValue(DataVideo.class);
//                Log.d("A", video.getLink());
//                Log.d("A", video.getThumnails());
//                Log.d("A", video.getTitle());
                modelList.add(new MyModel(video.getLink(),video.getThumnails(), video.getTitle()));
                //videos.add(new DataVideo(video.getLink(), video.getThumnails(), video.getTitle()));
                Collections.reverse(modelList);
                mAdapter.notifyDataSetChanged();
                ln_gif_loadhome.setVisibility(View.GONE);
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
//
//        for (int i = 0; i < videos.size(); i++){
//            modelList.add(new MyModel(videos.get(i).getLink(),videos.get(i).getThumnails(), videos.get(i).getTitle()));
//        }
//        modelList.add(new MyModel("http://www.betcoingaming.com/webdesigns/animatedslider/images/liveroulette2.mp4","http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg","name1"));
//        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video1"));
//        modelList.add(new MyModel("text 1"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1491561340/hello_cuwgcb.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1491561340/hello_cuwgcb.jpg", "video2"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/3_lfndfq.jpg", "image3"));
//        modelList.add(new MyModel("text 2"));
//        modelList.add(new MyModel("text 3"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/3_yqeudi.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/3_yqeudi.jpg", "video4"));
//        modelList.add(new MyModel("text 4"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/1_pyn1fm.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/1_pyn1fm.jpg", "video5"));
//        modelList.add(new MyModel("text 5"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1491561340/hello_cuwgcb.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1491561340/hello_cuwgcb.jpg", "video6"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/2_qwpgis.jpg", "image7"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/1_ybonak.jpg", "image8"));
//        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video9"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/3_lfndfq.jpg", "name10"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795676/4_nvnzry.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795676/4_nvnzry.jpg", "video11"));
//        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video12"));
//        modelList.add(new MyModel("text 6"));
//
//        modelList.add(new MyModel("text 7"));
//        modelList.add(new MyModel("text 8"));
//        modelList.add(new MyModel("https://r1---sn-i5ovpuj-i5oe.googlevideo.com/videoplayback?key=yt6&gir=yes&expire=1517921419&sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cexpire&mt=1517899737&pl=24&dur=41.215&lmt=1456724447933066&ip=45.126.96.38&ms=au%2Crdu&ei=K1B5WpX8HY30oQOpuoLQDg&mv=m&source=youtube&id=o-AL9lDEj94QCKiFQllcGZOePIqjctYUSC13UcXY0OFR52&mn=sn-i5ovpuj-i5oe%2Csn-i3b7kn76&mm=31%2C29&signature=A7F2B28A268FD928ACAE92621881082781FC096C.5938CBDA3DA95244844723AEFF718DA374F3A844&requiressl=yes&clen=2104218&itag=18&fvip=6&ratebypass=yes&ipbits=0&mime=video%2Fmp4&initcwndbps=241250","http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/2_qwpgis.jpg", " Youtube  image14"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/3_yqeudi.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/3_yqeudi.jpg", "video16"));
//        modelList.add(new MyModel("text 9"));
//        modelList.add(new MyModel("text 10"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/1_ybonak.jpg", "image15"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795675/1_pyn1fm.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/1_pyn1fm.jpg", "video17"));
//        modelList.add(new MyModel("https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video18"));
//        modelList.add(new MyModel("https://r1---sn-i5ovpuj-i5oe.googlevideo.com/videoplayback?ms=au%2Crdu&ei=mFF5Wu3EAsGhowOJpZT4CA&mv=m&mt=1517900082&expire=1517921784&requiressl=yes&mime=video%2Fmp4&fvip=1&ipbits=0&mn=sn-i5ovpuj-i5oe%2Csn-i3belnel&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cexpire&id=o-AGlNwLHmWE4ozp3PQsK2vmsG6XILUKzwHQRwzkDM8BCk&ratebypass=yes&itag=22&source=youtube&pl=24&mm=31%2C29&initcwndbps=301250&ip=45.126.96.38&key=yt6&dur=41.215&lmt=1472359826189397&signature=CE667415DE7BF33F383609BE8054073EE65B32E8.68F3136A4EF7B5425008D56374E157D540731358","http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/2_qwpgis.jpg", "Custom image19"));
//        modelList.add(new MyModel("http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/3_lfndfq.jpg", "image20"));
//        //modelList.add(new MyModel("https://r1---sn-i5ovpuj-i5oe.googlevideo.com/videoplayback?source=youtube&lmt=1472359826189397&key=yt6&ei=qFB5WpzDKNepoAOT_LOADw&ratebypass=yes&expire=1517921544&ipbits=0&dur=41.215&fvip=6&mime=video%2Fmp4&ms=au%2Crdu&mt=1517899845&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cexpire&mv=m&ip=45.126.96.38&requiressl=yes&initcwndbps=305000&signature=7F7AD9E61F854E87AC18764F70E8A3E4AF380D06.29CF32D3E9B8CFF8D10897F90EF273E27B091FD9&itag=22&id=o-ACWAGSnp-ufvYGjkY5brVk27i6dD51rFPvUdnASkroF8&pl=24&mm=31%2C29&mn=sn-i5ovpuj-i5oe%2Csn-i3b7kn76","http://res.cloudinary.com/krupen/image/upload/q_70/v1481795690/1_ybonak.jpg", "image21"));

        mAdapter = new MyVideosAdapter(modelList, p);

        super.onCreate(savedInstanceState);

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

//    @Override
//    public void onStop() {
//        super.onStop();
//        //add this code to pause videos (when app is minimised or paused)
//        recyclerView.stopVideos();
//    }

}

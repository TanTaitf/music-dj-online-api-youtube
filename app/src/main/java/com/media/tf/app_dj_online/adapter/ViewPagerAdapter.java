//package com.media.tf.app_dj_online.adapter;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.github.chrisbanes.photoview.PhotoView;
//import com.media.tf.app_dj_online.R;
//import com.squareup.picasso.MemoryPolicy;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
///**
// * Created by Nguyen Sang on 09/20/2017.
// */
//
//public class ViewPagerAdapter extends PagerAdapter {
//
//    OnClickInAdapter onClickInAdapter;
//    private Context context;
//    private LayoutInflater layoutInflater;
//    private List<String> stringList;
//
//
//    public ViewPagerAdapter(Context context, List<String> stringList) {
//        this.context = context;
//        this.stringList = stringList;
//    }
//
//    @Override
//    public int getCount() {
//        return stringList.size();
//    }
//
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    public Object instantiateItem(ViewGroup container, int position) {
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.custom_viewpager_truyen, null);
//        //PhotoView photoView= new PhotoView(container.getContext());
//        PhotoView photoView= (PhotoView) view.findViewById(R.id.myImage);
//        TextView textViewChapSo=(TextView)view.findViewById(R.id.txtChapHienTai);
//        int size=stringList.size();
//        textViewChapSo.setText(position+1+"/"+size);
//        photoView.setAdjustViewBounds(true);
//        //photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        Picasso.with(context)
//                .load(stringList.get(position))
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .noFade()
//                .placeholder(R.drawable.loading)
//                .into(photoView);
//        ViewPager vp = (ViewPager) container;
//        vp.addView(view, 0);
//        photoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickInAdapter= (OnClickInAdapter) context;
//                onClickInAdapter.onClickInAdapter();
//
//            }
//        });
//
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        ViewPager vp = (ViewPager) container;
//        View view = (View) object;
//        vp.removeView(view);
//    }
//    public interface OnClickInAdapter{
//        public void onClickInAdapter();
//    }
//}

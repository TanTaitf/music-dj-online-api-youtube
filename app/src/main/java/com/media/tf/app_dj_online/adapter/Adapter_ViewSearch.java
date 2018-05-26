package com.media.tf.app_dj_online.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.media.tf.app_dj_online.R;
import com.media.tf.app_dj_online.activity.PlayVideoAcivity;
import com.media.tf.app_dj_online.Model.Video;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.media.tf.app_dj_online.Model.Config.setFont;

/**
 * Created by Windows 8.1 Ultimate on 31/01/2018.
 */

public class Adapter_ViewSearch extends RecyclerView.Adapter<Adapter_ViewSearch.ViewHolder>{
    private ArrayList<Video> truyenTranhArrayList;
    private Context context;
    private int preViousPositon=0;
    private Typeface typeFace;
    public Adapter_ViewSearch(ArrayList<Video> truyenTranhArrayList, Context context) {
        this.truyenTranhArrayList = truyenTranhArrayList;
        this.context = context;
        //this.a = AnimationUtils.loadAnimation(context,R.anim.slide_down);
    }

    @Override
    public Adapter_ViewSearch.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.custom_tab_2,parent,false);

        return new ViewHolder(itemView,context,truyenTranhArrayList);
    }

    @Override
    public void onBindViewHolder(Adapter_ViewSearch.ViewHolder holder, int position) {
        holder.mTenTruyen.setText(truyenTranhArrayList.get(position).getTitle());
        holder.mTheLoai.setText("Chanel : " + truyenTranhArrayList.get(position).getChanelTitle() + "   ");
        holder.mLuotXem.setText(truyenTranhArrayList.get(position).getDercption());
        String url=truyenTranhArrayList.get(position).getThumnail();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.avata_bay_mau)
                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.avata_bay_mau)
                .into(holder.imgHinhTruyen);
        holder.itemView.clearAnimation();
        if(position>preViousPositon){
            holder.itemView.clearAnimation();
            //animate_up(holder);
            //AnimationUtils_Cus.animeteHorizontal(holder,true);

        }else{
            //AnimationUtils_Cus.animeteHorizontal(holder,false);
            //animate_down(holder);
        }
        preViousPositon=position;
    }
//    public void animate_down(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }
//    public void animate_up(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }
    @Override
    public int getItemCount() {
        return truyenTranhArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTenTruyen,mTheLoai,mLuotXem,mChapter;
        public ImageView imgHinhTruyen;
        ArrayList<Video> arrayListTruyen= new ArrayList<>();
        Context context;

        public ViewHolder(View itemView, Context context,ArrayList<Video> arrayListTruyen) {
            super(itemView);
            this.arrayListTruyen =arrayListTruyen;
            this.context=context;
            itemView.setOnClickListener(this);
            typeFace=setFont(context, typeFace);
            mTenTruyen= (TextView) itemView.findViewById(R.id.txtTenTruyen);
            mTenTruyen.setTypeface(typeFace);
            mTheLoai= (TextView) itemView.findViewById(R.id.txtTheLoai);
            mTheLoai.setTypeface(typeFace);
            mLuotXem= (TextView) itemView.findViewById(R.id.txtLuotXem);
            mLuotXem.setTypeface(typeFace);
            imgHinhTruyen= (ImageView) itemView.findViewById(R.id.imgTruyen);
            //imgHinhTruyen.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            Video truyenTranh = this.arrayListTruyen.get(pos);

            context.startActivity(new Intent(context,PlayVideoAcivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("id",truyenTranh.getId()));

        }
    }
}
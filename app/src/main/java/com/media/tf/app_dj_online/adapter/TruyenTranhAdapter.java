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

import com.media.tf.app_dj_online.CustomAnimate.AnimationUtils_Cus;
import com.media.tf.app_dj_online.R;
import com.media.tf.app_dj_online.activity.PlayVideoAcivity;
import com.media.tf.app_dj_online.Model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.media.tf.app_dj_online.Model.Config.setFont;

/**
 * Created by Nguyen Sang on 09/14/2017.
 */

public class TruyenTranhAdapter extends RecyclerView.Adapter<TruyenTranhAdapter.ViewHolder>{
    private ArrayList<Video> truyenTranhArrayList;
    private Context context1;
    private int preViousPositon=0;
    private Typeface typeFace;
    public TruyenTranhAdapter(ArrayList<Video> truyenTranhArrayList, Context context) {
        this.truyenTranhArrayList = truyenTranhArrayList;
        this.context1 = context;
    }

    @Override
    public TruyenTranhAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.custom_dong_truyen,parent,false);

        return new ViewHolder(itemView,context1,truyenTranhArrayList);
    }

    @Override
    public void onBindViewHolder(TruyenTranhAdapter.ViewHolder holder, int position) {

        holder.mTenTruyen.setText(truyenTranhArrayList.get(position).getTitle());
        holder.mChapter.setText("Chanel : " + truyenTranhArrayList.get(position).getChanelTitle() + "    ");
        holder.mLuotXem.setText(" " +truyenTranhArrayList.get(position).getDayupl().toString());
        String url=truyenTranhArrayList.get(position).getThumnail();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.avata_bay_mau)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .noFade()
                .error(R.drawable.avata_bay_mau)
//                .config(Bitmap.Config.RGB_565)
                .into(holder.imgHinhTruyen);
        try{
            if(position>preViousPositon){
                AnimationUtils_Cus.animate(holder,true);
            }else{
                AnimationUtils_Cus.animate(holder,false);
            }
            preViousPositon=position;
        }catch (Exception e){
            return;
        }

    }

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
            mLuotXem= (TextView) itemView.findViewById(R.id.txtLuotXem);
            mLuotXem.setTypeface(typeFace);
            mChapter=(TextView)itemView.findViewById(R.id.txtChapter);
            mChapter.setTypeface(typeFace);
            imgHinhTruyen= (ImageView) itemView.findViewById(R.id.imgTruyen);
//            imgHinhTruyen.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            Video truyenTranh = this.arrayListTruyen.get(pos);
//            Intent intent= new Intent(context,TruyenTranhChiTiet.class);
//            intent.putExtra("linkTruyen",truyenTranh.getId());
//            context.startActivity(intent);
//            Intent intent = new Intent(context, PlayVideoAcivity.class);
//            intent.putExtra("crash", true);
//            intent.putExtra("id",truyenTranh.getId());
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);

//            try {
//                pendingIntent.send();
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//            }
            context.startActivity(new Intent(context,PlayVideoAcivity.class).putExtra("id", truyenTranh.getId()));

//            context1.startActivity(new Intent(context1,PlayVideoAcivity.class)
//                    .putExtra("id",truyenTranh.getId()));

        }

    }
}

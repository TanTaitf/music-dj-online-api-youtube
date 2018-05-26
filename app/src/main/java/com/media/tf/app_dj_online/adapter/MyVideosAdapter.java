package com.media.tf.app_dj_online.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.media.tf.app_dj_online.Model.MyModel;
import com.media.tf.app_dj_online.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;


public class MyVideosAdapter extends AAH_VideosAdapter {

    private final List<MyModel> list;
    private final Picasso picasso;
    private static final int TYPE_VIDEO = 0, TYPE_TEXT = 1;
    Typeface typeFace;
    Context context1;

    public class MyViewHolder extends AAH_CustomViewHolder {
        final TextView tv;
        TextView txtDown;
        final ImageView img_vol, img_playback;
        //to mute/un-mute video (optional)
        boolean isMuted;

        public MyViewHolder(View x) {
            super(x);

            tv = ButterKnife.findById(x, R.id.tv);
            txtDown = ButterKnife.findById(x,R.id.txtDownload);
            tv.setTypeface(typeFace);
            txtDown.setTypeface(typeFace);
            img_vol = ButterKnife.findById(x, R.id.img_vol);
            img_playback = ButterKnife.findById(x, R.id.img_playback);
        }

        //override this method to get callback when video starts to play
        @Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.ic_pause);
            if (isMuted) {
                muteVideo();
                img_vol.setImageResource(R.drawable.ic_mute);
            } else {
                unmuteVideo();
                img_vol.setImageResource(R.drawable.ic_unmute);
            }
        }

        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.ic_play);
        }
    }


    public class MyTextViewHolder extends AAH_CustomViewHolder {
        final TextView tv;

        public MyTextViewHolder(View x) {
            super(x);
            tv = ButterKnife.findById(x, R.id.tv);
        }
    }

    public MyVideosAdapter(List<MyModel> list_urls, Picasso p) {
        this.list = list_urls;
        this.picasso = p;
    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        typeFace = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/Lato-Regular.ttf");
        context1 = parent.getContext();
        if (viewType==TYPE_TEXT) {
            View itemView1 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_text, parent, false);
            return new MyTextViewHolder(itemView1);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_card, parent, false);
            return new MyViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(final AAH_CustomViewHolder holder, int position) {
        if (list.get(position).getName().startsWith("text")) {
            ((MyTextViewHolder) holder).tv.setText(list.get(position).getName());
        } else {
            ((MyViewHolder) holder).tv.setText(list.get(position).getName());

            //todo
            holder.setImageUrl(list.get(position).getImage_url());
            holder.setVideoUrl(list.get(position).getVideo_url());

            //load image into imageview
            if (list.get(position).getImage_url() != null && !list.get(position).getImage_url().isEmpty()) {
                picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());
            }

            holder.setLooping(true); //optional - true by default

            //to play pause videos manually (optional)
            ((MyViewHolder) holder).img_playback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.isPlaying()) {
                        holder.pauseVideo();
                        holder.setPaused(true);
                    } else {
                        holder.playVideo();
                        holder.setPaused(false);
                    }
                }
            });
            ((MyViewHolder) holder).txtDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(holder.getVideoUrl().toString());
                    try{
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setTitle(((MyViewHolder) holder).tv.getText());

                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ((MyViewHolder) holder).tv.getText().toString());

                        DownloadManager manager = (DownloadManager)context1.getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                    }catch (Exception e){
                        return;
                    }

                }
            });

            //to mute/un-mute video (optional)
            ((MyViewHolder) holder).img_vol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MyViewHolder) holder).isMuted) {
                        holder.unmuteVideo();
                        ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_unmute);
                    } else {
                        holder.muteVideo();
                        ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_mute);
                    }
                    ((MyViewHolder) holder).isMuted = !((MyViewHolder) holder).isMuted;
                }
            });

            if (list.get(position).getVideo_url() == null) {
                ((MyViewHolder) holder).img_vol.setVisibility(View.GONE);
                ((MyViewHolder) holder).img_playback.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).img_vol.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).img_playback.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getName().startsWith("text")) {
            return TYPE_TEXT;
        } else return TYPE_VIDEO;
    }

}
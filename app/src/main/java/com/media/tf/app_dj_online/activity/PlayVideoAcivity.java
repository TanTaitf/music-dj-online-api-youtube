package com.media.tf.app_dj_online.activity;

import android.animation.Animator;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.media.tf.app_dj_online.Model.FullScreenManager;
import com.media.tf.app_dj_online.R;
import com.robertsimoes.shareable.Shareable;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

import static com.media.tf.app_dj_online.Model.Config.api_search;
import static com.media.tf.app_dj_online.Model.Config.setFont;


public class PlayVideoAcivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static YouTubePlayerView myyoutube;
    int REQUET_VIDEO = 111;
    String id = "";
    String api = "";
    YouTubePlayer youTubePlayer;
    ImageView fb;
    ImageView twitter;
    ImageView gplus;
    ImageView messages;
    ImageView email;
    ImageView linkedin;
    ImageView tumblr;
    ImageView reddit;
    private Window window;
    //final String api_search = "AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw";
    private CircleProgressBar circleProgressBar;
    private AVLoadingIndicatorView avi;
    private SmoothProgressBar mProgressBar;
    boolean check_heart = true, check_share = true, check_full = false;
    LinearLayout linear1, showless, review, layoutContainer;
    LinearLayout linear2, ln, layout_view_Other, layout_info_count, layout_title_view;
    //private Dialog dialog;
    ImageView img_heart, img_showfull;
    ImageButton img_share, img_comment;
    private LinearLayout mainLayout;
    TextView txttitle, txtCountView, txtdicription, txtDownload,
            txtCountLike, txtCountUnlike, txtShowMore, txtShowLess;
    Typeface typeface;
    String title = "", message, url;
    Intent intentnhan;
    private static FullScreenManager fullScreenManager;

//    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            intentnhan = getIntent();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeface = setFont(this,typeface);
        intentnhan = new Intent();
        intentnhan = getIntent();
        id = intentnhan.getStringExtra("id");
        // set color status
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.teal));
        }

        try {
            setContentView(R.layout.activity_playvideo);
        } catch (Exception e) {
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText("Oops...");
            pDialog.setContentText("Something went wrong!");
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                            onDestroy();
                            //android.os.Process.killProcess(android.os.Process.myPid());
                            return;
                        }
                    });
            pDialog.setCancelable(false);
            pDialog.show();
            return;
        }
        url = "http://youtube.com/watch?v=" + id;
        InitView();
        fullScreenManager = new FullScreenManager(PlayVideoAcivity.this);
        avi.show();
        getCountVideo(id);
        api = getResources().getString(R.string.api_key);
        myyoutube.initialize(api, PlayVideoAcivity.this);
        getYoutubeDownloadUrl(id);

//        linear1.setVisibility(View.GONE);
//        linear2.setVisibility(View.VISIBLE);

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInDown)
                        .duration(600)
                        .playOn(findViewById(R.id.linear2));


            }
        });

        showless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FadeOutUp)
                        .duration(700)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                linear1.setVisibility(View.VISIBLE);
                                linear2.setVisibility(View.GONE);

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .playOn(findViewById(R.id.linear2));

            }
        });

        img_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_heart == true) {
                    try {
                        img_heart.setImageResource(R.drawable.heart_28);
                        YoYo.with(Techniques.Landing)
                                .duration(400)
                                .withListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        img_heart.setImageResource(R.drawable.heart_28);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .playOn(img_heart);
                        check_heart = false;
                    } catch (Exception e) {
                        return;
                    }

                } else {
                    try {
                        img_heart.setImageResource(R.drawable.ic_ufi_heart);
                        check_heart = true;
                    } catch (Exception e) {
                        return;
                    }

                }


            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check_share == true) {

                    YoYo.with(Techniques.FadeInUp)
                            .duration(650)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    ln.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })
                            .playOn(ln);

                    check_share = false;
                } else {
                    YoYo.with(Techniques.FadeOutDown)
                            .duration(650)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    ln.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })
                            .playOn(ln);
                    check_share = true;

                }

            }
        });
        img_showfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    fullScreenManager.enterFullScreen();
                    youTubePlayer.setFullscreen(true);
                }catch (Exception e){
                    return;
                }
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
                            .message(message)
                            .socialChannel(Shareable.Builder.FACEBOOK)
                            .url(url)
                            .build();
                    shareInstance.share();
                } catch (Exception e) {
                    return;
                }


            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
                            .message(message)
                            .socialChannel(Shareable.Builder.TWITTER)
                            .url(url)
                            .build();
                    shareInstance.share();
                } catch (Exception e) {
                    return;
                }

            }
        });
        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
                            .message(message)
                            .socialChannel(Shareable.Builder.GOOGLE_PLUS)
                            .url(url)
                            .build();
                    shareInstance.share();
                } catch (Exception e) {
                    return;
                }

            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
                            .message(message)
                            .socialChannel(Shareable.Builder.MESSAGES)
                            .url(url)
                            .build();
                    shareInstance.share();
                } catch (Exception e) {
                    return;
                }

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
                            .message(message)
                            .socialChannel(Shareable.Builder.EMAIL)
                            .url(url)
                            .build();
                    shareInstance.share();
                } catch (Exception e) {
                    return;
                }

            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
                            .message(message)
                            .socialChannel(Shareable.Builder.LINKED_IN)
                            .url(url)
                            .build();
                    shareInstance.share();

                } catch (Exception e) {
                    return;
                }

            }
        });
        tumblr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
                            .message(message)
                            .socialChannel(Shareable.Builder.TUMBLR)
                            .url(url)
                            .build();
                    shareInstance.share();

                } catch (Exception e) {
                    return;
                }

            }
        });


    }

//    private void addFullScreenListenerToPlayer(final YouTubePlayer Player) {
//        myyoutube.addOnLayoutChangeListener(new YouTubePlayerFullScreenListener() {
//            @Override
//            public void onYouTubePlayerEnterFullScreen() {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                fullScreenManager.enterFullScreen();
//            }
//
//            @Override
//            public void onYouTubePlayerExitFullScreen() {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                fullScreenManager.exitFullScreen();
//
//                //myyoutube.getPlayerUIController().showCustomAction1(true);
//            }
//        });
//    }
//    public void setNoLandscape() {
//        if (player != null) {
//            int controlFlags = player.getFullscreenControlFlags();
//            controlFlags &= ~YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
//            player.setFullscreenControlFlags(controlFlags);
//            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//                player.pause();
//        }
//    }
//
//    public void setToLandscape() {
//        if (player != null) {
//            int controlFlags = player.getFullscreenControlFlags();
//            controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
//            player.setFullscreenControlFlags(controlFlags);
//        }
//    }
//    private void DialoglistviewSelectIntem() {
//        final String message = "This is my share message!";
//        final String url = "http://example.com";
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog_share);
//        dialog.setCanceledOnTouchOutside(false);
////        AnhxaDialog(dialog);
//
//        fb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
//                        .message(message)
//                        .socialChannel(Shareable.Builder.FACEBOOK)
//                        .url(url)
//                        .build();
//                shareInstance.share();
//            }
//        });
//        twitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
//                        .message(message)
//                        .socialChannel(Shareable.Builder.TWITTER)
//                        .url(url)
//                        .build();
//                shareInstance.share();
//            }
//        });
//        gplus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
//                        .message(message)
//                        .socialChannel(Shareable.Builder.GOOGLE_PLUS)
//                        .url(url)
//                        .build();
//                shareInstance.share();
//            }
//        });
//        messages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
//                        .message(message)
//                        .socialChannel(Shareable.Builder.MESSAGES)
//                        .url(url)
//                        .build();
//                shareInstance.share();
//            }
//        });
//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
//                        .message(message)
//                        .socialChannel(Shareable.Builder.EMAIL)
//                        .url(url)
//                        .build();
//                shareInstance.share();
//            }
//        });
//        linkedin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
//                        .message(message)
//                        .socialChannel(Shareable.Builder.LINKED_IN)
//                        .url(url)
//                        .build();
//                shareInstance.share();
//            }
//        });
//        tumblr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Shareable shareInstance = new Shareable.Builder(PlayVideoAcivity.this)
//                        .message(message)
//                        .socialChannel(Shareable.Builder.TUMBLR)
//                        .url(url)
//                        .build();
//                shareInstance.share();
//            }
//        });
//        dialog.show();
//        YoYo.with(Techniques.Flash)
//                .duration(700)
//                .playOn(ln);
//    }

//    private void AnhxaDialog(Dialog dialog) {
// anh xa dialog
//        ln = dialog.findViewById(R.id.dialog_main);
//        fb = (Button) dialog.findViewById(R.id.button_facebook);
//        twitter= (Button) dialog.findViewById(R.id.button_twitter);
//        gplus = (Button) dialog.findViewById(R.id.button_gplus);
//        messages= (Button) dialog.findViewById(R.id.button_messages);
//        email= (Button) dialog.findViewById(R.id.button_email);
//        linkedin= (Button) dialog.findViewById(R.id.button_linkedin);
//        tumblr= (Button) dialog.findViewById(R.id.button_tumblr);
//        btn_hide_dialog = dialog.findViewById(R.id.button_hide_dialog);
//        btn_hide_dialog.setTypeface(typeface);
//        fb.setTypeface(typeface);
//        twitter.setTypeface(typeface);
//        gplus.setTypeface(typeface);
//        messages.setTypeface(typeface);
//        linkedin.setTypeface(typeface);
//        email.setTypeface(typeface);
//        linkedin.setTypeface(typeface);
//        tumblr.setTypeface(typeface);
// }

    private void InitView() {
        layout_title_view = findViewById(R.id.layout_title_view);
        layout_info_count = findViewById(R.id.layout_info_count_view);
        txttitle = findViewById(R.id.txtTenVideo);
        txtCountView = findViewById(R.id.txtLuotXem);
        txtdicription = findViewById(R.id.txtdicription);
        img_heart = findViewById(R.id.img_heart);
        img_comment = findViewById(R.id.img_comment);
        img_share = findViewById(R.id.img_share);
        txtCountLike = findViewById(R.id.txtCountLike);
        txtCountUnlike = findViewById(R.id.txtCountunLike);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        txtShowMore = findViewById(R.id.show1);
        txtShowLess = findViewById(R.id.show2);
        txtDownload = findViewById(R.id.txtDownload);
        layout_view_Other = findViewById(R.id.layout_view_Other);

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        showless = (LinearLayout) findViewById(R.id.showless);
        linear2 = (LinearLayout) findViewById(R.id.linear2);

        myyoutube = findViewById(R.id.myyoutube);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mProgressBar = findViewById(R.id.google_now);

        ln = findViewById(R.id.share_main);
        fb = findViewById(R.id.button_facebook);
        twitter = findViewById(R.id.button_twitter);
        gplus = findViewById(R.id.button_gplus);
        messages = findViewById(R.id.button_messages);
        email = findViewById(R.id.button_email);
        linkedin = findViewById(R.id.button_linkedin);
        tumblr = findViewById(R.id.button_tumblr);
        img_showfull = findViewById(R.id.imageView_showfull);

        txtDownload.setTypeface(typeface);
        txtShowMore.setTypeface(typeface);
        txtShowLess.setTypeface(typeface);
        txtCountUnlike.setTypeface(typeface);
        txtdicription.setTypeface(typeface);
        txtCountLike.setTypeface(typeface);
        txttitle.setTypeface(typeface);
        txtCountView.setTypeface(typeface);


    }

    private void getCountVideo(String IdVideo) {
        // https://www.googleapis.com/youtube/v3/videos?id=BBRCKcGPmhI&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw&part=snippet,contentDetails,statistics,status
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //final DecimalFormat df = new DecimalFormat("#.#######");
        final DecimalFormat formatter = new DecimalFormat("#,###,###");

        String url = "https://www.googleapis.com/youtube/v3/videos?id=" + IdVideo + "&key=" + api_search + "&part=snippet,contentDetails,statistics,status";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                //String dayUpload = "";
                String viewCount = "";
                String likeCount = "";
                String disCount = "";
                //String commentCount = "";
                //String imaview = "";
                title = "";
                String dertion = "";
                JSONArray jsonitem = response.optJSONArray("items");
                for (int j = 0; j < jsonitem.length(); j++) {
                    JSONObject item = jsonitem.optJSONObject(j);

                    JSONObject jsonSpinet = item.optJSONObject("snippet");
                    //dayUpload = jsonSpinet.optString("publishedAt");

                    title = jsonSpinet.optString("title");
                    dertion = jsonSpinet.optString("description");

                    JSONObject jsonthumnail = jsonSpinet.optJSONObject("thumbnails");
                    JSONObject jsondefault = jsonthumnail.optJSONObject("high");
                    //imaview = jsondefault.optString("url");


                    JSONObject jsonStatic = item.optJSONObject("statistics");
                    viewCount = jsonStatic.optString("viewCount");
                    likeCount = jsonStatic.optString("likeCount");
                    disCount = jsonStatic.optString("dislikeCount");
                    //commentCount = jsonStatic.optString("commentCount");


                }
                txttitle.setText(title);
                message = title;
                String countV = formatter.format(Integer.parseInt(viewCount));
                txtCountView.setText(countV + " views");
                txtdicription.setText(dertion);
                txtCountLike.setText(formatter.format(Integer.parseInt(likeCount)));
                txtCountUnlike.setText(formatter.format(Integer.parseInt(disCount)));

                avi.hide();
                layout_title_view.setVisibility(View.VISIBLE);
                layout_info_count.setVisibility(View.VISIBLE);
                txtdicription.setVisibility(View.VISIBLE);
                txtShowMore.setVisibility(View.VISIBLE);
                txtShowLess.setVisibility(View.VISIBLE);
                txtDownload.setVisibility(View.VISIBLE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getYoutubeDownloadUrl(String IDLink) {
        final String youtubeLink = "http://youtube.com/watch?v=" + IDLink;
        new YouTubeExtractor(this) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                //mProgressBar.setVisibility(View.GONE);
                if (ytFiles == null) {
                    return;
                }
                // Iterate over itags
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    YtFile ytFile = ytFiles.get(itag);
                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                        addButtonToMainLayout(vMeta.getTitle(), ytFile);
                    }
                }

            }
        }.extract(youtubeLink, true, false);

    }

    private void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
        // Display some buttons and let the user choose the format
        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " +
                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
                ytfile.getFormat().getHeight() + "p";
        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";

        final Button btn = new Button(this);

        btn.setHeight(50);
        btn.setTypeface(typeface);
        btn.setText(btnText);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        btn.setBackgroundColor(color);
        btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_down, 0, 0, 0);
        btn.setTextColor(Color.parseColor("#ffffff"));

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .playOn(btn);
                String filename;
                if (videoTitle.length() > 55) {
                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
                } else {
                    filename = videoTitle + "." + ytfile.getFormat().getExt();
                }
                filename = filename.replaceAll("\\\\|>|<|\"|\\||\\*|\\?|%|:|#|/", "");


                downloadFromUrl(ytfile.getUrl(), videoTitle, filename, getApplicationContext());
            }
        });
        mainLayout.addView(btn);
    }
    public void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName, Context context) {
        try{
            Uri uri = Uri.parse(youtubeDlUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(downloadTitle);

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }catch (Exception e){

        }

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
            //Toast.makeText(getApplicationContext(),"Buffer",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPaused() {
            //Toast.makeText(getApplicationContext(),"Pause",Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onPlaying() {
            // Toast.makeText(getApplicationContext(),"play",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onSeekTo(int arg0) {
            //Toast.makeText(getApplicationContext(),"Seek to",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onStopped() {
            //Toast.makeText(getApplicationContext(),"Stop",Toast.LENGTH_SHORT).show();
//            if (check_full == true){
//                //onPlaying();
//                youTubePlayer.play();
//                Toast.makeText(getApplicationContext(),"Chạy tiếp tục",Toast.LENGTH_SHORT).show();
//
//
//            }

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
            // Toast.makeText(getApplicationContext(),"Loading",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
            //Toast.makeText(getApplicationContext(),"Started",Toast.LENGTH_SHORT).show();

        }
    };

//    @Override
//    public void registerComponentCallbacks(ComponentCallbacks callback) {
//        super.registerComponentCallbacks(callback);
//    }
//
//    @Override
//    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
//        super.unregisterComponentCallbacks(callback);
//    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubeplayer, boolean b) {
        youTubePlayer = youTubeplayer;
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        // youTubePlayer.setShowFullscreenButton(false);
        if (!b) {
            youTubePlayer.cueVideo(id); // priview thumbnail
            youTubePlayer.loadVideo(id);// play video now

        }
        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                if (check_full == false) {
                    //youTubePlayer.pause();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    fullScreenManager.enterFullScreen();
                    youTubePlayer.setFullscreen(true);
                    check_full = true;
                    playerStateChangeListener.onLoading();
                } else {
                    //youTubePlayer.pause();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    fullScreenManager.exitFullScreen();
                    youTubePlayer.setFullscreen(false);
                    check_full = false;
                    playerStateChangeListener.onLoading();
                }

            }
        });


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUET_VIDEO);
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Something went wrong!")
                    .show();
            Toast.makeText(getApplicationContext(), "Error Loading Video", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUET_VIDEO) {
            myyoutube.initialize(api, PlayVideoAcivity.this);
        }
    }

    // Kill an activity
    // android.os.Process.killProcess(android.os.Process.myPid());
    // Finish an activity
    // finish();
    @Override
    public void onBackPressed() {
        if (youTubePlayer == null) {
            finish();
            onDestroy();
            return;
        } else if (youTubePlayer.isPlaying() == true && check_full == false) {
            youTubePlayer.pause();
        } else {
            if (check_full == true) {
                //check_full = false;
                //youTubePlayer.setFullscreen(false);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreenManager.exitFullScreen();
                youTubePlayer.setFullscreen(false);
                youTubePlayer.play();
                playerStateChangeListener.onLoading();
                //youTubePlayer.release();
                //Toast.makeText(getApplicationContext(),"Vào đây ",Toast.LENGTH_SHORT).show();
                //youTubePlayer.pause();
//                return;
//               youTubePlayer.play();
            } else {
                if (youTubePlayer != null)
                    youTubePlayer.release();
                finish();
                onDestroy();
                return;

            }
        }


    }

    @Override
    protected void onDestroy() {
        //TODO: Clear intents
        if (youTubePlayer != null){
            youTubePlayer.release();
            fullScreenManager = null;
        }
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

}

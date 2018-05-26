package com.media.tf.app_dj_online.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.media.tf.app_dj_online.R;

import pl.droidsonroids.gif.GifImageView;

import static com.media.tf.app_dj_online.Model.Config.setFont_Logo;


public class LogoStart extends AppCompatActivity {
//    private Context mContext;
//    private RelativeLayout mRL;
//    private TextView mTV;
//    private Button mBTN;
//    private RadioGroup mRG;
//    private int mWidth;
//    private int mHeight;
//    private Random mRandom = new Random();
//    private Shader shader;
//    private GradientManager mGradientManager;
    RelativeLayout ln_wellcom;
    Typeface typeface ;
    GifImageView gitImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo_start);
        typeface= setFont_Logo(this, typeface);
        ln_wellcom = findViewById(R.id.ln_wellcom);
        gitImg = findViewById(R.id.gifview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        TextView mTV = findViewById(R.id.txtTitle);
        //ImageView imageView=(ImageView)findViewById(R.id.centerImage);
        mTV.setTypeface(typeface);

        // Get the TextView width and height in pixels
//        mWidth = mTV.getWidth();
//        mHeight = mTV.getHeight();
//        Point size = new Point(mWidth,mHeight);
//
//        // Initializing a new instance of GradientManager class
//        mGradientManager = new GradientManager(mContext,size);
//
//        // Get a random indicator to define next gradient type
//        int indicator = mRandom.nextInt(2);
//
//        if(indicator == 0){
//            shader = mGradientManager.getRandomLinearGradient();
//            mTV.setText("DJ EDM");
//        }else if(indicator == 1){
//            shader = mGradientManager.getRandomRadialGradient();
//            mTV.setText("DJ EDM");
//        }
////        else {
////            shader = mGradientManager.getRandomSweepGradient();
////            mTV.setText("Sweep Gradient");
////        }
//
//                /*
//                    public void setLayerType (int layerType, Paint paint)
//                        Specifies the type of layer backing this view. The layer can be LAYER_TYPE_NONE,
//                        LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE.
//
//                        A layer is associated with an optional Paint instance that controls how the
//                        layer is composed on screen.
//
//                    Parameters
//                        layerType : The type of layer to use with this view, must be one of
//                            LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE
//                        paint : The paint used to compose the layer. This argument is optional and
//                            can be null. It is ignored when the layer type is LAYER_TYPE_NONE
//                */
//                /*
//                    public static final int LAYER_TYPE_SOFTWARE
//                        Indicates that the view has a software layer. A software layer is backed by
//                        a bitmap and causes the view to be rendered using Android's software rendering
//                        pipeline, even if hardware acceleration is enabled.
//                */
//        mTV.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//
//                /*
//                    Paint
//                        The Paint class holds the style and color information about how to draw
//                        geometries, text and bitmaps.
//                */
//                /*
//                    Shader
//                        Known Direct Subclasses
//                        BitmapShader, ComposeShader, LinearGradient, RadialGradient, SweepGradient
//
//                        Shader is the based class for objects that return horizontal spans of colors
//                        during drawing. A subclass of Shader is installed in a Paint calling
//                        paint.setShader(shader). After that any object (other than a bitmap) that
//                        is drawn with that paint will get its color(s) from the shader.
//                */
//        mTV.getPaint().setShader(shader);



        //final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                gitImg.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(LogoStart.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        Runtime.getRuntime().gc();
        super.onDestroy();
    }
}

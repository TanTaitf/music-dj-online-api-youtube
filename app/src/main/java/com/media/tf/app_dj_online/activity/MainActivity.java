package com.media.tf.app_dj_online.activity;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.media.tf.app_dj_online.CustomClass.FlipPageViewTransformer;
import com.media.tf.app_dj_online.R;
import com.media.tf.app_dj_online.adapter.AdapterPagerFragment;
import com.media.tf.app_dj_online.fragment.CommentFragment;
import com.media.tf.app_dj_online.fragment.HomeFragment;
import com.media.tf.app_dj_online.fragment.NewspaperFragment;
import com.media.tf.app_dj_online.fragment.RootFragment;
import com.media.tf.app_dj_online.fragment.TheLoaiFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.media.tf.app_dj_online.Model.Config.isNetworkAvailable;
import static com.media.tf.app_dj_online.Model.Config.setFont;

public class MainActivity extends AppCompatActivity {
    private int limitNumberOfPages = 5;
    private Window window;
    private TextView txtView;
    private Toolbar toolbar;
    private Typeface typeface;
    private ViewPager viewPager;
    private SearchView searchView;
    private TabLayout tabLayout;
    public  LinearLayout layout_Wellcom;
//    public  RippleBackground rippleBackground;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface = setFont(this, typeface);
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("message");
//        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
        anhXa();
        //ImageView imageView=(ImageView)findViewById(R.id.centerImage);
        // set color status
        if (Build.VERSION.SDK_INT >= 21){
            window= this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.LightBlue));
        }

//        viewPager.setVisibility(View.INVISIBLE);
//        toolbar.setVisibility(View.INVISIBLE);
//        searchView.setVisibility(View.INVISIBLE);
//        tabLayout.setVisibility(View.INVISIBLE);

        txtView.setTypeface(typeface);
        // kiễm tra internet
        boolean checkInternet= isNetworkAvailable(this);
        if (checkInternet==false){
            showDialog();
        }
        else {
//            rippleBackground.startRippleAnimation();
//            rippleBackground.stopRippleAnimation();
//            layout_Wellcom.setVisibility(View.GONE);


            setUpViewPagerTab(viewPager);
            txtView.setText("HOME");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            if (layout_Wellcom.getVisibility() == View.GONE){
//                viewPager.setVisibility(View.VISIBLE);
//                toolbar.setVisibility(View.VISIBLE);
//                searchView.setVisibility(View.VISIBLE);
//                tabLayout.setVisibility(View.VISIBLE);
//            }
//            final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Loading");
//            pDialog.setCancelable(false);
//            pDialog.show();
//            new CountDownTimer(2000, 1000) {
//
//                public void onTick(long millisUntilFinished) {
//
//                }
//
//                public void onFinish() {
//                    rippleBackground.stopRippleAnimation();
//                    layout_Wellcom.setVisibility(View.GONE);
//
//                }
//            }.start();

        }
    }

    private void showDialog(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Internet not Connect?");
        pDialog.setContentText("You Sure Internet Connected !");
        pDialog.setCancelText("No, Exit !");
        pDialog.setConfirmText("Yes,Reload !");
        pDialog.setCancelable(false);
        pDialog.showCancelButton(false);
        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finishAffinity();
                        sDialog.cancel();
                    }
                });
        pDialog .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                        startActivity(getIntent());
                    }
                })
                .show();




//        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(this);
//        dialogBuilder.setTitle("Lỗi mạng");
//        dialogBuilder.setMessage("Vui lòng kiểm tra lại kết nối internet");
//        dialogBuilder.setCanceledOnTouchOutside(false);
//        dialogBuilder.setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//                startActivity(getIntent());
//            }
//        });
//        dialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finishAffinity();
//            }
//        });
//        MaterialDialog dialog = dialogBuilder.create();
//        dialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case  R.id.menu_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.menu_search);
        searchView = (SearchView) myActionMenuItem.getActionView();

        final EditText editText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
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

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtView.setVisibility(View.INVISIBLE);
//                YoYo.with(Techniques.FadeIn)
//                        .duration(250)
//                        .withListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//                        })
//                        .playOn(editText);

            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                txtView.setVisibility(View.VISIBLE);

//                YoYo.with(Techniques.FadeIn)
//                        .duration(250)
//                        .withListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                txtView.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//                        })
//                        .playOn(txtView);

                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //viewPager.setCurrentItem(2);
                searchView.setIconified(true);
                searchView.clearFocus();
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                startActivity(new Intent(getApplicationContext(), SearchVideo.class).putExtra("key",query));
//                Bundle bundle = new Bundle();
//                bundle.putString("query", query);
//                // set Fragmentclass Arguments
//                TheLoaiFragment fragobj = new TheLoaiFragment();
//                fragobj.setArguments(bundle);
//
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .detach(fragobj)
//                        .attach(fragobj)
//                        .commit();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
               // Toast.makeText(getApplicationContext(),"change",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu);
        return true;
    }

    private void setUpViewPagerTab(ViewPager viewpager) {
        AdapterPagerFragment adapter= new AdapterPagerFragment(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(),"");
        adapter.addFragment(new RootFragment(),"");
        adapter.addFragment(new TheLoaiFragment(),"");
        adapter.addFragment(new NewspaperFragment(),"");
        adapter.addFragment(new CommentFragment(),"");
//        adapter.addFragment(new MoreFragment(),"");
        viewpager.setOffscreenPageLimit(limitNumberOfPages);
        viewpager.setAdapter(adapter);
        viewpager.setPageTransformer(false,new FlipPageViewTransformer());
        tabLayout.setupWithViewPager(viewpager);
        setIconTabLayout();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
                int colorTo = getColorForTab(tab.getPosition());
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(900);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int color = (int) animator.getAnimatedValue();
                        toolbar.setBackgroundColor(color);
                        tabLayout.setBackgroundColor(color);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(color);
                        }
                    }
                });
                colorAnimation.start();
                txtView.setTypeface(typeface);

                //SET title cho Toolbar
                if (tab.getPosition()==1){
                    txtView.setText("TOP EDM");
                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

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
                            .playOn(txtView);
                    /*if (android.os.Build.VERSION.SDK_INT >= 21){
                        setStatusColorBlueGray900();
                    }*/
                }else if(tab.getPosition()==2){
                    txtView.setText("TOP 20 DJ");
                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

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
                            .playOn(txtView);

                    /*if (android.os.Build.VERSION.SDK_INT >= 21){
                        setStatusColorTeal900();
                    }*/
                }else if (tab.getPosition()==3){
                    txtView.setText("TOP INTRO");
                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

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
                            .playOn(txtView);

                }
                else if(tab.getPosition()==4){
                    txtView.setText("ABOUT APP");
                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

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
                            .playOn(txtView);

                }
                else if (tab.getPosition()==5) {
                    txtView.setText("TOP DANCE");
                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

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
                            .playOn(txtView);

                    /*if (android.os.Build.VERSION.SDK_INT >= 21) {
                        setStatusColorPurple900();
                    }*/
                }else {
                    txtView.setText("HOME");
                    YoYo.with(Techniques.FadeIn)
                            .duration(350)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

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
                            .playOn(txtView);

                    /*if (android.os.Build.VERSION.SDK_INT >= 21){
                        setStatusColorLightBlue900();
                    }*/
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setIconTabLayout() {
        View viewTabOne=getLayoutInflater().inflate(R.layout.tab_icon_home,null);
        viewTabOne.findViewById(R.id.iconHome).setBackgroundResource(R.drawable.home);
        tabLayout.getTabAt(0).setCustomView(viewTabOne);
        View viewTabTwo=getLayoutInflater().inflate(R.layout.tab_icon_theloai,null);
        viewTabTwo.findViewById(R.id.iconTheLoai).setBackgroundResource(R.drawable.menu_1);
        tabLayout.getTabAt(1).setCustomView(viewTabTwo);
        View viewTabThree=getLayoutInflater().inflate(R.layout.tab_icon_user,null);
        viewTabThree.findViewById(R.id.iconUser).setBackgroundResource(R.drawable.user);
        tabLayout.getTabAt(2).setCustomView(viewTabThree);
        View viewTabFour=getLayoutInflater().inflate(R.layout.tab_icon_newspaper,null);
        viewTabFour.findViewById(R.id.iconnewspaper).setBackgroundResource(R.drawable.newspaper);
        tabLayout.getTabAt(3).setCustomView(viewTabFour);
        View viewTabFive=getLayoutInflater().inflate(R.layout.tab_icon_comment,null);
        viewTabFive.findViewById(R.id.iconComment).setBackgroundResource(R.drawable.chat);
        tabLayout.getTabAt(4).setCustomView(viewTabFive);
//        View viewTabSix=getLayoutInflater().inflate(R.layout.tab_icon_more,null);
//        viewTabSix.findViewById(R.id.iconMore).setBackgroundResource(R.drawable.more);
//        tabLayout.getTabAt(5).setCustomView(viewTabSix);
    }
    public int getColorForTab(int position) {
        if (position == 0) return ContextCompat.getColor(this, R.color.LightBlue);
        else if (position == 1) return ContextCompat.getColor(this, R.color.cam);
        else if (position == 2) return ContextCompat.getColor(this, R.color.teal);
        else if (position==3) return  ContextCompat.getColor(this,R.color.Purple);
        else if (position==4) return ContextCompat.getColor(this,R.color.hongcanhsen);
        else if (position==5) return ContextCompat.getColor(this,R.color.hongcanhsen);
        else return 0;
}

    boolean doubleBackToExitPressedOnce = false;
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }else{
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                onDestroy();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                //System.exit(0);
                //return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit !", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }

    @Override
    protected void onDestroy() {
        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    private void anhXa() {
        txtView=(TextView)findViewById(R.id.myTextToolBar);
        toolbar=(Toolbar)findViewById(R.id.myToolBar);
        viewPager=(ViewPager)findViewById(R.id.myViewPager);
        tabLayout=(TabLayout)findViewById(R.id.myTabLayout);
        //layout_Wellcom = findViewById(R.id.layout_wellcome);
    }
}

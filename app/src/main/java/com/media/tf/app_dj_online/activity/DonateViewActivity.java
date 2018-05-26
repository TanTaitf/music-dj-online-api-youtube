package com.media.tf.app_dj_online.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.media.tf.app_dj_online.R;

public class DonateViewActivity extends AppCompatActivity implements RewardedVideoAd {
    private RewardedVideoAd mRewardedVideoAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_view);
//        MobileAds.initialize(this, ID_ungDung);
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//        mRewardedVideoAd.loadAd(ID_donViQuangCaoVideo,
//                new AdRequest.Builder().build());
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(getBaseContext(), "Ad triggered reward.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getBaseContext(), "Ad loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Toast.makeText(getBaseContext(), "Ad opened.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted() {
                Toast.makeText(getBaseContext(), "Ad started.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Toast.makeText(getBaseContext(), "Ad closed.", Toast.LENGTH_SHORT).show();
                mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                        new AdRequest.Builder().build());
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Toast.makeText(getBaseContext(), "Ad left application.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(getBaseContext(), "Ad failed to load.", Toast.LENGTH_SHORT).show();
            }
        });
        loadRewardedVideoAd();
    }
    private void loadRewardedVideoAd() {

        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void loadAd(String s, AdRequest adRequest) {

    }

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {

    }

    @Override
    public void setUserId(String s) {

    }

    @Override
    public RewardedVideoAdListener getRewardedVideoAdListener() {
        return null;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public void pause() {

    }

    @Override
    public void pause(Context context) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resume(Context context) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void destroy(Context context) {

    }

//    private static final long COUNTER_TIME = 10;
//    private static final int GAME_OVER_REWARD = 1;
//    private static final String TIME_REMAINING_KEY = "TIME_REMAINING";
//    private static final String COIN_COUNT_KEY = "COIN_COUNT";
//    private static final String GAME_PAUSE_KEY = "IS_GAME_PAUSED";
//    private static final String GAME_OVER_KEY = "IS_GAME_OVER";
//
//    private int mCoinCount;
//    private TextView mCoinCountText;
//    private CountDownTimer mCountDownTimer;
//    private boolean mGameOver;
//    private boolean mGamePaused;
//    private RewardedVideoAd mRewardedVideoAd;
//    private Button mRetryButton;
//    private Button mShowVideoButton;
//    private long mTimeRemaining;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donate_view);
//
//        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(this, ID_ungDung);
//
//        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//
//        // Create the "retry" button, which starts a new game.
//        mRetryButton = ((Button) findViewById(R.id.retry_button));
//        mRetryButton.setVisibility(View.INVISIBLE);
//        mRetryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startGame();
//            }
//        });
//
//        // Hide the "watch video" until the end of a game.
//        mShowVideoButton = ((Button) findViewById(R.id.watch_video));
//        mShowVideoButton.setVisibility(View.INVISIBLE);
//
//        mCoinCountText = ((TextView) findViewById(R.id.coin_count_text));
//
//        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
//            @Override
//            public void onRewarded(RewardItem rewardItem) {
//                Toast.makeText(getBaseContext(), "Ad triggered reward.", Toast.LENGTH_SHORT).show();
//                addCoins(rewardItem.getAmount());
//            }
//
//            @Override
//            public void onRewardedVideoAdLoaded() {
//                Toast.makeText(getBaseContext(), "Ad loaded.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdOpened() {
//                Toast.makeText(getBaseContext(), "Ad opened.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoStarted() {
//                Toast.makeText(getBaseContext(), "Ad started.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdClosed() {
//                Toast.makeText(getBaseContext(), "Ad closed.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdLeftApplication() {
//                Toast.makeText(getBaseContext(), "Ad left application.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdFailedToLoad(int i) {
//                Toast.makeText(getBaseContext(), "Ad failed to load.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        if (savedInstanceState == null) {
//            mCoinCount = 0;
//            mCoinCountText.setText("Coins: " + mCoinCount);
//
//            startGame();
//        }
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        mGamePaused = savedInstanceState.getBoolean(GAME_PAUSE_KEY);
//        mGameOver = savedInstanceState.getBoolean(GAME_OVER_KEY);
//        mTimeRemaining = savedInstanceState.getLong(TIME_REMAINING_KEY);
//        mCoinCount = savedInstanceState.getInt(COIN_COUNT_KEY);
//        mCoinCountText.setText("Coins: " + mCoinCount);
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putBoolean(GAME_PAUSE_KEY, mGamePaused);
//        outState.putBoolean(GAME_OVER_KEY, mGameOver);
//        outState.putLong(TIME_REMAINING_KEY, mTimeRemaining);
//        outState.putInt(COIN_COUNT_KEY, mCoinCount);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        pauseGame();
//        mRewardedVideoAd.pause(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mRewardedVideoAd.destroy(this);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!mGameOver && mGamePaused) {
//            resumeGame();
//        }
//        if (mGameOver) {
//            mRetryButton.setVisibility(View.VISIBLE);
//        }
//        if (mGameOver && mRewardedVideoAd.isLoaded()) {
//            mShowVideoButton.setVisibility(View.VISIBLE);
//        }
//        mRewardedVideoAd.resume(this);
//    }
//
//    private void pauseGame() {
//        if (mCountDownTimer != null) {
//            mCountDownTimer.cancel();
//        }
//        mGamePaused = true;
//    }
//
//    private void resumeGame() {
//        createTimer(mTimeRemaining);
//        mGamePaused = false;
//    }
//
//    private void addCoins(int coins) {
//        mCoinCount = mCoinCount + coins;
//        mCoinCountText.setText("Coins: " + mCoinCount);
//    }
//
//    private void startGame() {
//        // Hide the retry button and start the timer.
//        mRetryButton.setVisibility(View.INVISIBLE);
//        mShowVideoButton.setVisibility(View.INVISIBLE);
//        createTimer(COUNTER_TIME);
//        mGamePaused = false;
//        mGameOver = false;
//
//        mRewardedVideoAd.loadAd(ID_donViQuangCaoVideo, new AdRequest.Builder().build());
//    }
//
//    // Create the game timer, which counts down to the end of the level.
//    private void createTimer(long time) {
//        final TextView textView = ((TextView) findViewById(R.id.timer));
//        if (mCountDownTimer != null) {
//            mCountDownTimer.cancel();
//        }
//        mCountDownTimer = new CountDownTimer(time * 1000, 50) {
//            @Override
//            public void onTick(long millisUnitFinished) {
//                mTimeRemaining = ((millisUnitFinished / 1000) + 1);
//                textView.setText("seconds remaining: " + mTimeRemaining);
//            }
//
//            @Override
//            public void onFinish() {
//                gameOver();
//            }
//        };
//        mCountDownTimer.start();
//    }
//
//    private void gameOver() {
//        final TextView textView = ((TextView) findViewById(R.id.timer));
//        if (mRewardedVideoAd.isLoaded()) {
//            mShowVideoButton.setVisibility(View.VISIBLE);
//        }
//        textView.setText("You Lose!");
//        addCoins(GAME_OVER_REWARD);
//        mRetryButton.setVisibility(View.VISIBLE);
//        mGameOver = true;
//    }
//
//    public void showRewardedVideo(View view) {
//        mShowVideoButton.setVisibility(View.INVISIBLE);
//        if (mRewardedVideoAd.isLoaded()) {
//            mRewardedVideoAd.show();
//        }
//    }
}

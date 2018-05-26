package com.media.tf.app_dj_online.Model;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Windows 8.1 Ultimate on 10/03/2018.
 */

public class Config {

    public static ArrayList<String> getFirebase(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Key");
        final ArrayList<String> finalArrayKey = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                KeyWord keyWord = dataSnapshot.getValue(KeyWord.class);

                finalArrayKey.add(keyWord.getKey_1());
                finalArrayKey.add(keyWord.getKey_2());
                finalArrayKey.add(keyWord.getKey_3());
                finalArrayKey.add(keyWord.getKey_4());
                finalArrayKey.add(keyWord.getKey_5());
                finalArrayKey.add(keyWord.getKey_6());
                finalArrayKey.add(keyWord.getKey_7());

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

        return finalArrayKey;
    }


    public static String ID_donViQuangNative = "ca-app-pub-6352050986417104/2430514342";
    public static String ID_donViQuangCaoVideo = "ca-app-pub-6352050986417104/9059749290";
    public static String ID_ungDung = "ca-app-pub-6352050986417104~9357254466";
    public static String ID_donViQuangCao = "ca-app-pub-6352050986417104/5669290894";
    public static void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName, Context context) {
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
    public static Typeface setFont(Context context,Typeface typeface ){
        typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Lato-Regular.ttf");
        return typeface;
    }
    public static Typeface setFont_Logo(Context context,Typeface typeface ){
        typeface= Typeface.createFromAsset(context.getAssets(),"fonts/Capture_it.ttf");
        return typeface;
    }

    public static  String api_search = "AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw";
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}

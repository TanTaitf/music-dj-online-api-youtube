package com.media.tf.app_dj_online.Model;

/**
 * Created by Windows 8.1 Ultimate on 14/02/2018.
 */

public class DataVideo {
    public String title, link, thumnails;

    public String getTitle() {
        return title;
    }
    public DataVideo() {
        // mặc định nhận dữ liêu
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public DataVideo(String title, String link, String thumnails) {
        this.title = title;
        this.link = link;
        this.thumnails = thumnails;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setThumnails(String thumnails) {
        this.thumnails = thumnails;
    }

    public String getLink() {
        return link;

    }

    public String getThumnails() {
        return thumnails;
    }
}

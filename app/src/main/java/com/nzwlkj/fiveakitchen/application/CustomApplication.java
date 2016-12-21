package com.nzwlkj.fiveakitchen.application;

import android.app.Application;

/**
 * Created by WuWenGuang on 2016/10/19.
 */
public class CustomApplication extends Application {

    private String ApplicationCookName;
    private String ApplicationCookInfo;
    private String ApplicationCookFrom;
    private String ApplicationCookPrice;
    private String ApplicationCookImgURL;

    public String getApplicationCookImgURL() {
        return ApplicationCookImgURL;
    }

    public void setApplicationCookImgURL(String applicationCookImgURL) {
        ApplicationCookImgURL = applicationCookImgURL;
    }

    public String getApplicationCookPrice() {
        return ApplicationCookPrice;
    }

    public void setApplicationCookPrice(String applicationCookPrice) {
        ApplicationCookPrice = applicationCookPrice;
    }

    public String getApplicationCookFrom() {
        return ApplicationCookFrom;
    }

    public void setApplicationCookFrom(String applicationCookFrom) {
        ApplicationCookFrom = applicationCookFrom;
    }

    public String getApplicationCookInfo() {
        return ApplicationCookInfo;
    }

    public void setApplicationCookInfo(String applicationCookInfo) {
        ApplicationCookInfo = applicationCookInfo;
    }

    public String getApplicationCookName() {
        return ApplicationCookName;
    }

    public void setApplicationCookName(String applicationCookName) {
        ApplicationCookName = applicationCookName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}

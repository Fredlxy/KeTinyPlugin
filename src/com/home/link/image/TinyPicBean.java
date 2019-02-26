package com.home.link.image;

public class TinyPicBean {

    public String apiKey;

    public boolean freeCompress;

    public int freeCount;

    public int maxCount;


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isFreeCompress() {
        return freeCompress;
    }

    public void setFreeCompress(boolean freeCompress) {
        this.freeCompress = freeCompress;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public boolean isFree(){
       return freeCount <= maxCount;
    }


}

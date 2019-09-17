package com.home.link.image;

public class ApiKeyBean {

    public String apiKey;

    public boolean valid;

    public int compressCount;

    public String getApiKey(){
        return apiKey;
    }

    public boolean isValid(){
        return valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }

    public void setCompressCount(int compressCount){
        this.compressCount = compressCount;
    }

    public int getCompressCount() {
        return compressCount;
    }


    public ApiKeyBean(String apiKey, boolean validate) {
        this.apiKey = apiKey;
        this.valid = validate;
        this.compressCount = compressCount;
    }
}

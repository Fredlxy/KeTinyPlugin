package com.home.link.image;

public abstract class  CompressStrategy {

     public abstract  boolean  checkNeedCompress();

     public abstract  void onCompress(String imageUrls);

     public abstract String getCompressName();

}

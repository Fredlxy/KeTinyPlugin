package com.home.link.image;


import com.home.link.util.ComponentUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ImageBean {

    public String url;//路径

    public long beforeSize;//压缩前大小

    public long afterSize;//压缩后大小


    public String getSaveRate(){
        return accuracy(beforeSize - afterSize,beforeSize,2);
    }

    public String getBeforeSpace(){
        return ComponentUtil.formatFileSize(beforeSize);
    }

    public String getAfterSpace(){
        return ComponentUtil.formatFileSize(afterSize);
    }


    public static String accuracy(float num, float total, int scale){
        try {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            //可以设置精确几位小数
            df.setMaximumFractionDigits(scale);
            //模式 例如四舍五入
            df.setRoundingMode(RoundingMode.HALF_UP);
            double accuracy_num = num / total * 100;
            return df.format(accuracy_num)+"%";
        }catch (Exception e){
            return "0%";
        }
    }


}

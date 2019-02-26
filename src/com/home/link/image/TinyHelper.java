package com.home.link.image;

import com.home.link.config.KeTinyPicPreference;
import com.tinify.Source;
import com.tinify.Tinify;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class TinyHelper {

    public static void minify(@NotNull File src, @NotNull File des) throws Exception{

        Source source = Tinify.fromFile(src.getAbsolutePath());
        source.toFile(des.getAbsolutePath());
        //DefaultPreference.getInstance().updateKey(Tinify.key(), true, Tinify.compressionCount());
    }


    public static boolean checkTiny() {
        int compressionsThisMonth = Tinify.compressionCount();
        if (compressionsThisMonth < 500 && KeTinyPicPreference.getInstance().isTinyValid()) {
            Iterator var2 = KeTinyPicPreference.getInstance().getApiKeys().entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<String, ApiKeyBean> entry = (Map.Entry)var2.next();
                if (entry.getValue().isValid()) {
                    boolean valid = changeAPI(entry.getKey());
                    if (valid) {
                        return true;
                    }
                    KeTinyPicPreference.getInstance().updateKey(entry.getKey(), false, 0);
                }
            }
            KeTinyPicPreference.getInstance().setTiyValid(false);
        }
        return false;
    }


    private static boolean changeAPI(String apiKey) {
        try {
            Tinify.setKey(apiKey);
            Tinify.validate();
            return Tinify.compressionCount() < 500;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }
}

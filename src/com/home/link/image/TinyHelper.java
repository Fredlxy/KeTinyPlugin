package com.home.link.image;

import com.home.link.config.KeTinyPicPreference;
import com.intellij.ide.plugins.PluginManager;
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
        KeTinyPicPreference.getInstance().updateKey(Tinify.key(), true, Tinify.compressionCount());
    }


    public static boolean checkTiny() {
        int compressionsThisMonth = Tinify.compressionCount();
        PluginManager.getLogger().info("apiKey compressionCount = " + compressionsThisMonth +
                "  isTinyValid = " + KeTinyPicPreference.getInstance().isTinyValid());


        if (compressionsThisMonth < 500 && KeTinyPicPreference.getInstance().isTinyValid()) {
            Iterator var2 = KeTinyPicPreference.getInstance().getApiKeys().entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<String, ApiKeyBean> entry = (Map.Entry)var2.next();
                if (entry.getValue().isValid()) {
                    boolean valid = changeApiKey(entry.getKey());
                    if (valid) {
                        return true;
                    }
                    PluginManager.getLogger().info("updateKey " + entry.getKey() +" invalid");
                    KeTinyPicPreference.getInstance().updateKey(entry.getKey(), false, 0);
                }
            }
            PluginManager.getLogger().info("all apiKey value is invalid!" );
            KeTinyPicPreference.getInstance().setTiyValid(false);
        }
        return false;
    }


    private static boolean changeApiKey(String apiKey) {
        PluginManager.getLogger().info("changeApiKey is " + apiKey );
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

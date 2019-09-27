package com.home.link.image;

import com.home.link.config.TinyPngPreference;
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
        TinyPngPreference.getInstance().updateKey(Tinify.key(), true);
    }


    public static boolean checkTinyValid() {

        if(TinyPngPreference.getInstance() == null ){
            return false;
        }
        Map<String,ApiKeyBean> apiKeyMap = TinyPngPreference.getInstance().getApiKeys();
        Iterator iterator = apiKeyMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, ApiKeyBean> entry = (Map.Entry)iterator.next();
            if (entry.getValue().isValid()) {
                boolean valid = changeApiKey(entry.getKey());
                if (valid) {
                    return true;
                }
                PluginManager.getLogger().info("updateKey " + entry.getKey() +" invalid");
                TinyPngPreference.getInstance().updateKey(entry.getKey(), false);
            }
        }
        PluginManager.getLogger().info("all apiKey value is invalid!" );
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

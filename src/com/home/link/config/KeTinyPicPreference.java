package com.home.link.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 相当于android的preference，用于保存插件设置项
 * more detail :http://corochann.com/intellij-plugin-development-introduction-persiststatecomponent-903.html
 */

@State(name = "com.home.link.config.KeTinyPicPreference", storages = @Storage(value = "$APP_CONFIG$/LianjiaTinyPic.xml"))
public class KeTinyPicPreference implements PersistentStateComponent<KeTinyPicPreference> {
    private String mApikey;

    @Nullable
    @Override
    public KeTinyPicPreference getState() {
        //每次设置项被保存的时候调用
        return this;
    }

    @Override
    public void loadState(@NotNull KeTinyPicPreference tinyPicPreference) {
        XmlSerializerUtil.copyBean(tinyPicPreference, this);
    }

    /**
     * more detail:http://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_services.html
     *
     * @return
     */
    @Nullable
    public static KeTinyPicPreference getInstance() {
        return ServiceManager.getService(KeTinyPicPreference.class);
    }

    /**
     * 设置key
     *
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
        this.mApikey = apiKey;
    }


    public String getApiKey() {
        return mApikey;
    }
}

package com.home.link.config;

import com.home.link.common.Constants;
import com.home.link.image.ApiKeyBean;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 相当于android的preference，用于保存插件设置项
 * more detail :http://corochann.com/intellij-plugin-development-introduction-persiststatecomponent-903.html
 */

@State(name = "com.home.link.config.KeTinyPicPreference", storages = @Storage(value = "$APP_CONFIG$/LianjiaTinyPic.xml"))
public class KeTinyPicPreference implements PersistentStateComponent<KeTinyPicPreference> {

    private KeyState mKeyState = new KeyState();

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

    public Map<String,ApiKeyBean> getApiKeys() {
        return mKeyState.apiKeys;
    }

    public void updateKey(String key, boolean valid) {
        ApiKeyBean bean = this.mKeyState.apiKeys.get(key);
        if (bean == null) {
            bean = new ApiKeyBean(key, valid);
        } else {
            bean.setValid(valid);
        }

        this.mKeyState.apiKeys.put(key, bean);
    }

    public static class KeyState {
        private Map<String, ApiKeyBean> apiKeys;

        public KeyState() {
            this.apiKeys = new HashMap();
            this.apiKeys.put(Constants.DEFAULT_API_KEY, new ApiKeyBean(Constants.DEFAULT_API_KEY, true));
        }
    }
}

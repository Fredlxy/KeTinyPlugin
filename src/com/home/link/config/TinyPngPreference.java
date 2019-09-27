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

import java.util.HashMap;
import java.util.Map;

/**
 * 相当于android的preference，用于保存插件设置项
 * more detail :http://corochann.com/intellij-plugin-development-introduction-persiststatecomponent-903.html
 */

@State(name = "com.home.link.config.TinyPngPreference", storages = @Storage(value = "$APP_CONFIG$/KeTinyPng.xml"))
public class TinyPngPreference implements PersistentStateComponent<TinyPngPreference> {

    public KeyState keyState;


    public KeyState getKeyState(){
        if(keyState == null){
            keyState = new KeyState();
        }
        return keyState;
    }

    public void setKeyState(KeyState state){
        this.keyState = state;
    }

    public TinyPngPreference(){}

    @Nullable
    @Override
    public TinyPngPreference getState() {
        //每次设置项被保存的时候调用
        return this;
    }

    @Override
    public void loadState(@NotNull TinyPngPreference keyState) {
        XmlSerializerUtil.copyBean(keyState, this);
    }

    /**
     * more detail:http://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_services.html
     *
     * @return
     */
    @Nullable
    public static TinyPngPreference getInstance() {

        return ServiceManager.getService(TinyPngPreference.class);
    }

    public Map<String, ApiKeyBean> getApiKeys() {
        return getKeyState().apiKeys;
    }

    public void updateKey(String key, boolean valid) {

        ApiKeyBean bean = this.getKeyState().apiKeys.get(key);
        if (bean == null) {
            bean = new ApiKeyBean(key, valid);
        } else {
            bean.setValid(valid);
        }

        this.getKeyState().apiKeys.put(key, bean);
    }

    public static class KeyState {
        public Map<String, ApiKeyBean> apiKeys;

        public KeyState() {
            this.apiKeys = new HashMap<>();
            this.apiKeys.put(Constants.DEFAULT_API_KEY, new ApiKeyBean(Constants.DEFAULT_API_KEY, true));
        }
    }
}

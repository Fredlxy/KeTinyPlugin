package com.home.link.ui;

import com.home.link.common.Constants;
import com.home.link.config.KeTinyPicPreference;
import com.home.link.util.ComponentUtil;
import com.home.link.util.StringUtil;
import com.intellij.ide.BrowserUtil;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.components.labels.LinkListener;

import javax.swing.*;

public class PreferencesGUI {
    private JPanel mPanel;
    private JTextField mInputApiKey;
    private LinkLabel mApiLinkLabel;
    private KeTinyPicPreference mPrefence;

    public JComponent create(KeTinyPicPreference preference) {
        mPrefence = preference;
        mInputApiKey.setText(preference.getApiKey() != null ? preference.getApiKey() : "");
        System.out.println("KeTinyPicPreference api key :" + preference.getApiKey());
        mApiLinkLabel.setListener(new LinkListener() {
            @Override
            public void linkSelected(LinkLabel linkLabel, Object o) {
                BrowserUtil.browse(Constants.LINK_TINY_PNG_DEVELOPER);
            }
        }, "");
        return mPanel;
    }

    public void apply() {
        String text = mInputApiKey.getText();
        if (StringUtil.isNotEmpty(text)) {
            mPrefence.setApiKey(text);
        }
    }


    public boolean isModified() {
        String apiKeyInput = ComponentUtil.getInputText(mInputApiKey);
        if (!StringUtil.equals(apiKeyInput, mPrefence.getApiKey())) {
            return true;
        }
        return false;
    }

    public void reset(){
        mInputApiKey.setText(mPrefence.getApiKey());
    }
}


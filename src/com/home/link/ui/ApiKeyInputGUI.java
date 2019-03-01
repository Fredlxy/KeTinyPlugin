package com.home.link.ui;

import com.home.link.common.Constants;
import com.home.link.config.KeTinyPicPreference;
import com.home.link.image.ApiKeyBean;
import com.intellij.ide.BrowserUtil;
import com.intellij.ui.components.labels.LinkLabel;

import javax.swing.*;
import java.util.Map;

public class ApiKeyInputGUI {
    private JPanel rootPanel;
    private JButton mAddButton;
    private LinkLabel<String> mApiKeyLink;
    private JList mJList;
    private JButton mDeleteButton;
    private KeTinyPicPreference mPrefence;
    private DefaultListModel<String> mListModel;

    public JComponent create() {
        mPrefence = KeTinyPicPreference.getInstance();
        mApiKeyLink.setListener((linkLabel, o) -> {
            BrowserUtil.browse(Constants.LINK_TINY_PNG_DEVELOPER);
        }, "");

        this.displayApiKeys();
        this.mDeleteButton.addActionListener(actionEvent -> {
            int selectIndex = mJList.getSelectedIndex();
            String selectedApiKey = mListModel.get(selectIndex);
            if(!Constants.DEFAULT_API_KEY.equals(selectedApiKey)){
                mListModel.remove(selectIndex);
                mPrefence.getApiKeys().remove(selectedApiKey);
            }
        });

        mAddButton.addActionListener(actionEvent -> {
            AddApiKeyDialog dialog = new AddApiKeyDialog();
            dialog.setOnActionListener(apiKey -> {
                KeTinyPicPreference.getInstance().updateKey(apiKey,true,0);
                mListModel.add(0,apiKey);
                mJList.setModel(mListModel);
            });
            dialog.pack();
            dialog.setLocationRelativeTo(mJList);
            dialog.setVisible(true);

        });
        return rootPanel;
    }

    private void displayApiKeys() {
        mListModel = new DefaultListModel<String>();
        Map<String, ApiKeyBean> hashMap = mPrefence.getApiKeys();
        if (mPrefence.isTinyValid()) {
            for (String key : hashMap.keySet()) {
                if (key != null) {
                    mListModel.addElement(key);
                }
            }
        }
        mJList.setModel(mListModel);
        mJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mJList.setSelectedIndex(0);
        mJList.setVisibleRowCount(10);
        mJList.addListSelectionListener(listSelectionEvent -> {

        });
    }

    public void addInputKey(String inputKey) {
        if (inputKey != null && inputKey.trim().length() > 0) {
            Map<String, ApiKeyBean> hashMap = mPrefence.getApiKeys();
            hashMap.put(inputKey, new ApiKeyBean(inputKey, true, 0));
        }
    }

    public void removeInputKey(String inputKey) {
        if (inputKey != null && inputKey.trim().length() > 0) {
            Map<String, ApiKeyBean> hashMap = mPrefence.getApiKeys();
            hashMap.remove(inputKey);
        }
    }


    public void apply() {

    }

    public boolean isModified() {
        return false;
    }

    public void reset() {

    }

}

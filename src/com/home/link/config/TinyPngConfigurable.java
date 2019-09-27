package com.home.link.config;

import com.home.link.ui.ApiKeyInputGUI;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TinyPngConfigurable implements SearchableConfigurable {

    private ApiKeyInputGUI mRootGUI;

    public static void showSettingsDialog(Project project) {
        ShowSettingsUtil.getInstance().showSettingsDialog(project, TinyPngConfigurable.class);
    }

    @NotNull
    @Override
    public String getId() {
        return "com.home.link.config.TinyPngConfigurable";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "TinyPng";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mRootGUI = new ApiKeyInputGUI();
        return mRootGUI.create();
    }

    @Override
    public boolean isModified() {
        return mRootGUI.isModified();
    }

    @Override
    public void apply(){
        System.out.println("apply method is called");
        mRootGUI.apply();
    }

    @Override
    public void reset() {
        mRootGUI.reset();
    }

    @Override
    public void disposeUIResources() {
        //不需要ui的时候，释放资源
        mRootGUI = null;
    }
}

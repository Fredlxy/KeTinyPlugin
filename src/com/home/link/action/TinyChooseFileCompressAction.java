package com.home.link.action;

import com.home.link.image.TinyCompressFilesBackgroundTask;
import com.home.link.util.ComponentUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;


public class TinyChooseFileCompressAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getProject();
        if (project != null) {
            ComponentUtil.chooseFile(project,(files) -> {
                TinyCompressFilesBackgroundTask task = new TinyCompressFilesBackgroundTask(files,project,"TinyPng",true);
                task.project = project;
                task.runTask();
            });
        }

    }
}

package com.home.link.action;

import com.home.link.config.KeTinyPicPreference;
import com.home.link.image.TinyCompressFilesBackgroundTask;
import com.home.link.util.ComponentUtil;
import com.home.link.util.StringUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO：
 * 1、需要保存已经压缩的图片，避免多次压缩
 * 2、自动选择图片压缩
 * 3、自动输入api，可以多个
 */

public class TinyPicAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getData(PlatformDataKeys.PROJECT);

        VirtualFile[] virtualFiles = e.getData(LangDataKeys.VIRTUAL_FILE_ARRAY);

        List<String> imageUrls = filterVirtualFiles(virtualFiles);

        if (project == null || virtualFiles == null) {
            return;
        }


        TinyCompressFilesBackgroundTask task = new TinyCompressFilesBackgroundTask(imageUrls,project,"TinyPic",true);
        task.project = project;
        task.runTask();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        VirtualFile[] virtualFiles = e.getData(LangDataKeys.VIRTUAL_FILE_ARRAY);
        List<String> files = filterVirtualFiles(virtualFiles);
        e.getPresentation().setVisible(files.size() != 0);
    }

    /**
     * 拿到需要压缩的图片url
     *
     * @param virtualFiles
     * @return
     */
    private List<String> filterVirtualFiles(VirtualFile[] virtualFiles) {
        List<String> files = new ArrayList<>();
        if (virtualFiles == null) {
            return files;
        }
        for (VirtualFile virtualFile : virtualFiles) {
            if (isImage(virtualFile) && !virtualFile.isDirectory()) {
                files.add(virtualFile.getCanonicalPath());
            }
        }
        return files;
    }

    /**
     * 判断是否是图片
     *
     * @param virtualFile
     * @return
     */
    private boolean isImage(VirtualFile virtualFile) {
        if (virtualFile == null) {
            return false;
        }
        String ext = virtualFile.getExtension();
        if (ext != null && (ext.startsWith("png") || ext.startsWith("jpg")
                || ext.startsWith(".9") || ext.startsWith("jpeg"))) {
            return true;
        } else {
            return false;
        }
    }
}

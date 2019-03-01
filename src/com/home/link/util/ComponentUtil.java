package com.home.link.util;

import com.home.link.common.Constants;
import com.home.link.image.ImageBean;
import com.home.link.itf.OnFileChosenListener;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.swing.text.JTextComponent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alvince on 17-6-29.
 *
 * @author alvince.zy@gmail.com
 * @version 1.0, 6/28/2017
 * @since 1.0
 */
public class ComponentUtil {

    public static String getInputText(JTextComponent textComponent) {
        return getInputText(textComponent, true);
    }

    public static String getInputText(JTextComponent textComponent, boolean trim) {
        if (textComponent == null) return StringUtil.EMPTY;

        String text = textComponent.getText();
        if (trim) text = text.trim();
        return StringUtil.isNotEmpty(text) ? text : StringUtil.EMPTY;
    }

    /**
     * 展示通知
     * @param project
     * @param type
     * @param content
     */
    public static void showNotification(Project project,NotificationType type,String content,boolean important){
        Notification notification = new Notification(Constants.DISPLAY_GROUP_PROMPT,
                Constants.APP_NAME, content, type);
        notification.setImportant(important);
        Notifications.Bus.notify(notification, project);

    }


    public static void chooseFile(@NotNull Project project, @NotNull final OnFileChosenListener listener) {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(true, true, false, false, false, true) {
            public boolean isFileSelectable(VirtualFile file) {
                return super.isFileSelectable(file) && isFileCanBeCompressed(file) || file.isDirectory();//todo
            }
        };
        descriptor.setTitle("选择图片");
        FileChooser.chooseFiles(descriptor, project, project.getBaseDir(), new Consumer<List<VirtualFile>>() {
            public void consume(List<VirtualFile> virtualFiles) {
                listener.onFileChosen(selectFiles(virtualFiles));
            }
        });
    }

    public static boolean isFileCanBeCompressed(@Nullable VirtualFile virtualFile) {
        return virtualFile != null && "png".equalsIgnoreCase(virtualFile.getExtension()) && !virtualFile.getName().toLowerCase().endsWith(".9.png");
    }


    public static List<String> selectFiles(List<VirtualFile> virtualFiles) {
        List<String> data = new ArrayList();
        Iterator it = virtualFiles.iterator();

        while(true) {
            while(it.hasNext()) {
                VirtualFile virtualFile = (VirtualFile)it.next();
                if (virtualFile != null && virtualFile.isDirectory()) {
                    VirtualFile[] children = virtualFile.getChildren();
                    int size = children.length;

                    for(int index = 0; index < size; ++index) {
                        VirtualFile childFile = children[index];
                        if (childFile != null) {
                            data.add(childFile.getCanonicalPath());
                        }
                    }
                } else {
                    data.add(virtualFile.getCanonicalPath());
                }
            }

            return data;
        }
    }

    /**
     * 返回byte的数据大小对应的文本
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "b";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "kb";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "M";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "G";
        } else {
            return " error";
        }
    }

    public static List<ImageBean> covert2ImageBean(List<String> imageUrls ){
        List<ImageBean> imageBeans = new ArrayList<>();
        for(String imageUrl:imageUrls){
            File file = new File(imageUrl);
            if(file.isFile()){
                ImageBean bean = new ImageBean();
                bean.url = imageUrl;
                bean.beforeSize = file.length();
                imageBeans.add(bean);
            }
        }
        return imageBeans;
    }
}

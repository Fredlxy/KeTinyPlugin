package com.home.link.image;

import com.google.common.io.Files;
import com.home.link.util.ComponentUtil;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.tinify.Tinify;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;


public class TinyCompressFilesBackgroundTask extends Task.Backgroundable {
    public Project project;

    private List<ImageBean> imageBeans;

    public TinyCompressFilesBackgroundTask(@Nullable List<String> imageBeans, @Nullable Project project, @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title, boolean canBeCancelled) {
        super(project, title, canBeCancelled);
        this.imageBeans = ComponentUtil.covert2ImageBean(imageBeans);
    }


    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {

       boolean isValid = TinyHelper.checkTiny();
        //PluginManager.getLogger().info("压缩前tiny 服务状态：  " + isValid);

        if(!isValid){
           ComponentUtil.showNotification(project,NotificationType.ERROR,
                   "apiKey is valid",true);
            return;
       }
        //循环压缩图片
        try {
            PluginManager.getLogger().info("........start compress........");

            for (int index = 0; index < imageBeans.size(); index++) {

                ImageBean imageBean = imageBeans.get(index);
                String url = imageBean.url;
                //progressBar
                progressIndicator.setText( "compress image " + url);
                progressIndicator.setIndeterminate(false);
                progressIndicator.setFraction(5);
                //compress image
                Tinify.fromFile(url).toFile(url);
                File src = new File(url);
                File des = new File(url + "_compress.png");

                TinyHelper.minify(src,des);

                //show result
                imageBean.afterSize =des.length();
                String info = "     fileName: " + src.getName() + "    before size: " + imageBean.getBeforeSpace() + "     after size：" + imageBean.getAfterSpace() + "     save ：" + imageBean.getSaveRate();
                Files.move(des,src);

                ComponentUtil.showNotification(project,NotificationType.INFORMATION,
                        info,false);
            }
            progressIndicator.stop();
            PluginManager.getLogger().info("........compress finish........");

            //弹出成功的通知
            ComponentUtil.showNotification(project,NotificationType.INFORMATION,
                    "compress success!",true);


        } catch (Exception e) {
            e.printStackTrace();
            ComponentUtil.showNotification(project,NotificationType.ERROR,
                    "compress failure，error msg：" + e.getMessage(),true);
        }

    }

    public void runTask() {
        ProgressManager.getInstance().run(this);
    }
}

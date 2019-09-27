package com.home.link.ui;

import com.home.link.common.Constants;
import com.home.link.config.TinyPngConfigurable;
import com.home.link.image.TinyHelper;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import javax.swing.event.HyperlinkEvent;

public class ProjectNotifyComponent implements ProjectComponent {


    private Project mProject;

    private final String PROP_PROMPT_SETTINGS_IGNORE = "com.home.link.PROMPT_SETTINGS_IGNORE";

    public ProjectNotifyComponent(Project project) {
        this.mProject = project;
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "com.home.link.ui.ProjectNotifyComponent";
    }


    @Override
    public void projectOpened() {
        boolean isTinyValid = TinyHelper.checkTinyValid();
        PluginManager.getLogger().debug("tiny api valid = " + isTinyValid);
        if (!isTinyValid) {
            String notificationContent =
                    String.format("current apiKey is invalid，please set your ApiKey<br/>%s&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%s ",Constants.HTML_LINK_SETTINGS, Constants.HTML_LINK_IGNORE);
            Notification notification = new Notification(Constants.DISPLAY_GROUP_PROMPT,
                    Constants.APP_NAME, notificationContent, NotificationType.WARNING,
                    new NotificationListener.Adapter() {
                        @Override
                        protected void hyperlinkActivated(@NotNull Notification notification, @NotNull HyperlinkEvent hyperlinkEvent) {
                            notification.expire();
                            switch (hyperlinkEvent.getDescription()) {
                                case Constants.HTML_DESCRIPTION_SETTINGS:
                                    TinyPngConfigurable.showSettingsDialog(mProject);
                                    break;
                                case Constants.HTML_DESCRIPTION_IGNORE:
                                    PropertiesComponent.getInstance().setValue(PROP_PROMPT_SETTINGS_IGNORE, true);
                                    break;
                            }
                        }
                    });
            Notifications.Bus.notify(notification, mProject);
        }
    }
}




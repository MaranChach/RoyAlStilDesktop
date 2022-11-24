package com.royalstil.royalstildesktop;

import javafx.scene.Node;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ElementController {
    protected int id;

    protected HashMap<String, String> selectedRow;

    public void setSelectedRow(HashMap<String, String> selectedRow){
        this.selectedRow = selectedRow;
    }

    protected static void newNotification(Node notificationNode){
        Timer timer = new Timer("notificationTime");
        notificationNode.setVisible(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationNode.setVisible(false);
            }
        }, 3000);
    }
}

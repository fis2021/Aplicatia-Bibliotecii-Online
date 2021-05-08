package org.loose.fis.sre.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    Text labelMain;


    public void handleTransition(){


        double msgWidth = labelMain.getLayoutBounds().getWidth();
        KeyValue initKeyValue = new KeyValue(labelMain.translateXProperty(), 1600);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
        KeyValue endKeyValue = new KeyValue(labelMain.translateXProperty(), -1.0
                * msgWidth);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(10), endKeyValue);
        Timeline timeline = new Timeline(initFrame, endFrame);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handleTransition();
        Thread thread = new Thread(){
            public void run() {
                String[] messages = new String[4];
                messages[0] = "“So many books, so little time”";
                messages[1] = "“A room without books is like a body without a soul”";
                messages[2] = "“I have always imagined that Paradise will be a kind of library”";
                messages[3] = "“There is no friend as loyal as a book”";
                while (true) {
                    Random rand = new Random();
                    labelMain.setText(messages[rand.nextInt(messages.length)]);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
         thread.start();

    }


}

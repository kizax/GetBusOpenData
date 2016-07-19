/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 *
 * @author kizax
 */
public class FXMLController implements Initializable {

    @FXML
    private TextArea logTextArea;
    @FXML
    private Timer timer;
    @FXML
    private Button startButton;
    private int timeInterval;
    private boolean isStarted = false;
    private final String logFileName = "./record/log.txt";
    private FileWriter logFileWriter;

    @FXML
    private void handleStartButtonAction(ActionEvent event) {

        if (!isStarted) {

            File logFile = new File(logFileName);

            if (!logFile.getParentFile().exists()) {
                logFile.getParentFile().mkdirs();
            }

            try {
                if (!logFile.exists()) {

                    logFile.createNewFile();
                    logFileWriter = new FileWriter(logFile, true);

                } else {
                    logFileWriter = new FileWriter(logFile, true);
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Date date = new Date();
            timer = new Timer();

            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\tStart Bus Open Data Regular Downloader!", TimestampUtils.getTimestampStr()));

            timer.scheduleAtFixedRate(new GetOpenDataTask(logFileWriter, logTextArea), date, timeInterval);

            isStarted = true;
            startButton.setStyle("-fx-background-color: #cc0639;");
            startButton.setText("暫停收集資料");

        } else {
            timer.cancel();

            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\tThe timer stop.", TimestampUtils.getTimestampStr()));

            isStarted = false;
            startButton.setStyle("-fx-background-color: #00984f;");
            startButton.setText("開始收集資料");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeInterval = 25 * 1000; //in millissecond
        startButton.setStyle("-fx-background-color: #00984f;");
        startButton.setText("開始收集資料");

    }

}

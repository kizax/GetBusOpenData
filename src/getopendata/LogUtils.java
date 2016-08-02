/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.io.FileWriter;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @author kizax
 */
public class LogUtils {

    public static void log(FileWriter logFileWriter, TextArea logTextArea, String logStr) {
        Platform.runLater(() -> {
            logTextArea.appendText(logStr + "\n");
        });

        WriteThread writerThread = new WriteThread(logFileWriter, logStr);
        writerThread.start();
    }
    
        public static void clearLog(TextArea logTextArea) {
        Platform.runLater(() -> {
            logTextArea.clear();
        });

    }

}

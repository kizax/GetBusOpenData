/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.util.TimerTask;
import javafx.scene.control.TextArea;

/**
 *
 * @author kizax
 */
public class ClearLogTextAreaTask extends TimerTask {

    private final TextArea logTextArea;

    public ClearLogTextAreaTask(TextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    @Override
    public void run() {

        LogUtils.clearLog(logTextArea);

    }

}

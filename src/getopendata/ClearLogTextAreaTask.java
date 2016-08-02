/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javafx.scene.control.TextArea;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.xml.sax.SAXException;

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

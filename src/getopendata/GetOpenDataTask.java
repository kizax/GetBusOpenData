/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.xml.sax.SAXException;

/**
 *
 * @author kizax
 */
public class GetOpenDataTask extends TimerTask {

    @Override
    public void run() {

        System.out.println(String.format("%1$s\tVD data is downloading now.", TimestampUtil.getTimestampStr()));

        try {
            String url = "http://data.taipei/tisv/VDDATA";
            HttpResponse response = HttpUtil.httpGet(url);

            InputStream inputStream = response.getEntity().getContent();
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream, "UTF-8"));

            String xmlStr = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                xmlStr += (line + '\n');
            }

            ArrayList<VdData> vdDataList = VdDataXmlParser.getVdDataList(xmlStr);

            SimpleDateFormat fileTimestampFormat = new SimpleDateFormat("_yyyy-MM-dd");
            String fileTimestamp = fileTimestampFormat.format(new Date());
            String csvFileName = String.format("./record/vddata%1$s.csv", fileTimestamp);
            System.out.println(String.format("%1$s\tNow start writing data into csv file <%2$s>", TimestampUtil.getTimestampStr(), csvFileName));

            File csvDataFile = new File(csvFileName);

            if (!csvDataFile.getParentFile().exists()) {
                csvDataFile.getParentFile().mkdirs();
            }

            FileWriter csvFileWriter;
            if (!csvDataFile.exists()) {
                csvDataFile.createNewFile();
                csvFileWriter = new FileWriter(csvDataFile, true);
                writeCsvFile(csvFileWriter, "DeviceID, ExchangeTime, LaneNO, Volume, AvgSpeed, AvgOccupancy, Svolume, Mvolume, Lvolume");
            } else {
                csvFileWriter = new FileWriter(csvDataFile, true);
            }

            vdDataList.stream().forEach((vdData) -> {
                writeCsvFile(csvFileWriter, vdData.toString());
            });

            System.out.println(String.format("%1$s\tSuccessfully writing data into csv file <%2$s>", TimestampUtil.getTimestampStr(), csvFileName));

        } catch (IOException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GetOpenDataTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void writeCsvFile(FileWriter csvFileWriter, String record) {
        WriterThread writerThread = new WriterThread(csvFileWriter, record);
        writerThread.start();
    }

}

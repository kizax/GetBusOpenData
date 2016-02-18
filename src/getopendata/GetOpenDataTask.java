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
public class GetOpenDataTask extends TimerTask {

    private final TextArea logTextArea;
    private final FileWriter logFileWriter;

    public GetOpenDataTask(FileWriter logFileWriter, TextArea logTextArea) {
        this.logTextArea = logTextArea;
        this.logFileWriter = logFileWriter;
    }

    @Override
    public void run() {

        LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\tBus data is downloading now.", TimestampUtils.getTimestampStr()));

        try {
            String busDataUrl = "http://data.taipei/bus/BUSDATA";
            HttpResponse busDataHttpResponse = HttpUtils.httpGet(busDataUrl);

            String busDataJsonStr = getStrFromResponse(busDataHttpResponse);
            ArrayList<BusData> busDataList = BusDataJsonParser.getBusDataList(busDataJsonStr);

            String busEventDataUrl = "http://data.taipei/bus/BUSEVENT";
            String routeDataUrl = "http://data.taipei/bus/ROUTE";
            String stopLocationDataUrl = "http://data.taipei/bus/Stop";
            String providerDataUrl = "http://data.taipei/bus/PROVIDER";

            HttpResponse busEventDataHttpResponse = HttpUtils.httpGet(busEventDataUrl);
            HttpResponse routeDataHttpResponse = HttpUtils.httpGet(routeDataUrl);
            HttpResponse stopLocationHttpResponse = HttpUtils.httpGet(stopLocationDataUrl);
            HttpResponse providerDataHttpResponse = HttpUtils.httpGet(providerDataUrl);

            String busEventDataJsonStr = getStrFromResponse(busEventDataHttpResponse);
            ArrayList<BusEventData> busEventDataList = BusDataJsonParser.getBusEventDataList(busEventDataJsonStr);

            String routeDataJsonStr = getStrFromResponse(routeDataHttpResponse);
            ArrayList<RouteData> routeDataList = BusDataJsonParser.getRouteDataList(routeDataJsonStr);

            String stopLocationDataJsonStr = getStrFromResponse(stopLocationHttpResponse);
            ArrayList<StopLocationData> stopLocationDataList = BusDataJsonParser.getStopLocationDataList(stopLocationDataJsonStr);

            String providerDataJsonStr = getStrFromResponse(providerDataHttpResponse);
            ArrayList<ProviderData> providerDataList = BusDataJsonParser.getProviderDataList(providerDataJsonStr);

            //塞入stopId
            Map<Double, BusEventData> busEventDataTreeMap = new TreeMap<>(new DoubleComparator());
            busEventDataList.stream().forEach((busEventData) -> {
                busEventDataTreeMap.put(busEventData.getCarId(), busEventData);
            });

            ArrayList<BusData> tempBusDataList = new ArrayList<>();
            for (BusData busData : busDataList) {
                if (busEventDataTreeMap.containsKey(busData.getCarId())) {
                    BusEventData busEventData = busEventDataTreeMap.get(busData.getCarId());
                    busData.setStopId(busEventData.getStopId());
                    tempBusDataList.add(busData);
                } else {
                    tempBusDataList.add(busData);
                }
            }
            busDataList = new ArrayList<>(tempBusDataList);

            //塞入stopLocationName
            Map<Double, StopLocationData> stopLocationDataTreeMap = new TreeMap<>(new DoubleComparator());
            stopLocationDataList.stream().forEach((stopLocationData) -> {
                stopLocationDataTreeMap.put(stopLocationData.getStopId(), stopLocationData);
            });

            tempBusDataList = new ArrayList<>();
            for (BusData busData : busDataList) {
                if (stopLocationDataTreeMap.containsKey((double) busData.getStopId())) {
                    StopLocationData stopLocationData = stopLocationDataTreeMap.get((double) busData.getStopId());
                    busData.setStopLocationName(stopLocationData.getStopLocationName());
                    tempBusDataList.add(busData);
                } else {
                    tempBusDataList.add(busData);
                }
            }
            busDataList = new ArrayList<>(tempBusDataList);

            //塞入routeName
            Map<Double, RouteData> routeDataTreeMap = new TreeMap<>(new DoubleComparator());
            routeDataList.stream().forEach((routeData) -> {
                routeDataTreeMap.put(routeData.getRouteId(), routeData);
            });

            tempBusDataList = new ArrayList<>();
            for (BusData busData : busDataList) {
                if (routeDataTreeMap.containsKey(busData.getRouteId())) {
                    RouteData routeData = routeDataTreeMap.get(busData.getRouteId());
                    busData.setRouteName(routeData.getRouteName());
                    tempBusDataList.add(busData);
                } else {
                    tempBusDataList.add(busData);
                }
            }
            busDataList = new ArrayList<>(tempBusDataList);

            //塞入providerName
            Map<Double, ProviderData> providerDataTreeMap = new TreeMap<>(new DoubleComparator());
            providerDataList.stream().forEach((providerData) -> {
                providerDataTreeMap.put(providerData.getProviderId(), providerData);
            });

            tempBusDataList = new ArrayList<>();
            for (BusData busData : busDataList) {
                if (providerDataTreeMap.containsKey(busData.getProviderId())) {
                    ProviderData providerData = providerDataTreeMap.get(busData.getProviderId());
                    busData.setProviderName(providerData.getProviderName());
                    tempBusDataList.add(busData);
                } else {
                    tempBusDataList.add(busData);
                }
            }
            busDataList = new ArrayList<>(tempBusDataList);

            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\tNum of bus data: %2$d", TimestampUtils.getTimestampStr(), busDataList.size()));

            SimpleDateFormat fileTimestampFormat = new SimpleDateFormat("_yyyy-MM-dd_HH00");
            String fileTimestamp = fileTimestampFormat.format(new Date());
            String csvFileName = String.format("./record/busdata%1$s.csv", fileTimestamp);
            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\tNow start writing data into db", TimestampUtils.getTimestampStr()));

            File csvDataFile = new File(csvFileName);

            if (!csvDataFile.getParentFile().exists()) {
                csvDataFile.getParentFile().mkdirs();
            }

            BusDataDaoImpl busDataDaoImpl = new BusDataDaoImpl();

            busDataDaoImpl.add(busDataList);

            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\tSuccessfully writing data into busdb", TimestampUtils.getTimestampStr()));

        } catch (IOException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\t%2$s", TimestampUtils.getTimestampStr(), ex));
        } catch (SAXException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\t%2$s", TimestampUtils.getTimestampStr(), ex));
        } catch (ParseException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\t%2$s", TimestampUtils.getTimestampStr(), ex));
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GetOpenDataTask.class.getName()).log(Level.SEVERE, null, ex);
            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\t%2$s", TimestampUtils.getTimestampStr(), ex));
        } catch (JSONException ex) {
            Logger.getLogger(GetOpenDataTask.class.getName()).log(Level.SEVERE, null, ex);
            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\t%2$s", TimestampUtils.getTimestampStr(), ex));
        } catch (SQLException ex) {
            Logger.getLogger(GetOpenDataTask.class.getName()).log(Level.SEVERE, null, ex);
            LogUtils.log(logFileWriter, logTextArea, String.format("%1$s\t%2$s", TimestampUtils.getTimestampStr(), ex));
        }

    }

    private void writeCsvFile(FileWriter csvFileWriter, String record) {
        WriteThread writerThread = new WriteThread(csvFileWriter, record);
        writerThread.start();
    }

    private String getStrFromResponse(HttpResponse response) throws IOException {
        InputStream inputStream = response.getEntity().getContent();
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream, "UTF-8"));

        String jsonStr = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            jsonStr += (line + '\n');
        }
        return jsonStr;
    }

}

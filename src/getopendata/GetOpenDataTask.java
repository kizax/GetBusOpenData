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
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.xml.sax.SAXException;

/**
 *
 * @author kizax
 */
public class GetOpenDataTask extends TimerTask {

    @Override
    public void run() {

        System.out.println(String.format("%1$s\tBus data is downloading now.", TimestampUtils.getTimestampStr()));

        try {
            String busDataUrl = "http://data.taipei/bus/BUSDATA";
            HttpResponse busDataHttpResponse = HttpUtils.httpGet(busDataUrl);

            String busDataJsonStr = getStrFromResponse(busDataHttpResponse);
            ArrayList<BusData> tempBusDataList = BusDataJsonParser.getBusDataList(busDataJsonStr);

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

            ArrayList<BusData> busDataList = new ArrayList<>(tempBusDataList);
            for (BusData busData : tempBusDataList) {
                if (busEventDataTreeMap.containsKey(busData.getCarId())) {
                    BusEventData busEventData = busEventDataTreeMap.get(busData.getCarId());
                    busData.setStopId(busEventData.getStopId());
                    busDataList.add(busData);
                } else {
                    busDataList.add(busData);
                }
            }
            tempBusDataList = new ArrayList<>(busDataList);

            //塞入stopLocationName
            Map<Double, StopLocationData> stopLocationDataTreeMap = new TreeMap<>(new DoubleComparator());
            stopLocationDataList.stream().forEach((stopLocationData) -> {
                stopLocationDataTreeMap.put(stopLocationData.getStopId(), stopLocationData);
            });

            busDataList = new ArrayList<>(tempBusDataList);
            for (BusData busData : tempBusDataList) {
                if (stopLocationDataTreeMap.containsKey((double) busData.getStopId())) {
                    StopLocationData stopLocationData = stopLocationDataTreeMap.get((double) busData.getStopId());
                    busData.setStopLocationName(stopLocationData.getStopLocationName());
                    busDataList.add(busData);
                } else {
                    busDataList.add(busData);
                }
            }
            tempBusDataList = new ArrayList<>(busDataList);

            //塞入routeName
            Map<Double, RouteData> routeDataTreeMap = new TreeMap<>(new DoubleComparator());
            routeDataList.stream().forEach((routeData) -> {
                routeDataTreeMap.put(routeData.getRouteId(), routeData);
            });

            busDataList = new ArrayList<>(tempBusDataList);
            for (BusData busData : tempBusDataList) {
                if (routeDataTreeMap.containsKey(busData.getRouteId())) {
                    RouteData routeData = routeDataTreeMap.get(busData.getRouteId());
                    busData.setRouteName(routeData.getRouteName());
                    busDataList.add(busData);
                } else {
                    busDataList.add(busData);
                }
            }
            tempBusDataList = new ArrayList<>(busDataList);

            //塞入providerName
            Map<Double, ProviderData> providerDataTreeMap = new TreeMap<>(new DoubleComparator());
            providerDataList.stream().forEach((providerData) -> {
                providerDataTreeMap.put(providerData.getProviderId(), providerData);
            });

            busDataList = new ArrayList<>(tempBusDataList);
            for (BusData busData : tempBusDataList) {
                if (providerDataTreeMap.containsKey(busData.getProviderId())) {
                    ProviderData providerData = providerDataTreeMap.get(busData.getProviderId());
                    busData.setProviderName(providerData.getProviderName());
                    busDataList.add(busData);
                } else {
                    busDataList.add(busData);
                }
            }
            tempBusDataList = new ArrayList<>(busDataList);

            SimpleDateFormat fileTimestampFormat = new SimpleDateFormat("_yyyy-MM-dd");
            String fileTimestamp = fileTimestampFormat.format(new Date());
            String csvFileName = String.format("./record/busdata%1$s.csv", fileTimestamp);
            System.out.println(String.format("%1$s\tNow start writing data into csv file <%2$s>", TimestampUtils.getTimestampStr(), csvFileName));

            File csvDataFile = new File(csvFileName);

            if (!csvDataFile.getParentFile().exists()) {
                csvDataFile.getParentFile().mkdirs();
            }

            FileWriter csvFileWriter;
            if (!csvDataFile.exists()) {
                csvDataFile.createNewFile();
                csvFileWriter = new FileWriter(csvDataFile, true);
                writeCsvFile(csvFileWriter, " DataTime,  CarType, ProviderID, ProviderName, CarID, BusId, DutyStatus, BusStatus, RouteID, RouteName,  GoBack, Longitude,  Latitude, Speed, Azimuth, StopID, StopLocationName");
            } else {
                csvFileWriter = new FileWriter(csvDataFile, true);
            }

            BusDataDaoImpl busDataDaoImpl = new BusDataDaoImpl();

            for (BusData busData : busDataList) {
                writeCsvFile(csvFileWriter, busData.toString());
                busDataDaoImpl.add(busData);
            }

            System.out.println(String.format("%1$s\tSuccessfully writing data into csv file <%2$s>", TimestampUtils.getTimestampStr(), csvFileName));

        } catch (IOException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(OpenDataRegularDownloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GetOpenDataTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(GetOpenDataTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetOpenDataTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void writeCsvFile(FileWriter csvFileWriter, String record) {
        WriterThread writerThread = new WriterThread(csvFileWriter, record);
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

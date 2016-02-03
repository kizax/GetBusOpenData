/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kizax
 */
public class BusDataJsonParser {

    public static ArrayList<BusData> getBusDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<BusData> busDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray busInfoJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < busInfoJsonArray.length(); i++) {
            JSONObject busJsonObj = (JSONObject) busInfoJsonArray.get(i);

            double providerId = (Double) busJsonObj.get("ProviderID");
            String busId = busJsonObj.getString("BusID");
            int carType = busJsonObj.getInt("CarType");
            double carId = (Double) busJsonObj.get("CarID");
            int dutyStatus = busJsonObj.getInt("DutyStatus");

            String tempStr = (String) busJsonObj.get("BusStatus");
            int busStatus = (tempStr == null ? 0 : tempStr.trim().isEmpty() ? 0 : Integer.parseInt(tempStr));

            double routeId = (Double) busJsonObj.get("RouteID");
            int goBack = busJsonObj.getInt("GoBack");
            double longitude = (Double) busJsonObj.get("Longitude");
            double latitude = (Double) busJsonObj.get("Latitude");
            double speed = (Double) busJsonObj.get("Speed");
            double azimuth = (Double) busJsonObj.get("Azimuth");

            int stopId = 0;
            String stopLocationName = "";
            String routeName = "";
            String providerName = "";

            Date dataTime = null;
            String dataTimeStr = busJsonObj.getString("DataTime");
            Pattern pattern = Pattern.compile("(\\d){13}");
            Matcher matcher = pattern.matcher(dataTimeStr);
            if (matcher.find()) {
                long dateTimeLong = Long.parseLong(matcher.group(0));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dateTimeLong);
                dataTime = calendar.getTime();
            }

            BusData busData = new BusData(providerId, busId, carType, carId, dutyStatus, busStatus, routeId, goBack, longitude, latitude, speed, azimuth, dataTime, stopId, stopLocationName, routeName, providerName);
            System.out.println(busData.toString());
            busDataList.add(busData);
        }
        System.out.println(String.format("%1$s\tNum of bus data rows: %2$d", TimestampUtil.getTimestampStr(), busDataList.size()));

        return busDataList;
    }

    public static ArrayList<BusEventData> getBusEventDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<BusEventData> busEventDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray busInfoJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < busInfoJsonArray.length(); i++) {
            JSONObject busJsonObj = (JSONObject) busInfoJsonArray.get(i);

            double providerId = (Double) busJsonObj.get("ProviderID");
            String busId = busJsonObj.getString("BusID");
            int carType = busJsonObj.getInt("CarType");
            double carId = (Double) busJsonObj.get("CarID");
            int dutyStatus = busJsonObj.getInt("DutyStatus");
            int busStatus = busJsonObj.getInt("BusStatus");
            double routeId = (Double) busJsonObj.get("RouteID");
            int goBack = busJsonObj.getInt("GoBack");
            int stopId = busJsonObj.getInt("StopID");
            int carOnStop = busJsonObj.getInt("CarOnStop");

            Date dataTime = null;
            String dataTimeStr = busJsonObj.getString("DataTime");
            Pattern pattern = Pattern.compile("(\\d){13}");
            Matcher matcher = pattern.matcher(dataTimeStr);
            if (matcher.find()) {
                long dateTimeLong = Long.parseLong(matcher.group(0));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dateTimeLong);
                dataTime = calendar.getTime();
            }

            BusEventData busEventData = new BusEventData(providerId, busId, carType, carId, dutyStatus, busStatus, routeId, goBack, stopId, carOnStop, dataTime);
            System.out.println(busEventData.toString());
            busEventDataList.add(busEventData);
        }
        System.out.println(String.format("%1$s\tNum of bus event data rows: %2$d", TimestampUtil.getTimestampStr(), busEventDataList.size()));
        return busEventDataList;
    }

    public static ArrayList<RouteData> getRouteDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<RouteData> routeDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject kmlJsonObject = jsonObject.getJSONObject("kml");
        JSONObject folderJsonObject = kmlJsonObject.getJSONObject("Folder");
        JSONArray routeDataJsonArray = folderJsonObject.getJSONArray("Placemark");
        for (int i = 0; i < routeDataJsonArray.length(); i++) {
            JSONObject routeJsonObj = (JSONObject) routeDataJsonArray.get(i);

            int routeId = routeJsonObj.getInt("@id");
            String routeName = routeJsonObj.getString("name");

            RouteData routeData = new RouteData(routeId, routeName);
            System.out.println(routeData.toString());
            routeDataList.add(routeData);
        }
        System.out.println(String.format("%1$s\tNum of route data rows: %2$d", TimestampUtil.getTimestampStr(), routeDataList.size()));
        return routeDataList;
    }

    public static ArrayList<StopLocationData> getStopLocationDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<StopLocationData> stopLocationDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray stopLocationDataJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < stopLocationDataJsonArray.length(); i++) {
            JSONObject stopLocationJsonObj = (JSONObject) stopLocationDataJsonArray.get(i);

            double stopId = (double)stopLocationJsonObj.get("id");
            String stopLocationName = stopLocationJsonObj.getString("name");

            StopLocationData stopLocationData = new StopLocationData(stopId, stopLocationName);
            System.out.println(stopLocationData.toString());
            stopLocationDataList.add(stopLocationData);
        }
        System.out.println(String.format("%1$s\tNum of route data rows: %2$d", TimestampUtil.getTimestampStr(), stopLocationDataList.size()));
        return stopLocationDataList;
    }
    
    
        public static ArrayList<ProviderData> getProviderDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<ProviderData> providerDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray providerDataJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < providerDataJsonArray.length(); i++) {
            JSONObject stopLocationJsonObj = (JSONObject) providerDataJsonArray.get(i);

            double stopId = (double) stopLocationJsonObj.get("id");
            String stopLocationName = stopLocationJsonObj.getString("nameZn");

            ProviderData providerData = new ProviderData(stopId, stopLocationName);
            System.out.println(providerData.toString());
            providerDataList.add(providerData);
        }
        System.out.println(String.format("%1$s\tNum of route data rows: %2$d", TimestampUtil.getTimestampStr(), providerDataList.size()));
        return providerDataList;
    }

}

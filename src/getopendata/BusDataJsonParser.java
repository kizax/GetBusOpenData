/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

            int tempProviderID = (Integer)busJsonObj.get("ProviderID");
            double providerId = (double) tempProviderID;
            String busId = busJsonObj.getString("BusID");
            int carType = busJsonObj.getInt("CarType");
            int tempCarID = (Integer)busJsonObj.get("CarID");
            double carId = (double) tempCarID;
            int dutyStatus = busJsonObj.getInt("DutyStatus");

            String tempStr = (String) busJsonObj.get("BusStatus");
            int busStatus = (tempStr == null ? 0 : tempStr.trim().isEmpty() ? 0 : Integer.parseInt(tempStr));

            String tempRouteId = (String) busJsonObj.get("RouteID");
            double routeId = (double) Double.parseDouble(tempRouteId);
            int goBack = busJsonObj.getInt("GoBack");
            
            String tempLongitude = (String) busJsonObj.get("Longitude");
            double longitude =  (double) Double.parseDouble(tempLongitude);
            
            String tempLatitude = (String) busJsonObj.get("Latitude");
            double latitude = (Double) (double) Double.parseDouble(tempLatitude);
            
            String tempSpeed = (String) busJsonObj.get("Speed");
            double speed = (Double) (double) Double.parseDouble(tempSpeed);
            
            String tempAzimuth = (String) busJsonObj.get("Azimuth");
            double azimuth = (Double) Double.parseDouble(tempAzimuth);

            int stopId = 0;
            String stopLocationName = "";
            String routeName = "";
            String providerName = "";

            String dataTimeStr = (String)busJsonObj.getString("DataTime");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dataTime =  df.parse(dataTimeStr);  

            BusData busData = new BusData(providerId, busId, carType, carId, dutyStatus, busStatus, routeId, goBack, longitude, latitude, speed, azimuth, dataTime, stopId, stopLocationName, routeName, providerName);
            busDataList.add(busData);
        }
      

        return busDataList;
    }

    public static ArrayList<BusEventData> getBusEventDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<BusEventData> busEventDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray busInfoJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < busInfoJsonArray.length(); i++) {
            JSONObject busJsonObj = (JSONObject) busInfoJsonArray.get(i);

            int tempProviderID = (Integer)busJsonObj.get("ProviderID");
            double providerId = (double) tempProviderID;
            
            String busId = busJsonObj.getString("BusID");
            int carType = busJsonObj.getInt("CarType");
            
            int tempCarID = (Integer)busJsonObj.get("CarID");
            double carId = (double) tempCarID;
            
            int dutyStatus = busJsonObj.getInt("DutyStatus");
            int busStatus = busJsonObj.getInt("BusStatus");
            
            String tempRouteId = (String) busJsonObj.get("RouteID");
            double routeId = (double) Double.parseDouble(tempRouteId);
            
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
            busEventDataList.add(busEventData);
        }
        return busEventDataList;
    }

    public static ArrayList<RouteData> getRouteDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<RouteData> routeDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray routeDataJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < routeDataJsonArray.length(); i++) {
            JSONObject routeJsonObj = (JSONObject) routeDataJsonArray.get(i);

            int routeId = routeJsonObj.getInt("pathAttributeId");
            String routeName = routeJsonObj.getString("pathAttributeName");

            RouteData routeData = new RouteData(routeId, routeName);

            routeDataList.add(routeData);
        }

        return routeDataList;
    }

    public static ArrayList<StopLocationData> getStopLocationDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<StopLocationData> stopLocationDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray stopLocationDataJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < stopLocationDataJsonArray.length(); i++) {
            JSONObject stopLocationJsonObj = (JSONObject) stopLocationDataJsonArray.get(i);

            int tempStopId = (Integer) stopLocationJsonObj.get("Id");
            double stopId = (double)tempStopId;
            String stopLocationName = stopLocationJsonObj.getString("nameZh");

            StopLocationData stopLocationData = new StopLocationData(stopId, stopLocationName);

            stopLocationDataList.add(stopLocationData);
        }

        return stopLocationDataList;
    }

    public static ArrayList<ProviderData> getProviderDataList(String jsonStr) throws SAXException, IOException, ParseException, ParserConfigurationException, JSONException {

        ArrayList<ProviderData> providerDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray providerDataJsonArray = jsonObject.getJSONArray("BusInfo");
        for (int i = 0; i < providerDataJsonArray.length(); i++) {
            JSONObject stopLocationJsonObj = (JSONObject) providerDataJsonArray.get(i);

            int tempStopId = (Integer) stopLocationJsonObj.get("id");
            double stopId = (double)tempStopId;
            String stopLocationName = stopLocationJsonObj.getString("nameZn");

            ProviderData providerData = new ProviderData(stopId, stopLocationName);

            providerDataList.add(providerData);
        }

        return providerDataList;
    }

}

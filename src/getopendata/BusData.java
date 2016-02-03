/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author kizax
 */
public class BusData {

    private final double providerId;
    private final String busId;
    private final int carType;
    private final double carId;
    private final int dutyStatus;
    private final int busStatus;
    private final double routeId;
    private final int goBack;
    private final double longitude;
    private final double latitude;
    private final double speed;
    private final double azimuth;
    private final Date dataTime;
    private int stopId;
    private String stopLocationName;
    private String routeName;
    private String providerName;

    public BusData(double providerId, String busId, int carType, double carId, int dutyStatus, int busStatus, double routeId, int goBack, double longitude, double latitude, double speed, double azimuth, Date dataTime, int stopId, String stopLocationName, String routeName, String providerName) {

        this.providerId = providerId;
        this.busId = busId;
        this.carType = carType;
        this.carId = carId;
        this.dutyStatus = dutyStatus;
        this.busStatus = busStatus;
        this.routeId = routeId;
        this.goBack = goBack;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
        this.azimuth = azimuth;
        this.dataTime = dataTime;
        this.stopId = stopId;
        this.stopLocationName = stopLocationName;
        this.routeName = routeName;
        this.providerName = providerName;

    }

    @Override
    public String toString() {
        String vdDataStr = String.format("%1$s, %2$d, %3$.1f, %4$s, %5$.1f, %6$s, %7$d, %8$d, %9$.1f, %10$s, %11$d, %12$.6f, %13$.6f, %14$.6f, %15$.6f, %16$d, %17$s", this.getTimeStr(), carType, providerId, getProviderName(), carId, busId, dutyStatus, busStatus, routeId, getRouteName(), goBack, longitude, latitude, speed, azimuth, stopId, getStopLocationName());
        return vdDataStr;
    }

    String getTimeStr() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-01-15 00:00:00
        String timeStr = timeFormat.format(getDataTime());
        return timeStr;
    }

    /**
     * @return the providerID
     */
    public double getProviderId() {
        return providerId;
    }

    /**
     * @return the busID
     */
    public String getBusId() {
        return busId;
    }

    /**
     * @return the carType
     */
    public int getCarType() {
        return carType;
    }

    /**
     * @return the carId
     */
    public double getCarId() {
        return carId;
    }

    /**
     * @return the dutyStatus
     */
    public int getDutyStatus() {
        return dutyStatus;
    }

    /**
     * @return the busStatus
     */
    public int getBusStatus() {
        return busStatus;
    }

    /**
     * @return the routeID
     */
    public double getRouteId() {
        return routeId;
    }

    /**
     * @return the goBack
     */
    public int getGoBack() {
        return goBack;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return the azimuth
     */
    public double getAzimuth() {
        return azimuth;
    }

    /**
     * @return the dataTime
     */
    public Date getDataTime() {
        return dataTime;
    }

    /**
     * @return the stopId
     */
    public int getStopId() {
        return stopId;
    }

    /**
     * @param stopId the stopId to set
     */
    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    /**
     * @param stopLocationName the stopLocationName to set
     */
    public void setStopLocationName(String stopLocationName) {
        this.stopLocationName = stopLocationName;
    }

    /**
     * @param routeName the routeName to set
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * @param providerName the providerName to set
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * @return the stopLocationName
     */
    public String getStopLocationName() {
        return stopLocationName;
    }

    /**
     * @return the routeName
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * @return the providerName
     */
    public String getProviderName() {
        return providerName;
    }

}

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
    private final int stopId;

    public BusData(double providerId, String busId, int carType, double carId, int dutyStatus, int busStatus, double routeId, int goBack, double longitude, double latitude, double speed, double azimuth, Date dataTime, int stopId) {

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

    }

    @Override
    public String toString() {
        String vdDataStr = String.format("%1$s, %2$s, %3$d, %4$.1f, %5$.1f, %6$d, %7$d, %8$.1f, %9$d, %10$.6f, %11$.6f, %12$.1f, %13$.1f, %14$d", busId, this.getTimeStr(), carType, providerId, carId, dutyStatus, busStatus, routeId, goBack, longitude, latitude, speed, azimuth, stopId);
        return vdDataStr;
    }

    private String getTimeStr() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-01-15 00:00:00
        String timeStr = timeFormat.format(getDataTime());
        return timeStr;
    }

    /**
     * @return the providerID
     */
    public double getProviderID() {
        return providerId;
    }

    /**
     * @return the busID
     */
    public String getBusID() {
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
    public double getRouteID() {
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

}

package getopendata;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kizax
 */
public class BusEventData {

    private final double providerId;
    private final String busId;
    private final int carType;
    private final double carId;
    private final int dutyStatus;
    private final int busStatus;
    private final double routeId;
    private final int goBack;
    private final Date dataTime;
    private final int stopId;
    private final int carOnStop;

    /**
     *
     * @param providerId
     * @param busId
     * @param carType
     * @param carId
     * @param dutyStatus
     * @param busStatus
     * @param routeId
     * @param goBack
     * @param stopId
     * @param carOnStop
     * @param dataTime
     */
    public BusEventData(double providerId, String busId, int carType, double carId, int dutyStatus, int busStatus, double routeId, int goBack, int stopId, int carOnStop, Date dataTime) {

        this.providerId = providerId;
        this.busId = busId;
        this.carType = carType;
        this.carId = carId;
        this.dutyStatus = dutyStatus;
        this.busStatus = busStatus;
        this.routeId = routeId;
        this.goBack = goBack;
        this.stopId = stopId;
        this.carOnStop = carOnStop;
        this.dataTime = dataTime;

    }

    @Override
    public String toString() {
        String vdDataStr = String.format("%1$s, %2$s, %3$d, %4$.1f, %5$.1f, %6$d, %7$d, %8$.1f, %9$d, %10$d, %11$d", getBusId(), this.getTimeStr(), getCarType(), getProviderId(), getCarId(), getDutyStatus(), getBusStatus(), getRouteId(), getGoBack(), getStopId(), getCarOnStop());
        return vdDataStr;
    }

    private String getTimeStr() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-01-15 00:00:00
        String timeStr = timeFormat.format(getDataTime());
        return timeStr;
    }

    /**
     * @return the providerId
     */
    public double getProviderId() {
        return providerId;
    }

    /**
     * @return the busId
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
     * @return the routeId
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
     * @return the carOnStop
     */
    public int getCarOnStop() {
        return carOnStop;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

/**
 *
 * @author kizax
 */
public class StopLocationData {

    private final double stopId;
    private final String stopLocationName;

    StopLocationData(double stopId, String stopLocationName) {
        this.stopId = stopId;
        this.stopLocationName = stopLocationName;
    }

    @Override
    public String toString() {
        String vdDataStr = String.format("%1$.1f, %2$s", stopId, stopLocationName);
        return vdDataStr;
    }

    /**
     * @return the stopId
     */
    public double getStopId() {
        return stopId;
    }

    /**
     * @return the stopLocationName
     */
    public String getStopLocationName() {
        return stopLocationName;
    }

}

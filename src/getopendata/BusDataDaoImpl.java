/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kizax
 */
public class BusDataDaoImpl implements BusDataDao {

    @Override
    public void add(ArrayList<BusData> busDataList) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        StringBuffer sql = new StringBuffer("INSERT INTO busdata (datatime,cartype,providerid,providername,carid,busid,dutystatus,busstatus,routeid,routename,goback,longitude,latitude,speed,azimuth,stopid,stopLocationName) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        try {
            conn = DbUtils.getConnection();

            for (int i = 0; i < busDataList.size() - 1; i++) {
                sql.append(", (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            }

            preparedStatement = conn.prepareStatement(sql.toString());

            for (int i = 0; i < busDataList.size(); i++) {

                BusData busData = busDataList.get(i);
                preparedStatement.setString(i * 17 + 1, busData.getTimeStr());
                preparedStatement.setInt(i * 17 + 2, busData.getCarType());
                preparedStatement.setDouble(i * 17 + 3, busData.getProviderId());
                preparedStatement.setString(i * 17 + 4, busData.getProviderName());
                preparedStatement.setDouble(i * 17 + 5, busData.getCarId());

                preparedStatement.setString(i * 17 + 6, busData.getBusId());
                preparedStatement.setInt(i * 17 + 7, busData.getDutyStatus());
                preparedStatement.setInt(i * 17 + 8, busData.getBusStatus());
                preparedStatement.setDouble(i * 17 + 9, busData.getRouteId());
                preparedStatement.setString(i * 17 + 10, busData.getRouteName());

                preparedStatement.setInt(i * 17 + 11, busData.getGoBack());
                preparedStatement.setDouble(i * 17 + 12, busData.getLongitude());
                preparedStatement.setDouble(i * 17 + 13, busData.getLatitude());
                preparedStatement.setDouble(i * 17 + 14, busData.getSpeed());
                preparedStatement.setDouble(i * 17 + 15, busData.getAzimuth());

                preparedStatement.setInt(i * 17 + 16, busData.getStopId());
                preparedStatement.setString(i * 17 + 17, busData.getStopLocationName());
            }

            if (busDataList.size() > 0) {
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Insertion fail");
        } finally {
            DbUtils.close(null, preparedStatement, conn);
        }
    }

    @Override
    public BusData getLatestBusData(int carid) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM busdb.busdata where carid = ? order by datatime DESC Limit 1";

        BusData busData = null;
        try {

            conn = DbUtils.getConnection();
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, carid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                double providerId = (double) resultSet.getFloat("providerid");
                String busId = resultSet.getString("busid");
                int carType = resultSet.getInt("cartype");
                double carId = (double) resultSet.getInt("carid");
                int dutyStatus = resultSet.getInt("dutystatus");
                int busStatus = resultSet.getInt("busstatus");
                double routeId = (double) resultSet.getFloat("routeid");
                int goBack = resultSet.getInt("goback");
                double longitude = (double) resultSet.getFloat("longitude");
                double latitude = (double) resultSet.getFloat("latitude");
                double speed = (double) resultSet.getFloat("speed");
                double azimuth = (double) resultSet.getFloat("azimuth");
                Date dataTime = new Date(resultSet.getTimestamp("datatime").getTime());
                int stopId = resultSet.getInt("stopid");
                String stopLocationName = resultSet.getString("stopLocationName");
                String routeName = resultSet.getString("routename");
                String providerName = resultSet.getString("providername");

                busData = new BusData(providerId, busId, carType, carId, dutyStatus, busStatus, routeId, goBack, longitude, latitude, speed, azimuth, dataTime, stopId, stopLocationName, routeName, providerName);
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BusDataDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.close(null, preparedStatement, conn);
        }

        return busData;

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author kizax
 */
public class BusDataDaoImpl implements BusDataDao {

    @Override
    public void add(BusData busData) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO busdata VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DbUtils.getConnection();
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, busData.getTimeStr());
            preparedStatement.setInt(2, busData.getCarType());
            preparedStatement.setDouble(3, busData.getProviderId());
            preparedStatement.setString(4, busData.getProviderName());
            preparedStatement.setDouble(5, busData.getCarId());

            preparedStatement.setString(6, busData.getBusId());
            preparedStatement.setInt(7, busData.getDutyStatus());
            preparedStatement.setInt(8, busData.getBusStatus());
            preparedStatement.setDouble(9, busData.getRouteId());
            preparedStatement.setString(10, busData.getRouteName());

            preparedStatement.setInt(11, busData.getGoBack());
            preparedStatement.setDouble(12, busData.getLongitude());
            preparedStatement.setDouble(13, busData.getLatitude());
            preparedStatement.setDouble(14, busData.getSpeed());
            preparedStatement.setDouble(15, busData.getAzimuth());

            preparedStatement.setInt(16, busData.getStopId());
            preparedStatement.setString(17, busData.getStopLocationName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Insertion fail");
        } finally {
            DbUtils.close(null, preparedStatement, conn);
        }
    }

}

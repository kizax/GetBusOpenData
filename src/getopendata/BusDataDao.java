/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author kizax
 */
public interface BusDataDao {

    public void add(ArrayList<BusData> busDataList) throws SQLException;

    public TreeMap<Integer, BusData> getLatestBusDataMap();
}

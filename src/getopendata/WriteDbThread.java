package getopendata;


import getopendata.BusData;
import getopendata.BusDataDaoImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kizax
 */
public class WriteDbThread extends Thread {

    private final BusDataDaoImpl busDataDaoImpl;
    private final BusData busData;

    public WriteDbThread(BusData busData) {
        this.busDataDaoImpl = new BusDataDaoImpl();
        this.busData = busData;
    }

    @Override
    public void run() {
        super.run();
        try {
            busDataDaoImpl.add(busData);
        } catch (SQLException ex) {
            Logger.getLogger(WriteDbThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

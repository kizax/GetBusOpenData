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
public class RouteData {

    private final int routeId;
    private final String routeName;

    public RouteData(int routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }

    @Override
    public String toString() {
        String vdDataStr = String.format("%1$d, %2$s", routeId, routeName);
        return vdDataStr;
    }

    /**
     * @return the routeId
     */
    public int getRouteId() {
        return routeId;
    }

    /**
     * @return the routeName
     */
    public String getRouteName() {
        return routeName;
    }

}

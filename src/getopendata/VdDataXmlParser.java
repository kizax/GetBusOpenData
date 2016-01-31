/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getopendata;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author kizax
 */
public class VdDataXmlParser {

    public static ArrayList<VdData> getVdDataList(String xmlStr) throws SAXException, IOException, ParseException, ParserConfigurationException {

        ArrayList<VdData> vdDataList = new ArrayList<>();

        DocumentBuilderFactory factory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new StringBufferInputStream(xmlStr));

        String exchangeTimeStr = doc.getElementsByTagName("ExchangeTime").item(0)
                .getTextContent();

        SimpleDateFormat exchangeTimeStrFormat = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss"); //2016/01/30T12:12:21

        Date exchangeTime = (Date) exchangeTimeStrFormat.parse(exchangeTimeStr);

        NodeList nodeList = doc.getElementsByTagName("VDDevice");
        System.out.println(String.format("%1$s\tNum of VD device: %2$d", TimestampUtil.getTimestampStr(), nodeList.getLength()));

        for (int nodeCount = 0; nodeCount < nodeList.getLength(); nodeCount++) {

            Node node = nodeList.item(nodeCount);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                String deviceID = element.getElementsByTagName("DeviceID")
                        .item(0)
                        .getTextContent();

                String timeInterval = element.getElementsByTagName("TimeInterval")
                        .item(0)
                        .getTextContent();

                int totalLaneNum = Integer.parseInt(element.getElementsByTagName("TotalOfLane").item(0)
                        .getTextContent());

                for (int i = 0; i < totalLaneNum; i++) {

                    int laneNO = Integer.parseInt(element.getElementsByTagName("LaneNO")
                            .item(i)
                            .getTextContent());

                    double volume = Double.parseDouble(element.getElementsByTagName("Volume")
                            .item(i)
                            .getTextContent());

                    double avgSpeed = Double.parseDouble(element.getElementsByTagName("AvgSpeed")
                            .item(i)
                            .getTextContent());

                    double avgOccupancy = Double.parseDouble(element.getElementsByTagName("AvgOccupancy")
                            .item(i)
                            .getTextContent());

                    double sVolume = Double.parseDouble(element.getElementsByTagName("Svolume")
                            .item(i)
                            .getTextContent());

                    double mVolume = Double.parseDouble(element.getElementsByTagName("Mvolume")
                            .item(i)
                            .getTextContent());

                    double lVolume = Double.parseDouble(element.getElementsByTagName("Lvolume")
                            .item(i)
                            .getTextContent());

                    VdData vdData = new VdData(deviceID, exchangeTime, laneNO, volume, avgSpeed, avgOccupancy, sVolume, mVolume, lVolume);
                    vdDataList.add(vdData);

                }

            }
        }

        System.out.println(String.format("%1$s\tNum of data rows: %2$d", TimestampUtil.getTimestampStr(), vdDataList.size()));
        
        return vdDataList;
    }
}

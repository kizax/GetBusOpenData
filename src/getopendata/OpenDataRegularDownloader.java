package getopendata;

import javax.xml.parsers.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;

public class OpenDataRegularDownloader {

    public static void main(String[] args) throws ParserConfigurationException {

        int timeInterval = 1 * 60 * 1000; //in millissecond

        Date date = new Date();
        Timer timer = new Timer();

        System.out.println(String.format("%1$s\tIf you want to stop timer, press 12345.", TimestampUtil.getTimestampStr()));
        System.out.println(String.format("%1$s\tStart VD Open Data Regular Downloader!", TimestampUtil.getTimestampStr()));
        timer.schedule(new GetOpenDataTask(), date, timeInterval);

        while(true){
            Scanner scanner = new Scanner(System.in);
            int stopSign = scanner.nextInt();
            if (stopSign == 12345) {
                timer.cancel();
                System.out.println(String.format("%1$s\tThe timer stop.", TimestampUtil.getTimestampStr()));
            }
        }

    }

}

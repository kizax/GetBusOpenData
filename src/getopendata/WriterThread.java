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
import java.io.FileWriter;
import java.io.IOException;

public class WriterThread extends Thread {

    private final String record;
    private final FileWriter txtFileWriter;

    public WriterThread(FileWriter fileWriter, String record) {
        this.txtFileWriter = fileWriter;
        this.record = record;
    }

    @Override
    public void run() {
        super.run();
        try {

            synchronized (txtFileWriter) {
                txtFileWriter.write(record + "\n");
                txtFileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

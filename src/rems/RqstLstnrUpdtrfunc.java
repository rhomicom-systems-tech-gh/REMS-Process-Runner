/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rems;

import java.util.Arrays;

/**
 *
 * @author richard.adjei-mensah
 */
public class RqstLstnrUpdtrfunc extends Thread {

    private Thread t;
    private String threadName;

    RqstLstnrUpdtrfunc(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }

    @Override
    public void run() {
        try {
            System.out.println("Running " + threadName);
            long prgmID = Global.getGnrlRecID("rpt.rpt_prcss_rnnrs", "rnnr_name", "prcss_rnnr_id", Program.runnerName);
            Global.errorLog = "Successfully Started Thread One\r\nProgram ID:" + prgmID + "\r\n";
            System.out.println(Global.errorLog);
            Global.writeToLog();
            do {
                Program.updatePrgrm(prgmID);
                Global.minimizeMemory();
                Thread.sleep(15000);
            } while (true);
        } catch (InterruptedException ex) {
            //write to log file
            Global.errorLog = ex.getMessage() + "\r\n" + Arrays.toString(ex.getStackTrace()) + "\r\n";
            System.out.println(Global.errorLog);
            Global.writeToLog();
            if (Program.thread1.isAlive()) {
                Program.thread1.interrupt();
            }
            Program.killThreads();
        } catch (Exception ex) {
            //write to log file
            Global.errorLog = ex.getMessage() + "\r\n" + Arrays.toString(ex.getStackTrace()) + "\r\n";
            System.out.println(Global.errorLog);
            Global.writeToLog();
            if (Program.thread1.isAlive()) {
                Program.thread1.interrupt();
            }
            Program.killThreads();
        } finally {
            Program.killThreads();
        }
    }

    @Override
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

package com.rhuangal.logger;

import com.rhuangal.logger.util.LevelLogger;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rober
 */
public class MyLoggerTest {
    
    public MyLoggerTest() {
    }
       
    @Before
    public void setUp() {
        MyLogger my = new MyLogger();
        //Set Database Params
        Map dbParams = new HashMap();
        dbParams.put("userName", "logger");
        dbParams.put("password", "logger");
        dbParams.put("dbms", "derby");       
        dbParams.put("serverName", "localhost");      
        dbParams.put("portNumber", "1527");
        dbParams.put("database", "logger");
        my.setDbParams(dbParams);
        //Enable to save in database
        my.setLogToDatabase(false);
        //Enable to show in console
        my.setLogToConsole(true);
        //Enable to save in file
        my.setLogToFile(true);
        //Set file
        my.setFilename("D:\\logFile.txt");
        /*Set Logger Level
            MESSAGE = ONLY SHOW MESSAGGES
            WARNING = SHOW WARNING AND ERRORS
            ERROR   = ONLY SHOW ERRORS
        */
        my.setLogLevel(LevelLogger.WARNING);
    }

    /**
     * Test of LogMessage method, of class MyLogger.
     */
    @Test
    public void testLogMessage() {
        System.out.println("LogMessage");
        String msg = "Internal process error.";
        MyLogger.LogMessage(LevelLogger.ERROR, msg);
    }

}

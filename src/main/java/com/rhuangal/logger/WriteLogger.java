package com.rhuangal.logger;

import com.rhuangal.logger.util.MyDatabaseHandler;
import com.rhuangal.logger.util.LevelLogger;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author rober
 */
public class WriteLogger {

    private MyLogger myLogger;

    private LevelLogger level;

    private File logFile;
    private FileHandler fh;
    private ConsoleHandler ch;
    private MyDatabaseHandler dh;

    private Logger logger;

    public WriteLogger(MyLogger myLogger) {
        this.myLogger = myLogger;
    }

    public void startLogger(String message, LevelLogger level) {
        this.logger = Logger.getLogger("MyLog");
        this.level = level;
        if (fh == null && ch == null && dh == null) {
            init();
        }
        logger.log(Level.INFO, getLine(message), level);
    }

    private void init() {
        
        logger.setUseParentHandlers(false);
        if (!myLogger.isLogToConsole() && !myLogger.isLogToFile() && !myLogger.isLogToDatabase()) {
            throw new Error("Invalid configuration");
        } else {
            if (myLogger.isLogToConsole()) {
                writeToConsole();
            }
            if (myLogger.isLogToFile()) {
                writeToFile();
            }
            if (myLogger.isLogToDatabase()) {
                writeToDatabase();
            }
        }
    }

    private void writeToConsole() {
        ch = new ConsoleHandler();
        logger.addHandler(ch);
        ch.setFormatter(getSimpleFormatter());

    }

    private synchronized void writeToFile() {
        logFile = new File(myLogger.getFilename());
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create new file: " + myLogger.getFilename());
                e.printStackTrace();
            }
        }
        try {
            fh = new FileHandler(myLogger.getFilename(), true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(getSimpleFormatter());
        } catch (SecurityException e) {
            System.err.println("Cannot access to file: " + myLogger.getFilename());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Cannot write to file: " + myLogger.getFilename());
            e.printStackTrace();
        }
    }

    private void writeToDatabase() {
        dh = new MyDatabaseHandler(myLogger.getDbParams());
        logger.addHandler(dh);
        dh.setFormatter(getSimpleFormatter());
    }

    private String getDate() {
        String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        return date;
    }

    private String getLine(String message) {
        return getDate() + " - " + message;
    }

    private SimpleFormatter getSimpleFormatter() {
        return new SimpleFormatter() {
            private static final String format = "[%1$s] %2$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        level.name(),
                        lr.getMessage()
                );
            }
        };
    }

}

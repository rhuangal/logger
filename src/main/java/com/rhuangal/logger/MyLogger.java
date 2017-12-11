package com.rhuangal.logger;

import com.rhuangal.logger.util.LevelLogger;
import java.util.Map;

/**
 *
 * @author rober
 */
public class MyLogger {

    private String filename;
    private Map dbParams;

    private boolean logToFile;
    private boolean logToConsole;
    private boolean logToDatabase;

    private static LevelLogger logLevel;

    private static WriteLogger write;

    public MyLogger() {
        this.write = new WriteLogger(this);
    }

    public static void LogMessage(LevelLogger l, String msg) {
        switch (l) {
            case MESSAGE:
                message(msg, l);
                break;
            case WARNING:
                warning(msg, l);
                break;
            case ERROR:
                error(msg, l);
                break;
            default: {
                try {
                    throw new IllegalStateException();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void message(String s, LevelLogger l) {
        if (logLevel.equals(l)) {
            write.startLogger(s, l);
        }
    }

    private static void warning(String s, LevelLogger l) {
        if (logLevel.equals(l)) {
            write.startLogger(s, l);
        }
    }

    private static void error(String s, LevelLogger l) {
        if (logLevel.equals(l) || logLevel.equals(LevelLogger.WARNING)) {
            write.startLogger(s, l);
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Map getDbParams() {
        return dbParams;
    }

    public void setDbParams(Map dbParams) {
        this.dbParams = dbParams;
    }

    public boolean isLogToFile() {
        return logToFile;
    }

    public void setLogToFile(boolean logToFile) {
        this.logToFile = logToFile;
    }

    public boolean isLogToConsole() {
        return logToConsole;
    }

    public void setLogToConsole(boolean logToConsole) {
        this.logToConsole = logToConsole;
    }

    public boolean isLogToDatabase() {
        return logToDatabase;
    }

    public void setLogToDatabase(boolean logToDatabase) {
        this.logToDatabase = logToDatabase;
    }

    public void setLogLevel(LevelLogger logLevel) {
        MyLogger.logLevel = logLevel;
    }



}

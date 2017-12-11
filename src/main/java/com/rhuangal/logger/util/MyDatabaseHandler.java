package com.rhuangal.logger.util;

import com.rhuangal.logger.util.LevelLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author roberto huangal diaz
 * @web https://github.com/rhuangal/
 * @version 2.0
 */
public class MyDatabaseHandler extends Handler {

    private Connection con;
    private PreparedStatement pstmt;

    public MyDatabaseHandler(Map dbParams) {
        Properties props = new Properties();
        con = null;
        try {
            props.put("user", dbParams.get("userName"));
            props.put("password", dbParams.get("password"));
            String url = "jdbc:" + dbParams.get("dbms") + "://"
                    + dbParams.get("serverName")
                    + ":" + dbParams.get("portNumber") + "/" + dbParams.get("database");
            con = DriverManager.getConnection(url, props);
            String sql = "insert into Log_Values values(?,?)";
            pstmt = con.prepareStatement(sql);
        } catch (SQLException e) {
            System.err.println("Connection error");
            e.printStackTrace();
        }
    }

    @Override
    public void publish(LogRecord record) {
        try {   
            LevelLogger l = (LevelLogger) record.getParameters()[0];
            pstmt.setString(1, record.getMessage());
            pstmt.setInt(2, l.ordinal());
            pstmt.executeUpdate(); 
            con.commit();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public void flush() {
        
    }

    @Override
    public void close() throws SecurityException {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

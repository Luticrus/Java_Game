package main;
import main.GamePanel;

import java.sql.*;

public class DataBaseConfiguration {

    GamePanel gp;
    public DataBaseConfiguration(GamePanel gp)
    {
        this.gp = gp;
    }

    public static Connection GetDataBaseConnection() throws SQLException {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:OptionConfig.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        c.setAutoCommit(false);
        return c;

    }

    public static void CloseDataBaseConnection(Connection c)
    {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getOptions(Connection c, Statement stmt) throws SQLException {
        c.setAutoCommit(false);
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM CONFIG;" );
        while ( rs.next() ) {
            int fullscreen = rs.getInt("FULLSCREEN");
            if(fullscreen == 1)
            {
                gp.FullScreenOn = true;
            }
            if(fullscreen == 0)
            {
                gp.FullScreenOn = false;
            }
            gp.music.volumeScale  = rs.getInt("MUSIC");
            gp.se.volumeScale = rs.getInt("SFX");
        }
        rs.close();

    }


    public Statement Update(Connection c, Statement stmt)
    {
        try {
            stmt = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql=null;
        ///UPDATE MUSIC FROM DATABASE
        if(gp.music.volumeScale == 0)
        {
            sql = "UPDATE CONFIG set MUSIC = 0";
        }
        if(gp.music.volumeScale == 1)
        {
            sql = "UPDATE CONFIG set MUSIC = 1";
        }
        if(gp.music.volumeScale == 2)
        {
            sql = "UPDATE CONFIG set MUSIC = 2";
        }
        if(gp.music.volumeScale == 3)
        {
            sql = "UPDATE CONFIG set MUSIC = 3";
        }
        if(gp.music.volumeScale == 4)
        {
            sql = "UPDATE CONFIG set MUSIC = 4";
        }
        if(gp.music.volumeScale == 5)
        {
            sql = "UPDATE CONFIG set MUSIC = 5";
        }
        try {
            stmt.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ///UPDATE SFX FROM DATABASE
        if(gp.se.volumeScale == 0)
        {
            sql = "UPDATE CONFIG set SFX = 0";
        }
        if(gp.se.volumeScale == 1)
        {
            sql = "UPDATE CONFIG set SFX = 1";
        }
        if(gp.se.volumeScale == 2)
        {
            sql = "UPDATE CONFIG set SFX = 2";
        }
        if(gp.se.volumeScale == 3)
        {
            sql = "UPDATE CONFIG set SFX = 3";
        }
        if(gp.se.volumeScale == 4)
        {
            sql = "UPDATE CONFIG set SFX = 4";
        }
        if(gp.se.volumeScale == 5)
        {
            sql = "UPDATE CONFIG set SFX = 5";
        }
        try {
            stmt.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ////UPDATE FULLSCREEN
        if(gp.FullScreenOn == true)
        {
            sql = "UPDATE CONFIG set FULLSCREEN = 1";
        }
        if(gp.FullScreenOn == false)
        {
            sql = "UPDATE CONFIG set FULLSCREEN = 0";
        }
        try {
            stmt.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;

    }

}

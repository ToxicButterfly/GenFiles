package com.company.database;

import com.company.Const;
import com.company.model.Line;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Database {

    //Create connection with Postgres database
    public Connection getDbConnection()  {
        Properties props = new Properties();
        //get database properties
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String connect = props.getProperty("url") + ":" + props.getProperty("port") + "/" + props.getProperty("database");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(connect,props.getProperty("username"),props.getProperty("password"));
            return conn;
        } catch (SQLException e) {
            System.out.println("Нет соединения!");
            return null;
        }
    }

    //Inserting 1000 lines at a time
    public void Input(List<Line> entities, int count, int num) throws SQLException {
        Connection connection = null;
        connection = getDbConnection();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO " + Const.LINES_TABLE + "(" +
                    Const.LINES_DATE + ", " + Const.LINES_ENGLISH + ", " + Const.LINES_RUSSIAN + ", " +
                    Const.LINES_INT + ", " + Const.LINES_DOUBLE + ")" + " VALUES(?,?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int i = 0;

        for (int l = 0; l < entities.size(); l++) {
            statement.setString(1, entities.get(l).getDate());
            statement.setString(2, entities.get(l).getEng());
            statement.setString(3, entities.get(l).getRus());
            statement.setString(4, entities.get(l).getInteg());
            //Unfortunately, JDBC removes trailing zeroes from decimal numbers.
            statement.setBigDecimal(5,entities.get(l).getDoubl());
            statement.addBatch();
            i++;

            if (i % 1000 == 0 || i == entities.size()) {
                statement.executeBatch(); // Execute every 1000 items.
                System.out.println("Already imported " + count + " lines. " + (num-count) + " lines are left!");
            }

        }
        connection.close();
    }
}


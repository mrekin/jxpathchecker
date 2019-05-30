package ru.rmm.home;

import java.sql.*;
import java.util.ArrayList;

public class DBLayer {

    static final String DB_NAME = "jxpath.db";
    static final String JDBC_URL = "jdbc:sqlite:" + DB_NAME;
    private Connection conn = null;
    static DBLayer instance = null;

    private DBLayer() {

        init();

    }

    public static DBLayer getInstance(){
        if(instance==null){
            instance = new DBLayer();
        }
        return instance;
    }

    private void init() {
        try {
            conn = DriverManager.getConnection(JDBC_URL);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");

                Statement statement = conn.createStatement();

                statement.executeUpdate("create table filters (name string UNIQUE NOT NULL PRIMARY KEY, filter string)");
                statement.executeUpdate("create table messages (name string UNIQUE NOT NULL PRIMARY KEY, message string)");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<String> getFilersList() {
        ArrayList<String> fl = new ArrayList<>();
        if (conn != null) {
            try {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from filters");
                while(rs.next())
                {
                    // read the result set

                    System.out.println("name = " + rs.getString("name"));
                    fl.add(rs.getString("name"));
                }
            } catch (SQLException se) {
                System.out.println(se.getLocalizedMessage());
            }
        }
        return fl;
    }

    public ArrayList<String> getMessagesList() {
        ArrayList<String> fl = new ArrayList<>();
        if (conn != null) {
            try {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from messages");
                while(rs.next())
                {
                    // read the result set

                    System.out.println("name = " + rs.getString("name"));
                    fl.add(rs.getString("name"));
                }
            } catch (SQLException se) {
                System.out.println(se.getLocalizedMessage());
            }
        }
        return fl;
    }

    public String getFilter(String name) {
        String filter = "";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from filters");
            while(rs.next())
            {
                // read the result set

                System.out.println("name = " + rs.getString("name"));
                if(name.equals(rs.getString("name"))){
                    filter = rs.getString("filter");
                    break;
                }

            }
        } catch (SQLException se) {
            System.out.println(se.getLocalizedMessage());
        }

        return filter;
    }

    public String getMessage(String name) {
        String message = "";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from messages");
            while(rs.next())
            {
                // read the result set

                System.out.println("name = " + rs.getString("name"));
                if(name.equals(rs.getString("name"))){
                    message = rs.getString("message");
                    break;
                }

            }
        } catch (SQLException se) {
            System.out.println(se.getLocalizedMessage());
        }

        return message;
    }

    public String setFilter(String name, String filter) {

        try {
            /*
            Statement statement = conn.createStatement();
            statement.executeUpdate("insert into filters values("+name+", "+filter+");");
*/
            PreparedStatement preparedStatement = null;
            // ? - место вставки нашего значеня
            preparedStatement = conn.prepareStatement(
                    "insert into filters values(?,?);");
            //Устанавливаем в нужную позицию значения определённого типа
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, filter);

            preparedStatement.executeUpdate();

        } catch (SQLException se) {
            System.out.println(se.getLocalizedMessage());
        }

        return filter;
    }

    public String setMessage(String name, String message) {

        try {
            /*
            Statement statement = conn.createStatement();
            statement.executeUpdate("insert into messages values("+name+", "+message+");");
*/
            PreparedStatement preparedStatement = null;
            // ? - место вставки нашего значеня
            preparedStatement = conn.prepareStatement(
                    "insert into messages values(?,?);");
            //Устанавливаем в нужную позицию значения определённого типа
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, message);

            preparedStatement.executeUpdate();

        } catch (SQLException se) {
            System.out.println(se.getLocalizedMessage());
        }

        return message;
    }


}

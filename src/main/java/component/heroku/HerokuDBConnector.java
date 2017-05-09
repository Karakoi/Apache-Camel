package component.heroku;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
public class HerokuDBConnector {

    private final String url;
    private final String user;
    private final String password;
    private Connection connection = null;

    public Connection connect() {
        System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
//            connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-247-99-159.eu-west-1.compute.amazonaws.com:5432/dfrccd9sohrr9l?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", user, password);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void disConnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int readItemByName(String itemName) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = "SELECT item_value,date_of_creation FROM erlang_data WHERE item_name =" + itemName + ";";
            ps = connect().prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt("item_value");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return 0;
    }

    public void writeNewItem(String itemName, int itemValue) {
        PreparedStatement ps;
        try {
            String sql = "INSERT INTO erlang_data(item_name,date_of_creation, item_value) VALUES(?, ?, ?);";
            ps = connect().prepareStatement(sql);

            ps.setString(1, itemName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            ps.setString(2, formatter.format(now));

            ps.setInt(3, itemValue);
            ps.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            disConnect();
        }

    }


//    public static void main(String[] argv) {
//
//        System.out.println("-------- PostgreSQL "
//                + "JDBC Connection Testing ------------");
//
//        try {
//            Class.forName("org.postgresql.Driver");
//            System.out.println("PostgreSQL JDBC Driver Registered!");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        Connection connection;
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:postgresql://ec2-54-247-99-159.eu-west-1.compute.amazonaws.com:5432/dfrccd9sohrr9l?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory",
//                    "xcovmuduhgfobf",
//                    "6bd88d5e0e8afdf4d428d8e868a859efcf02b6e98b101fbff356a37cb5c9ea03");
//
//            System.out.println("OK!");
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT id, item_name FROM erlang_data");
//            int id;
//            String name;
//            while (rs.next()) {
//                id = rs.getInt("id");
//                name = rs.getString("item_name");
//                System.out.println("id: " + id + " ,name: " + name);
//            }
//
//
//        } catch (SQLException e) {
//            System.out.println("Connection Failed! Check output console");
//            e.printStackTrace();
//            return;
//        }
//
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}

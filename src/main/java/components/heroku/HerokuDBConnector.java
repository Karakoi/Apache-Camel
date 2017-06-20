package components.heroku;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public String readItemByName(String itemName) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = "SELECT item_value,date_of_creation FROM erlang_data WHERE item_name ='" + itemName + "';";
            ps = connect().prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            return rs.getString("item_value");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return null;
    }

    public void writeNewItem(String itemName, int itemValue) {
        PreparedStatement ps;
        try {
            String sql = "INSERT INTO erlang_data(item_name,date_of_creation, item_value) VALUES(?, ?, ?);";
            ps = connect().prepareStatement(sql);
            ps.setString(1, itemName);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, itemValue);
            ps.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            disConnect();
        }

    }

    public Set<List<String>> readItemsFromDevicesPurchase(String periodFrom, String periodTo) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            String sql = "SELECT d.device_type, d.device_name, d.date_of_purchase, d.quantity, d.unit_price FROM devices_purchase d;";
            ps = connect().prepareStatement(sql);
            rs = ps.executeQuery();
            // Количество колонок в результирующем запросе
            int columns = rs.getMetaData().getColumnCount();
            // Перебор строк с данными
            Set<List<String>> devices = new HashSet<>();
//            List<String> dataFromRS = null;
            while (rs.next()) {
//                for (int i = 0; i < columns; i++) {
//                    dataFromRS.add(rs.getString(i));
//                }
                List<String> dataFromRS = new ArrayList<>();
                dataFromRS.add(rs.getString("device_type"));
                dataFromRS.add(rs.getString("device_name"));
                dataFromRS.add(rs.getString("date_of_purchase"));
                dataFromRS.add(rs.getString("quantity"));
                dataFromRS.add(rs.getString("unit_price"));
                devices.add(dataFromRS);
            }
            System.out.println(devices);
            return devices;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
        return null;
    }

}

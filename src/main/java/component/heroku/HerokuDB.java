package component.heroku;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class HerokuDB {

    public static void main(String[] argv) {

        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://ec2-54-247-99-159.eu-west-1.compute.amazonaws.com:5432/dfrccd9sohrr9l?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory",
                    "xcovmuduhgfobf",
                    "6bd88d5e0e8afdf4d428d8e868a859efcf02b6e98b101fbff356a37cb5c9ea03");

            System.out.println("OK!");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, item_name FROM erlang_data");
            int id = 0;
            String name = "";
            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("item_name");
                System.out.println("id: " + id + " ,name: "+ name);
            }


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

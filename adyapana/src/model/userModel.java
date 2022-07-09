package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PATHUMI
 */
public class userModel {

    Connection conn;

    public userModel() {
        conn = db.dbConnection.getConnection();
    }

    public String login(String username, String password) {

        String msg = "";
        String Query = "SELECT * FROM user WHERE username ='" + username + "' AND password ='" + password + "'  ";

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            String name = "";
            while (rs.next()) {
                name = rs.getString("username");
            }
            if (name.equals("")) {
                msg = "invalid username or password";
            } else {
                msg = "welcome " + name;
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }

        return msg;
    }

  
    
}

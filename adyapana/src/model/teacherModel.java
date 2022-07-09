/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import gui.teacherRegistration;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PATHUMI
 */
public class teacherModel {

    Connection conn;
    

    public teacherModel() {
        conn = db.dbConnection.getConnection();
    }

    public String insertTeacher(String id,String name, String address, String subject) {
        String msg = null;
        String Query = "INSERT INTO `adyapana`.`teacher` (`tno`, `name`, `address`, `subjects`) VALUES (?,?, ?, ?) ";
        
        
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);
            psm.setString(2, name);
            psm.setString(3, address);
            psm.setString(4, subject);

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public String updateTeacher(String tno, String name, String address, int subject) {
        String msg = null;
        String Query = "UPDATE teacher SET  name=?, address=?, subjects=?  WHERE tno =? ";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, name);
            psm.setString(2, address);
            psm.setInt(3, subject);
            psm.setString(4, tno);

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public void loadTable(String keyword, DefaultTableModel teacherTableModel) {

        String loadData = "SELECT * FROM teacher WHERE name LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(loadData);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            Object[] dataRow;
            teacherTableModel.setRowCount(0);
            while (rs.next()) {
                String tno = rs.getString("tno");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String sub = rs.getString("subjects");
                dataRow = new Object[]{tno, name, address, sub};
                teacherTableModel.addRow(dataRow);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[] getSelectedTeacherDataById(String tno) {

        String runningQuery = "SELECT * FROM teacher WHERE tno = ?";
        try {
            PreparedStatement psm = conn.prepareStatement(runningQuery);
            psm.setString(1, tno);
            ResultSet rs = psm.executeQuery();
            Object[] dataRow = null;
            while (rs.next()) {

                String tcno = rs.getString("tno");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String sub = rs.getString("subjects");
                dataRow = new Object[]{tcno, name, address, sub};

            }
            return dataRow;
        } catch (Exception e) {
        }
        return null;

    }

    public String deleteTeacher(String id) {
        String msg = null;
        String Query = "DELETE FROM teacher WHERE tno =? ";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

 

}

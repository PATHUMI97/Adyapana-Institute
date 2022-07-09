/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PATHUMI
 */
public class studentModel {

    Connection conn;

    public studentModel() {
        conn = db.dbConnection.getConnection();
    }

    public String insertStudent(String id,String name, String address, Date birthday) {

        String msg = null;
        String Query = "INSERT INTO student(sno,name, address, dob) VALUES(?, ?, ?,?)  ";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);
            psm.setString(2, name);
            psm.setString(3, address);
            psm.setDate(4, new java.sql.Date(birthday.getTime()));

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public String updateStudent(String sno, String name, String address, Date birthday) {
        String msg = null;
        String Query = "UPDATE student SET name=?, address=?, dob=?  WHERE sno =? ";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            
            psm.setString(1, name);
            psm.setString(2, address);
            psm.setDate(3, new java.sql.Date(birthday.getTime()));
            psm.setString(4, sno);
            

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public void loadTable(String keyword, DefaultTableModel studenTableModel) {

        String loadData = "SELECT * FROM student WHERE name LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(loadData);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            Object[] dataRow;
            studenTableModel.setRowCount(0);
            while (rs.next()) {
                String stno = rs.getString("sno");
                String name = rs.getString("name");
                String address = rs.getString("address");
                Date dob = rs.getDate("dob");
                dataRow = new Object[]{stno, name, address, dob};
                studenTableModel.addRow(dataRow);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[] getSelectedStudentDataById(String stno) {

        String runningQuery = "SELECT * FROM student WHERE sno = ?";
        try {
            PreparedStatement psm = conn.prepareStatement(runningQuery);
            psm.setString(1, stno);
            ResultSet rs = psm.executeQuery();
            Object[] dataRow = null;
            while (rs.next()) {

                String stdno = rs.getString("sno");
                String name = rs.getString("name");
                String address = rs.getString("address");
                Date dob = rs.getDate("dob");
                dataRow = new Object[]{stdno, name, address, dob};

            }
            return dataRow;
        } catch (Exception e) {
        }
        return null;

        

    }

    public String deleteStudent(String sno) {
               String msg = null;
        String Query = "DELETE FROM student WHERE sno =? ";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
             psm.setString(1, sno);

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

}

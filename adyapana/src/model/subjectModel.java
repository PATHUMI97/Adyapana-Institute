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
public class subjectModel {
    Connection conn;
    
    public subjectModel(){
        conn=db.dbConnection.getConnection();
    }

    public String InsertSubject(String id, String name, String price) {
       String msg = null;
        String Query = "INSERT INTO subjects(sub_no,name,price) VALUES(?, ?, ?)  ";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);
            psm.setString(2, name);
            psm.setString(3, price);
           

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public void loadTable(String keyword, DefaultTableModel subTableModel) {
       String loadData = "SELECT * FROM subjects WHERE name LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(loadData);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            Object[] dataRow;
            subTableModel.setRowCount(0);
            while (rs.next()) {
                String sno = rs.getString("sub_no");
                String name = rs.getString("name");
                String price = rs.getString("price");
                
                dataRow = new Object[]{sno, name, price};
                subTableModel.addRow(dataRow);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[] getSelectedSubjectDataById(String sbno) {
        String runningQuery = "SELECT * FROM subjects WHERE sub_no = ?";
        try {
            PreparedStatement psm = conn.prepareStatement(runningQuery);
            psm.setString(1, sbno);
            ResultSet rs = psm.executeQuery();
            Object[] dataRow = null;
            while (rs.next()) {

                String subno = rs.getString("sub_no");
                String name = rs.getString("name");
                String prc = rs.getString("price");
                
                dataRow = new Object[]{subno, name, prc};

            }
            return dataRow;
        } catch (Exception e) {
        }
        return null;

    }

    public String updateSubject(String id, String name, String price) {
        String msg = null;
        String Query = "UPDATE subjects SET name=?, price=? WHERE sub_no =?   ";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            
            psm.setString(1, name);
            psm.setString(2, price);
            psm.setString(3, id);
           

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
        }

    public String deleteSubject(String id) {
               String msg = null;
        String Query = "DELETE FROM subjects WHERE sub_no =? ";
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

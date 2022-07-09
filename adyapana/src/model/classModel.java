/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PATHUMI
 */
public class classModel {
    
    Connection conn;
    
    public classModel(){
        conn =db.dbConnection.getConnection();
    }

 

    public String insertClass(String id, String subject, String teacher, String timeslot) {
      
        String msg = null;
        String Query = "INSERT INTO `adyapana`.`class` (`class_no`, `sub_no`, `tno`, `time_slot`) VALUES (?,?,?,?);";
        
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);
            psm.setString(2, subject);
            psm.setString(3, teacher);
            psm.setString(4, timeslot);
            psm.execute();
            
            msg = "Success";
            
        } catch (Exception e) {
            e.printStackTrace();
             msg = "Error" + e.getMessage();
        }
  
        return msg; 
    
    }

    public void loadTable(String keyword, DefaultTableModel classTableModel) {
        
    String loadData = "SELECT * FROM class WHERE class_no LIKE ?";
       try {
            PreparedStatement ps = conn.prepareStatement(loadData);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            Object[] dataRow;
            classTableModel.setRowCount(0);
            while (rs.next()) {
                String cno = rs.getString("class_no");
                String sbno = rs.getString("sub_no");
                String tno = rs.getString("tno");
                String timeslot = rs.getString("time_slot");
                dataRow = new Object[]{cno, sbno, tno, timeslot};
                classTableModel.addRow(dataRow);

            }
        } catch (Exception e) {
            e.printStackTrace();
    }
    }

    public Object[] getSelectedClassDataById(String cno) {
      
          String runningQuery = "SELECT * FROM class WHERE class_no = ?";
        try {
            PreparedStatement psm = conn.prepareStatement(runningQuery);
            psm.setString(1, cno);
            ResultSet rs = psm.executeQuery();
            Object[] dataRow = null;
            while (rs.next()) {

                String clno = rs.getString("class_no");
                String sbno = rs.getString("sub_no");
                String tno = rs.getString("tno");
                String timeslot = rs.getString("time_slot");
                dataRow = new Object[]{clno, sbno, tno, timeslot};

            }
            return dataRow;
        } catch (Exception e) {
        }
        return null;
    }

    public String updateClass(String id, int subject, int teacher, String timeslot) {
       String msg = null;
        String Query = "UPDATE class SET `sub_no`=?, `tno`=?, `time_slot`=? WHERE class_no=?";
        
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);
            psm.setInt(2, subject);
            psm.setInt(3, teacher);
            psm.setString(4, timeslot);
            psm.execute();
            
            msg = "Success";
            
        } catch (Exception e) {
            e.printStackTrace();
             msg = "Error" + e.getMessage();
        }
  
        return msg;   
    }

    public String deleteClass(String id) {
        String msg = null;
        String Query = "DELETE FROM class WHERE class_no =? ";
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

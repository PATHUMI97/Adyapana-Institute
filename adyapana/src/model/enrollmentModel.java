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
public class enrollmentModel {
    Connection conn;
    
    public enrollmentModel(){
        conn=db.dbConnection.getConnection();
    }

    public String insertEnrl(String id, String student, String classid) {
           String msg = null;
        String Query = "INSERT INTO `adyapana`.`student_has_class` (`id`, `student_sno`, `class_class_no`) VALUES (?,?,?)";
        
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);
            psm.setString(2, student);
            psm.setString(3, classid);
           
            psm.execute();
            
            msg = "Success";
            
        } catch (Exception e) {
            e.printStackTrace();
             msg = "Error" + e.getMessage();
        }
  
        return msg; 
    }

    public void loadTable(String keyword, DefaultTableModel enrollmentTableModel) {
          String loadData = "SELECT * FROM student_has_class WHERE class_class_no LIKE ?";
       try {
            PreparedStatement ps = conn.prepareStatement(loadData);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            Object[] dataRow;
            enrollmentTableModel.setRowCount(0);
            while (rs.next()) {
                String eid = rs.getString("id");
                String sno = rs.getString("student_sno");
                String cno = rs.getString("class_class_no");
                
                dataRow = new Object[]{eid, sno, cno};
                enrollmentTableModel.addRow(dataRow);

            }
        } catch (Exception e) {
            e.printStackTrace();
    }
    }

    public Object[] getSelectedEnrlDataById(String eno) {
            String runningQuery = "SELECT * FROM student_has_class WHERE id = ?";
        try {
            PreparedStatement psm = conn.prepareStatement(runningQuery);
            psm.setString(1, eno);
            ResultSet rs = psm.executeQuery();
            Object[] dataRow = null;
            while (rs.next()) {

                String enno = rs.getString("id");
                String stno = rs.getString("student_sno");
                String clno = rs.getString("class_class_no");
                
                dataRow = new Object[]{enno, stno, clno};

            }
            return dataRow;
        } catch (Exception e) {
        }
        return null;
    }

    public String updateEnrl(String id, String student, String classid) {
            String msg = null;
        String Query = "UPDATE `student_has_class` SET `student_sno`=?, `class_class_no`=? WHERE id =?";
        
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);
            psm.setString(2, student);
            psm.setString(3, classid);
           
            psm.execute();
            
            msg = "Success";
            
        } catch (Exception e) {
            e.printStackTrace();
             msg = "Error" + e.getMessage();
        }
  
        return msg; 
    }

    public String deleteEnrl(String id) {
            String msg = null;
        String Query = "DELETE FROM student_has_class WHERE id =? ";
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

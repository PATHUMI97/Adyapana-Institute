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
public class paymentModel {
    Connection conn;
    
public paymentModel(){
    conn=db.dbConnection.getConnection();
}
    public String insertPayment(String stno, String tno, String sbno, String month, String fees) {
      String msg = null;
        String Query = "INSERT INTO `adyapana`.`invoice` ( `sno`, `tno`, `subno`, `month`, `value`) VALUES ( ?, ?, ?, ?, ?);";
  
    try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, stno);
            psm.setString(2, tno);
            psm.setString(3, sbno);
            psm.setString(4, month);
            psm.setString(5, fees);

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public void loadTable(String keyword, DefaultTableModel paymentTableModel) {
      String loadData ="SELECT * FROM invoice WHERE sno  LIKE  ?";
       try {
            PreparedStatement ps = conn.prepareStatement(loadData);
            ps.setString(1, "%" + keyword + "%");
            
            ResultSet rs = ps.executeQuery();
            Object[] dataRow;
            paymentTableModel.setRowCount(0);
            while (rs.next()) {
               
                 int ino= rs.getInt("invoice_id");
                String sno = rs.getString("sno");
                String tno = rs.getString("tno");
                String sub = rs.getString("subno");
                String month = rs.getString("month");
                String value = rs.getString("value");
                dataRow = new Object[]{ino, sno, tno, sub, month,value};
                paymentTableModel.addRow(dataRow);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    public String updatePayment(String id, String stno, String tno, String sbno, String month, String fees) {
            String msg = null;
        String Query = "UPDATE `invoice` SET `sno`=?, `tno`=?, `subno`=?, `month`=? WHERE invoice_id=? ";
  
    try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, stno);
            psm.setString(2, tno);
            psm.setString(3, sbno);
            psm.setString(4, month);
            psm.setString(5, fees);
            psm.setString(6, id);

            psm.execute();

            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public Object[] getSelectedInvoiceById(int invno) {
       String query ="SELECT * FROM invoice WHERE invoice_id=?";
               try {
            PreparedStatement psm = conn.prepareStatement(query);
            psm.setInt(1, invno);
            ResultSet rs = psm.executeQuery();
            Object[] dataRow = null;
            while (rs.next()) {

               int ino= rs.getInt("invoice_id");
                String sno = rs.getString("sno");
                String tno = rs.getString("tno");
                String sub = rs.getString("subno");
                String month = rs.getString("month");
                String value = rs.getString("value");
                dataRow = new Object[]{ino, sno, tno, sub, month,value};

            }
            return dataRow;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deletePayment(String id) {
                   String msg = null;
        String Query = "DELETE FROM invoice WHERE invoice_id=? ";
  
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

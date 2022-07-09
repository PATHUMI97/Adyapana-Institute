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
public class attendanceModel {

    Connection conn;

    public attendanceModel() {
        conn = db.dbConnection.getConnection();
    }

    public String insertStudent(String id, String sno, String cno, boolean present) {
        String msg = null;
        String Query = "INSERT INTO `adyapana`.`attendance` (`id_attendance`, `date`, `sno`, `class_no`, `attendance`) VALUES (?, now(), ?, ?,?)";
        try {
            PreparedStatement psm = conn.prepareStatement(Query);
            psm.setString(1, id);

            psm.setString(2, sno);
            psm.setString(3, cno);
            psm.setBoolean(4, present);

            psm.execute();
            msg = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error" + e.getMessage();
        }

        return msg;
    }

    public void loadTable(String keyword, int categoryId, DefaultTableModel attendanceTableModel) {

        String loadDataByStudent = "SELECT * FROM attendance WHERE sno LIKE ?";
        String loadDataByClass = "SELECT * FROM attendance WHERE class_no LIKE ?";

        String runningQuery = null;
        if (categoryId == 0) {
            runningQuery = loadDataByStudent;
        } else if (categoryId == 1) {
            runningQuery = loadDataByClass;
        }
        try {
            PreparedStatement psm = conn.prepareStatement(runningQuery);
            psm.setString(1, "%" + keyword + "%");
            ResultSet rs = psm.executeQuery();
            Object[] dataRow;
            attendanceTableModel.setRowCount(0);// clear current lines
            while (rs.next()) {
                int id = rs.getInt("id_attendance");
                Date date = rs.getDate("date");
                String sno = rs.getString("sno");
                String cno = rs.getString("class_no");
                boolean atnd = rs.getBoolean("attendance");

                String a = null;
                if (atnd) {// male->gender=true 
                    a = "PRESENT";
                } else {//femle->false
                    a = "ABSENT";
                }
                dataRow = new Object[]{id, date, sno, cno, a};
                attendanceTableModel.addRow(dataRow);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Object[] getSelectedStudentAttendanceDataById(int atno) {
        String runningQuery = "SELECT * FROM `attendance` WHERE id_attendance= ?";
        try {
            PreparedStatement psm = conn.prepareStatement(runningQuery);
            psm.setInt(1, atno);
            ResultSet rs = psm.executeQuery();
            Object[] dataRow = null;
            while (rs.next()) {
                int id = rs.getInt("id_attendance");
                String std = rs.getString("sno");
                String cls = rs.getString("class_no");
                boolean atnd = rs.getBoolean("attendance");
                dataRow = new Object[]{id, std, cls, atnd};

            }
            return dataRow;
        } catch (Exception e) {
        }
        return null;
    }



}

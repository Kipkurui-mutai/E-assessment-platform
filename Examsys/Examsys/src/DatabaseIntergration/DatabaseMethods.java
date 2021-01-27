package DatabaseIntergration;

import examsys.ShowErrors;
import impl.Exam;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseMethods {

    DatabaseConnect dbconnect;
    Connection conn;
    ResultSet rs;
    Statement st;
    PreparedStatement pst;
    FileInputStream fin;
    ShowErrors showerr;

    public DatabaseMethods() {
        dbconnect = new DatabaseConnect();
        conn = dbconnect.getConnection();
        showerr = new ShowErrors();
    }

    public ResultSet dbFetchAllFromTable(String table) {
        String sql = "select * from " + table + "";
        return selection(sql);
    }

    public ResultSet studentsForExam(int id) {
        String sql = " SELECT sc.id, sc.student_id, s.firstname, s.lastname, s.photo FROM exam_stud sc INNER JOIN student s ON s.id = sc.student_id WHERE sc.exam_id = " + id;
        return selection(sql);
    }

    public ResultSet examsForStudent(int id) {
        String sql = "SELECT sc.id, c.name, c.quest1, c.quest2, c.quest3, c.status, sc.exam_status FROM exam_stud sc INNER JOIN exam c ON c.id = sc.exam_id WHERE sc.student_id = " + id;
        return selection(sql);
    }

    public ResultSet answersForExamForStudent(int id) {
        String sql = "SELECT sc.id, c.quest1, c.quest2, c.quest3 FROM exam_student_answer sc INNER JOIN answers c ON c.id = sc.answers WHERE sc.exam_id = " + id;
        return selection(sql);
    }

    public int deleteFromTable(String table, int id) {
        int deleted = 0;
        String sql = "delete from " + table + " where id=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            deleted = pst.executeUpdate();
            return deleted;
        } catch (SQLException e) {
            showerr.displayDialog(e.toString());
        }
        return deleted;
    }

    public ResultSet fetchByCondition(String sql) {
        return selection(sql);
    }

    public ResultSet login(String table, String email, String password) throws SQLException {
        String sql = "SELECT * FROM `" + table + "` WHERE `email` = ? AND `password` = ?";

        pst = conn.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, password);

        return pst.executeQuery();
    }

    public ResultSet insertIntoStudentTable(String firstname, String lastname, String email, String password, File file) throws FileNotFoundException, SQLException {
        int inserted_rows = 0;        
            fin = new FileInputStream(file);
            int len = (int) file.length();

            String sql = "INSERT INTO `student`(`firstname`, `lastname`, `email`, `password`, `photo`) VALUES (?,?,?,?,?)";
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, firstname);
            pst.setString(2, lastname);
            pst.setString(3, email);
            pst.setString(4, password);
            pst.setBinaryStream(5, fin, len);

            inserted_rows = pst.executeUpdate();
            return pst.getGeneratedKeys();        
    }

    public int insertExamStudTable(int exam_id, int stud_id) {
        int inserted_rows = 0;
        try {
            String sql = "INSERT INTO `exam_stud`( `exam_id`, `student_id`, `exam_status`) VALUES (?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, exam_id);
            pst.setInt(2, stud_id);
            pst.setString(3, "pending");

            return pst.executeUpdate();
        } catch (Exception ex) {
            showerr.displayDialog(ex.toString());
        }
        return inserted_rows;
    }

    public int updateExamStatus(int exam_id, String status) {
        int updated_rows = 0;
        try {
            String sql = "UPDATE `exam` SET `status` = ? where id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(2, exam_id);
            pst.setString(1, status);

            updated_rows = pst.executeUpdate();

        } catch (SQLException ex) {
            showerr.displayDialog(ex.toString());
            return updated_rows;
        }
        return updated_rows;
    }

    public int addExam(Exam exam) {
        int inserted_rows = 0;
        try {
            String sql = "INSERT INTO `exam`(`name`, `quest1`, `quest2`, `quest3`, `status`) "
                    + "VALUES (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, exam.getName());
            pst.setString(2, exam.getQuest1());
            pst.setString(3, exam.getQuest2());
            pst.setString(4, exam.getQuest3());
            pst.setString(5, exam.getStatus());

            inserted_rows = pst.executeUpdate();

        } catch (SQLException ex) {
            showerr.displayDialog(ex.toString());
            return inserted_rows;
        }
        return inserted_rows;
    }

    public int doExam(int id, String status) {
        int updated_rows = 0;
        try {
            String sql = "UPDATE `exam_stud` SET `exam_status`=? WHERE `id`=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2, id);

            updated_rows = pst.executeUpdate();

        } catch (SQLException ex) {
            showerr.displayDialog(ex.toString());
            return updated_rows;
        }
        return updated_rows;
    }

    public ResultSet saveExam(String quest1, String quest2, String quest3) throws SQLException {
        int inserted_rows = 0;
        String sql = "INSERT INTO `answers`( `quest1`, `quest2`, `quest3`) VALUES (?,?,?)";
        pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, quest1);
        pst.setString(2, quest2);
        pst.setString(3, quest3);

        inserted_rows = pst.executeUpdate();
        return pst.getGeneratedKeys();

    }

    public int updateExamStudAnswer(int exam_id, int answers_id) {
        int updated_rows = 0;
        String sql = "INSERT INTO `exam_student_answer`( `exam_id`, `answers`) VALUES (?,?)";
        try {

            pst = conn.prepareStatement(sql);
            pst.setInt(1, exam_id);
            pst.setInt(2, answers_id);

            return pst.executeUpdate();

        } catch (SQLException ex) {
            showerr.displayDialog(ex.toString());
            return updated_rows;
        }
    }

    public ResultSet selection(String sql) {
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            showerr.displayDialog(e.toString());
        }
        return rs;
    }

    public int insertIntoTable(String sql) {
        int inserted_rows = 0;
        try {
            pst = conn.prepareStatement(sql);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            showerr.displayDialog(ex.toString());
        }
        return inserted_rows;
    }

}

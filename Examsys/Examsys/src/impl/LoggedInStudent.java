package impl;

public class LoggedInStudent {
    public static Student student;
    

    public LoggedInStudent() {
    }

    public static Student getStudent() {
        return student;
    }

    public static void setStudent(Student student) {
        LoggedInStudent.student = student;
    }

  
    
    
}

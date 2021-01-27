package impl;

public class ExamStudent {
//    variables    
    private String name;
    private int id;
    private String quest1;
    private String quest2;
    private String quest3;
    private String status;
    private String exam_status;

    public ExamStudent() {
    }

    public ExamStudent( int id, String name, String quest1, String quest2, String quest3, String status, String exam_status) {
        this.name = name;
        this.id = id;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;
        this.status = status;
        this.exam_status = exam_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuest1() {
        return quest1;
    }

    public void setQuest1(String quest1) {
        this.quest1 = quest1;
    }

    public String getQuest2() {
        return quest2;
    }

    public void setQuest2(String quest2) {
        this.quest2 = quest2;
    }

    public String getQuest3() {
        return quest3;
    }

    public void setQuest3(String quest3) {
        this.quest3 = quest3;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExam_status() {
        return exam_status;
    }

    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
    }

    
}

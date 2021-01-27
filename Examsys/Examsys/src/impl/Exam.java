package impl;

//Implimentation for Exams

public class Exam {

//    variables
    private int id;
    private String name;
    private String quest1;
    private String quest2;
    private String quest3;
    private String status;

//    Constructors
    public Exam() {
    }

    public Exam(int id, String name, String quest1, String quest2, String quest3, String status) {
        this.id = id;
        this.name = name;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;
        this.status = status;
    }

    public Exam(String name, String quest1, String quest2, String quest3, String status) {
        this.name = name;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;
        this.status = status;
    }
    
    

    
//    Getters and Setters for the variables
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    

}

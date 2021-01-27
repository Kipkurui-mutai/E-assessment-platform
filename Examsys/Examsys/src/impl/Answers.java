/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

public class Answers {
//    variables

    private int id;
    private String name;
    private String quest1;
    private String quest2;
    private String quest3;
    private String status;

//    Constructors
    public Answers() {
    }

    public Answers(int id, String quest1, String quest2, String quest3) {
        this.id = id;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;

    }

    public Answers(String quest1, String quest2, String quest3) {
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;

    }

//    Getters and Setters for the variables
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

}



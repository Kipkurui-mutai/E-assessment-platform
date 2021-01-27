package impl;

public class Question {
    
//    Variables
//    private int number;
    private String quest;

//    Constructors
    public Question() {
    }

    public Question( String quest) {
//        this.number = number;
        this.quest = quest;
    }

//Getters and Setters for the variables
//    public int getNumber() {
//        return number;
//    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }
    
}

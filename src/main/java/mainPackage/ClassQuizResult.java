package mainPackage;

import java.util.ArrayList;

public class ClassQuizResult {
    public  QuizInfo quizInfo;
    public ArrayList<String> emailList;
    public  ArrayList<String> nameList;

    public ArrayList<String> markList;

    ClassQuizResult(ArrayList<String> EmailList,ArrayList<String> NameList, ArrayList<String> MarkList )
    {
        this.emailList = EmailList;
        this.nameList = NameList;
        this.markList = MarkList;
    }
    public void set(ArrayList<String> EmailList,ArrayList<String> NameList, ArrayList<String> MarkList )
    {
        this.emailList = EmailList;
        this.nameList = NameList;
        this.markList = MarkList;
    }
}
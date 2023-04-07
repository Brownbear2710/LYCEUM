package mainPackage;

import java.util.ArrayList;

public class PublishResult implements  Runnable{

    public static QuizInfo quizInfo;
    public static ArrayList<String> emailList;
    public static ArrayList<String> markList;

    private void sendMark(String email, String mark)
    {
        String sub = "Marks for " + quizInfo.name;
        String text = "You scored "+ mark ;
        MailService.sendMail(email,text,sub);
    }

    PublishResult(ArrayList<String> EmailList,ArrayList<String> MarkList,QuizInfo qInfo)
    {
        emailList = EmailList;
        markList = MarkList;
        quizInfo = qInfo;
    }

    @Override
    public void run() {

        int sz = emailList.size();

        for (int i = 0; i< sz; i++)
        {
            sendMark(emailList.get(i), markList.get(i));
        }

    }
}

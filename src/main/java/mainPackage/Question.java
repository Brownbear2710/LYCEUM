package mainPackage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
    public  String question, answer;
    public String[] option;
    Question(String Question, String Option1, String Option2, String Option3, String Option4, String Answer)
    {
        question = Question;
        option = new String[4];
        option[0] = Option1;
        option[1] = Option2;
        option[2] = Option3;
        option[3] = Option4;
        answer  = Answer;
    }
    Question ()
    {
        question = "";
        option = new String[]{"","","",""};
        answer  = "";
    }

    void shuffle()
    {
        List<String> optionList = Arrays.asList(option);
        Collections.shuffle(optionList);
        optionList.toArray(option);
    }

}

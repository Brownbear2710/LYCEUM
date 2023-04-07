package mainPackage;

import java.sql.Timestamp;

public class QuizInfo {

    QuizInfo(String _name, String _id, String _classcode, Timestamp _start, Timestamp _end) {
        name = _name;
        id = _id;
        classcode = _classcode;
        start = _start;
        end = _end;
    }
    public String name, id, classcode;
    public Timestamp start, end;
}

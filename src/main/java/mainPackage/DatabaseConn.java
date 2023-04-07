package mainPackage;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.*;
import java.lang.annotation.Retention;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DatabaseConn {
    public static Connection databaselink;
    public static Connection getConnection() // Offline
    {
        String database = "lyceum";
        String databaseUser = "ldb";
        String databasePass = "easypassword";
        String url="jdbc:mysql://localhost/" + database;
//        String url="jdbc:mysql://127.0.0.1/" + database;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(url,databaseUser,databasePass);
//            System.out.println(databaselink.toString());//////////////////
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaselink;
    }

    public static void updatePassword(String password)
    {
        Connection conn = getConnection();
        try{
            PreparedStatement preparedStatement = conn.prepareStatement("update user set password = ? where email = ?");
            preparedStatement.setString(1, hash(password));
            preparedStatement.setString(2, Main.email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static boolean accountAlreadyExists(String email)
    {
        DatabaseConn connection = new DatabaseConn();
        Connection connectDB = connection.getConnection();
        try {
            PreparedStatement preparedStatementQ = connectDB.prepareStatement("select count(email) from user where email = ?");
            preparedStatementQ.setString(1, email);

            ResultSet rs = preparedStatementQ.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) return true;
            }
            return false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static String hash(String s)
    {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < s.length(); i++)
        {
            ret.append((char)(s.charAt(i) ^ 1234));
        }
        return String.valueOf(ret);
    }
    /// Hash password before calling the function
    public static int loginValidation(String email,String password)
    {
        DatabaseConn connection = new DatabaseConn();
        Connection conn = connection.getConnection();
        try {
            PreparedStatement preparedStatementQ = conn.prepareStatement("SELECT count(email) from user where email = " +
                    "? and password = ?");
            preparedStatementQ.setString(1, email);
            preparedStatementQ.setString(2, password);
            ResultSet rs = preparedStatementQ.executeQuery();
            while(rs.next())
            {
                if(rs.getInt(1) == 0)
                    return 0;
            }
            PreparedStatement ps = conn.prepareStatement("select usertype from user where email = ?");
            ps.setString(1,email);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if(rs.getString(1).equals("Student")) return 1;
                else return 2;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static int registerUser(String firstName, String lastName, String email, String password, String usertype) {
        DatabaseConn connection = new DatabaseConn();
        Connection connectDB = connection.getConnection();
        try {
            PreparedStatement preparedStatementU = connectDB.prepareStatement("insert into user values(?,?,?,?,?,?)");
            preparedStatementU.setString(1, email);
            preparedStatementU.setString(2, firstName);
            preparedStatementU.setString(3, lastName);
            preparedStatementU.setString(4, usertype);
            preparedStatementU.setString(5, password);
            preparedStatementU.setBinaryStream(6,null,0);

            PreparedStatement preparedStatementQ = connectDB.prepareStatement("select count(email) from user where email = ?");
            preparedStatementQ.setString(1, email);

            ResultSet rs = preparedStatementQ.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) return 0;
            }
            preparedStatementU.executeUpdate();

            if(usertype.equals("Student")) preparedStatementU = connectDB.prepareStatement("insert into student values(?);");
            else preparedStatementU = connectDB.prepareStatement("insert into teacher values(?);");
            preparedStatementU.setString(1, email);
            preparedStatementU.executeUpdate();
            return 1;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static AccountInfo getAccountInfo(String email) {
        DatabaseConn connection = new DatabaseConn();
        Connection conn = connection.getConnection();
        AccountInfo accountInfo = new AccountInfo();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from user where email = ?;");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountInfo.email = resultSet.getString(1);
                accountInfo.firstName = resultSet.getString(2);
                accountInfo.lastName = resultSet.getString(3);
                accountInfo.accountType = resultSet.getString(4);

                InputStream is = resultSet.getBinaryStream(6);
                Image image = null;
                if(is != null) image = new Image(is);

                accountInfo.dp = image;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return accountInfo;
    }

    public static void updateName(String firstName, String lastName, String email) {
        Connection conn = getConnection();
        try{
            PreparedStatement preparedStatement = conn.prepareStatement("update user set first_name = ?  where email = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();

            preparedStatement = conn.prepareStatement("update user set  last_name = ? where email = ?");
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean classcodeExists(String classcode) {
        Connection conn = getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select  * from classroom where classcode = ?");
            preparedStatement.setString(1, classcode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return  true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addNewClassroom(String subject, String classcode, String email) {
        Connection conn = getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into classroom values(?,?,?)");
            preparedStatement.setString(1, classcode);
            preparedStatement.setString(2, subject);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Pair<String, String>> getAllClassroomsForTeacher() {
        ArrayList<Pair<String,String>> arrayList = new ArrayList<Pair<String,String>>();
        Connection conn = getConnection();
        try {

            PreparedStatement preparedStatement = conn.prepareStatement("select classcode, subject from classroom where email = ?;");
            preparedStatement.setString(1, Main.email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Pair<String, String> pss = new Pair<String,String>(resultSet.getString(1), resultSet.getString(2));
                arrayList.add(pss);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList< Pair<String,Pair<String, String>> > getAllClassForStudent() {// <Classcode, <Teacher, Subject>>
        ArrayList<Pair<String, Pair<String, String>>> arrayList = new ArrayList<>();
        Connection conn = getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("select T1.subject, T2.first_name, T2.last_name, T1.classcode from\n" +
                    "(select * from classroom where classcode in\n" +
                    "(select classcode from studentclassjunction where email = ?)) T1\n" +
                    "left join user T2\n" +
                    "on T2.email = T1.email;");
            pstmt.setString(1, Main.email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pair<String, Pair<String, String>> p = new Pair<String, Pair<String, String>>(rs.getString(4),
                         new Pair<String, String>(rs.getString(2) + " " + rs.getString(3), rs.getString(1)));
                arrayList.add(p);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static boolean alreadyJoined(String classcode) {
        Connection conn = getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from studentclassjunction where email = ? and classcode = ?");
            preparedStatement.setString(1, Main.email);
            preparedStatement.setString(2, classcode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return  true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void joinClassRoom(String classcode) {
        Connection conn = getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into studentclassjunction values(?,?);");
            preparedStatement.setString(1, Main.email);
            preparedStatement.setString(2, classcode);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteClassroom(String classcode) {
        Connection conn = getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("delete from classroom where classcode = ?;");
            preparedStatement.setString(1, classcode);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean quizIDFound(String qid) {
        Connection conn = getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from quiz where quiz_id = ?;");
            preparedStatement.setString(1, qid);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
                return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<String> getAllEmail(String classcode)
    {
        getConnection();
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            PreparedStatement pstmt = databaselink.prepareStatement("select * from studentclassjunction where classcode = ?;");
            pstmt.setString(1, classcode);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                arrayList.add(rs.getString(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return  arrayList;
    }
    public static ArrayList<StudentInfo> getAllStudents(String classcode)
    {
        getConnection();
        ArrayList<StudentInfo> arrayList = new ArrayList<>();
        try{
            PreparedStatement pstmt =databaselink.prepareStatement(
                    "select email, CONCAT(first_name,' ', last_name) as full_name from user\n" +
                    "where email in (select email from studentclassjunction where classcode = ?);");
            pstmt.setString(1, classcode);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                StudentInfo s = new StudentInfo();
                s.email = rs.getString(1);
                s.name = rs.getString(2);
                arrayList.add(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return  arrayList;
    }
    public static void addQuiz(String qid, String name, String classcode, LocalDateTime start, LocalDateTime end) {
        Connection conn = getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into quiz values(?,?,?,?,?)");
            pstmt.setString(1,qid);
            pstmt.setString(2,name);
            pstmt.setString(3, classcode);
            Timestamp t = Timestamp.valueOf(start);
            pstmt.setTimestamp(4,t);
            t = Timestamp.valueOf(end);
            pstmt.setTimestamp(5,t);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("insert into marks values (?,?,?,?);");
            ArrayList<String> emails = getAllEmail(classcode);
            pstmt.setString(1, qid);
            pstmt.setString(3, "0");
            pstmt.setInt(4, 0);
            for(String s : emails) {
                pstmt.setString(2, s);
                pstmt.executeUpdate();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addQuestion(String q, String a, String b, String c, String d, String ans, String qid) {
        Connection conn = getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("Insert into question values(?,?,?,?,?,?,?)");
            pstmt.setString(1, q);
            pstmt.setString(2, a);
            pstmt.setString(3, b);
            pstmt.setString(4, c);
            pstmt.setString(5, d);
            pstmt.setString(6, ans);
            pstmt.setString(7, qid);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveClassroom(String classcode, String email) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from studentclassjunction where classcode = ? and email = ?;");
            preparedStatement.setString(1, classcode);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<QuizInfo> getQuizFromClassroom(String classcode) {
        Connection connection = getConnection();
        ArrayList<QuizInfo> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("select * from quiz where class_code = ? and quiz_id in\n" +
                    "(select quiz_id from marks where attended = 0 and email = ?);");
            pstmt.setString(1,classcode);
            pstmt.setString(2,Main.email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                QuizInfo q = new QuizInfo(rs.getString(2), rs.getString(1), rs.getString(3),
                        rs.getTimestamp(4), rs.getTimestamp(5));
                arrayList.add(q);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(arrayList, new Comparator<QuizInfo>() {
            public int compare(QuizInfo o1, QuizInfo o2) {
                return (o2.start.compareTo(o1.start));
            }
        });
        return arrayList;
    }

    public static ArrayList<QuizInfo> getAllQuizOfClassroom(String classcode) {
        getConnection();
        ArrayList<QuizInfo> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement(
                    "select * from quiz where class_code = ?;");
            pstmt.setString(1, classcode);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                QuizInfo q = new QuizInfo(rs.getString(2), rs.getString(1), rs.getString(3),
                        rs.getTimestamp(4), rs.getTimestamp(5));
                arrayList.add(q);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(arrayList, new Comparator<QuizInfo>() {
            public int compare(QuizInfo o1, QuizInfo o2) {
                return (o2.start.compareTo(o1.start));
            }
        });
        return arrayList;
    }

    public static ArrayList<Question> getQuizQuestions(String quizid) {
        getConnection();
        ArrayList<Question> qArr = new ArrayList<>();
        try {

            PreparedStatement ptsd = DatabaseConn.databaselink.prepareStatement("select * from question where quiz_id = ?");
            ptsd.setString(1,quizid);
            ResultSet rs = ptsd.executeQuery();
            while(rs.next())
            {
                Question q = new Question();
                q.question = rs.getString(1);
                q.option[0] = rs.getString(2);
                q.option[1] = rs.getString(3);
                q.option[2] = rs.getString(4);
                q.option[3] = rs.getString(5);
                q.answer = rs.getString(6);
//                q.shuffle();
                qArr.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return qArr;
    }

    public static void updateMark(String qid, String email, String mark)
    {
        getConnection();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement("update marks set mark = ? where email = ? and quiz_id = ?");
            pstmt.setString(1, mark);
            pstmt.setString(2, email);
            pstmt.setString(3, qid);
            pstmt.executeUpdate();
            pstmt = databaselink.prepareStatement("update marks set attended = 1 where email = ? and quiz_id = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, qid);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMark(String quizid) {
        getConnection();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement("select mark from marks where quiz_id = ? and email = ?;");
            pstmt.setString(1, quizid);
            pstmt.setString(2, Main.email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                return rs.getString(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static ArrayList<Pair<QuizInfo, String>> getPreviousQuiz(String classcode) {
        getConnection();
        ArrayList<Pair<QuizInfo, String>> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement("select * from quiz where class_code = ? and quiz_id in\n" +
                    "(select quiz_id from marks where email = ?);");
            pstmt.setString(1,classcode);
            pstmt.setString(2,Main.email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                QuizInfo q = new QuizInfo(rs.getString(2), rs.getString(1), rs.getString(3),
                        rs.getTimestamp(4), rs.getTimestamp(5));
                String mark = getMark(q.id);
                arrayList.add(new Pair<>(q,mark));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(arrayList, new Comparator<Pair<QuizInfo,String>>() {
            public int compare(Pair<QuizInfo,String> o1, Pair<QuizInfo,String> o2) {
                return (o2.getKey().start.compareTo(o1.getKey().start));
            }
        });
        return arrayList;
    }

    public static ArrayList<Pair<QuizInfo, String>> getAllPrevQuizForStudent() {
        getConnection();
        ArrayList<Pair<QuizInfo, String>> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement("select * from quiz where quiz_id in\n" +
                    "(select quiz_id from marks where email = ?);");
            pstmt.setString(1,Main.email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                QuizInfo q = new QuizInfo(rs.getString(2), rs.getString(1), rs.getString(3),
                        rs.getTimestamp(4), rs.getTimestamp(5));
                String mark = getMark(q.id);
                arrayList.add(new Pair<>(q,mark));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(arrayList, new Comparator<Pair<QuizInfo,String>>() {
            public int compare(Pair<QuizInfo,String> o1, Pair<QuizInfo,String> o2) {
                return (o2.getKey().start.compareTo(o1.getKey().start));
            }
        });
        return arrayList;
    }
    public static ClassQuizResult getResultInfo(QuizInfo q) throws SQLException {

        Connection conn = getConnection();
        PreparedStatement ptsd = conn.prepareStatement("select * from marks, user " +
                "where marks.email = user.email " +
                "and quiz_id = ?;");
        ArrayList<String> emailList = new ArrayList<>();
        ArrayList<String> nameList= new ArrayList<>();
        ArrayList<String> markList= new ArrayList<>();
        /*
         * 2 email
         * 3 mark
         * 6 fname
         * 7 lname
         */
        ptsd.setString(1, q.id);
        ResultSet rs = ptsd.executeQuery();
        while(rs.next())
        {
            emailList.add(rs.getString(2));
            markList.add(rs.getString(3));
            nameList.add(rs.getString(6) + " " + rs.getString(7));
        }
        return new ClassQuizResult(emailList, nameList, markList);
    }
    public static ArrayList<QuizInfo> getAllQuizTeacher(String email) {
        getConnection();
        ArrayList<QuizInfo> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement("select * from lyceum.quiz where class_code in\n" +
                    "(select classcode as class_code from lyceum.classroom where email = ?);");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QuizInfo q = new QuizInfo(rs.getString(2), rs.getString(1), rs.getString(3),
                        rs.getTimestamp(4), rs.getTimestamp(5));
                arrayList.add(q);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void updateImage(File file) throws FileNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        getConnection();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement("update user set dp = ? where email = ?");
            pstmt.setBinaryStream(1,fin, (int) file.length());
            pstmt.setString(2, Main.email);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Image getTeacherImage(String classcode) {
        getConnection();
        try {
            PreparedStatement pstmt = databaselink.prepareStatement("select email from classroom where classcode = ?;");
            pstmt.setString(1,classcode);
            ResultSet rs = pstmt.executeQuery();
            String email = null;
            while (rs.next()) {
                email = rs.getString(1);
            }
            AccountInfo accountInfo = getAccountInfo(email);
            return accountInfo.dp;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
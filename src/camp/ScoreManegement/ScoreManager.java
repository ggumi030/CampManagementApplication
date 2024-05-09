package camp.ScoreManegement;
import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.store.ScoreMapStore;
import camp.store.StudentStore;

import java.util.*;

public class ScoreManager {
    public final ScoreMapStore scoreStore;
    public final StudentStore studentStore;
    Scanner sc;

    public ScoreManager( StudentStore studentStore ) {
        this.scoreStore = new ScoreMapStore();
        this.studentStore = studentStore;
        sc = new Scanner(System.in);
    }


    public String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }


    public int getRightScoreId(){
        int scoreId;
        while (true) {
            System.out.print("회차를 입력해주세요: ");
            scoreId = sc.nextInt();
            sc.nextLine();//buffer cleaner

            if(0 < scoreId && scoreId<11){
                return scoreId;
            }
            System.out.println("회차는 1~10까지 입력할 수 있습니다.");
        }
    }

    public int getRightSubjectId() {
        int subjectId;
        while (true) {
            System.out.print("과목id를 입력해주세요: ");
            subjectId = sc.nextInt();
            sc.nextLine();//buffer cleaner

            if(0 < subjectId && subjectId<10){
                return subjectId;
            }
            System.out.println("과목id는 1~9까지 입력할 수 있습니다.");
        }
    }

    public int getRightScore() {
        int score;
        while (true) {
            System.out.print("시험점수를 입력해주세요: ");
            score = sc.nextInt();
            sc.nextLine();

            if(0 < score && score<101){
                return score;
            }
            System.out.println("점수는 1~100점까지만 입력 할 수 있습니다.");
        }
    }

    public String findStudentId(String studentId){
        List<Student> students = this.studentStore.findAll();
        for (Student student : students){
            if(student.getStudentId().equals(studentId)){
                return student.getStudentId();
            }
        }
        System.out.println("캠프에 등록되지 않은 학생입니다.");
        return null;
    }

    public void createScore() {
        String studentId = getStudentId();
        List<Student> students = this.studentStore.findAll();
        for (Student student : students){
            if (studentId.equals(student.getStudentId())){

                System.out.println("[student ID: " + studentId+"] 등록된"+ student.getStudentName() +"의 과목정보를 출력합니다." ) ;
                List<Subject> subjects = student.getSubjects();
                for(Subject subject : subjects) {
                    System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName());
                }

                scoreStore.checkExistKey(studentId);

                int subjectId = getRightSubjectId();

                int scoreId = getRightScoreId();

                int score = getRightScore();

                ScoreToGradeConversation grade = ScoreToGradeConversation.getGrade(subjectId ,score);

                Score studentScore = new Score(scoreId, subjectId, score, grade);

                scoreStore.save(studentId, studentScore);

                System.out.println("\n점수 등록 성공!");
      }else {
                System.out.println("캠프에 등록되지 않은 학생입니다");
                return;
            }
        }

    }

    public void updateRoundScoreBySubject() {
        HashMap<String, ArrayList<Score>> students = this.scoreStore.findAll();
        String studentId = getStudentId();
        int scoreId = getRightScoreId();

        int subjectId = getRightSubjectId();

        int newScore = getRightScore();

        for(int i =0; i < students.get(studentId).size();i++){
            for(Score score1 : students.get(studentId)){
                if(score1.getScoreId() == (scoreId) && score1.getsubjectId() == (subjectId)){
                    score1.setScore(newScore);
                    ScoreToGradeConversation newGrade = ScoreToGradeConversation.getGrade(subjectId ,newScore);
                    score1.setGrade(newGrade);
                    break;
                }
            }
        }

        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }


    public void inquireRoundGradeBySubject() {
        HashMap<String, ArrayList<Score>> scores = this.scoreStore.findAll();

        String studentId = getStudentId();

        if(Objects.equals(null,findStudentId(studentId))){
            return;
        }


        System.out.println("특정 과목의 회차별 등급을 조회합니다...");
        int subjectId = getRightSubjectId();

        List<Subject> subjects = getSelectedList();
        checkExistSubject(scores ,subjects,subjectId,studentId);
    }





    public List<Subject> getSelectedList(){
        List<Student> students = this.studentStore.findAll();
        List<Subject> subjects = List.of();

        for (Student student : students) {
            subjects = student.getSubjects();
        }

        return subjects;
    }



    public void checkExistSubject(HashMap<String, ArrayList<Score>> scores ,List<Subject> subjects,int subjectId,String studentId){
        for(Subject subject : subjects) {
            if (String.valueOf(subjectId).equals(subject.getSubjectId())) {
                showAllGrade(scores,subjectId,studentId);
                System.out.println("\n등급 조회 성공!");
                return;
            }

        }
        System.out.println("등록된 해당 과목이 없습니다");
    }


    public void showAllGrade(HashMap<String, ArrayList<Score>> scores ,int subjectId,String studentId){
        for (int i = 0; i < scores.get(studentId).size(); i++) { //3
            if(scores.get(studentId).get(i).getsubjectId() == subjectId) {//3

                System.out.println(scores.get(studentId).get(i).getScoreId() + "회차 " + "등급 : " + scores.get(studentId).get(i).getGrade());//3

            }
        }
    }

}

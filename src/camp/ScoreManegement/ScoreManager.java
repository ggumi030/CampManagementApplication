package camp.ScoreManegement;
import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.store.ScoreMapStore;
import camp.store.StudentStore;
import camp.store.SubjectStore;

import java.util.*;

public class ScoreManager {
    public final ScoreMapStore scoreStore;
    public final StudentStore studentStore;
    public final SubjectStore subjectStore;
    Scanner sc;

    public ScoreManager( StudentStore studentStore ) {
        this.scoreStore = new ScoreMapStore();
        this.studentStore = studentStore;
        this.subjectStore = new SubjectStore();
        sc = new Scanner(System.in);
    }

    //수강생 쪽으로 이동
    //수강생 번호 입력
    public String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    //범위내 회차 입력 (1~10)
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
    //과목 쪽으로 이동
    //범위내 과목id 입력 (1 ~9)
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
    //범위내 점수 입력 (1 ~ 100)
    public int getRightScore() {
        int score;
        while (true) {
            System.out.print("시험점수를 입력해주세요: ");
            score = sc.nextInt();
            sc.nextLine();//buffer cleaner

            if(0 < score && score<101){
                return score;
            }
            System.out.println("점수는 1~100점까지만 입력 할 수 있습니다.");
        }
    }

    //수강생 쪽으로 이동
    public String findStudentId(String studentId){ //수강생 고유번호 입력받으면 리스트에 있는지 확인하는 메서드
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
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        //예외처리 : 등록된거 또 등록하면 안 되게 =>완료
        List<Student> students = this.studentStore.findAll();
        for (Student student : students){
            if (studentId.equals(student.getStudentId())) {

                System.out.println("[student ID: " + studentId + "] " + student.getStudentName() + "가 선택한 과목정보를 출력합니다.");
                List<Subject> subjects = student.getSubjects();
                for (Subject subject : subjects) {
                    System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName());
                }

                scoreStore.checkExistKey(studentId);
                int subjectId = getRightSubjectId();
                int scoreId = getRightScoreId();
                int score = getRightScore();
                ScoreToGradeConversation grade = ScoreToGradeConversation.getGrade(subjectId, score);

                Score studentScore = new Score(scoreId, subjectId, score, grade);
                scoreStore.save(studentId, studentScore);
                System.out.println("\n점수 등록 성공!");

            }
        }
        findStudentId(studentId);
    }
    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject() {
        //예외처리 : 등록되지 않은 과목 수정 불가하게
        HashMap<String, ArrayList<Score>> scores = this.scoreStore.findAll();
        System.out.println("시험 점수를 수정합니다...");
        String studentId = getStudentId();

        List<Student> students = this.studentStore.findAll();
        for (Student student : students){
            if (studentId.equals(student.getStudentId())) {
                System.out.println("[student ID: " + studentId + "] " + student.getStudentName() + "가 점수를 등록한 과목정보를 출력합니다.");
                List<Subject> subjects = student.getSubjects();
                for (Subject subject : subjects) {
                    for (int i = 0; i < scores.get(studentId).size(); i++) { //3

                        if(Objects.equals(String.valueOf(scores.get(studentId).get(i).getsubjectId()),subject.getSubjectId())) {//3
                            System.out.println("[" + subject.getSubjectName() + "] " + scores.get(studentId).get(i).getScoreId() + "회차 " + "점수 : " + scores.get(studentId).get(i).getScore() + " ");// 선택한 과목 점수 전체 출력

                        }
                    }
                }
            }

        }
        int subjectId = getRightSubjectId();
        int scoreId = getRightScoreId();
        int newScore = getRightScore();

        for (int i = 0; i < scores.get(studentId).size(); i++) {
            for (Score score1 : scores.get(studentId)) {
                if (score1.getScoreId() == (scoreId) && score1.getsubjectId() == (subjectId)) {
                    score1.setScore(newScore);
                    ScoreToGradeConversation newGrade = ScoreToGradeConversation.getGrade(subjectId, newScore);
                    score1.setGrade(newGrade);
                    break;
                }
            }
        }
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {
        HashMap<String, ArrayList<Score>> scores = this.scoreStore.findAll();

        String studentId = getStudentId(); // 관리할 수강생 고유 번호

        if(Objects.equals(null,findStudentId(studentId))){
            return;
        }

        // 기능 구현 (조회할 특정 과목)
        System.out.println("특정 과목의 회차별 등급을 조회합니다...");
        List<Student> students = this.studentStore.findAll();
        for (Student student : students){
            if (studentId.equals(student.getStudentId())) {

                System.out.println("[student ID: " + studentId + "] " + student.getStudentName() + "가 선택한 과목정보를 출력합니다.");
                List<Subject> subjects = student.getSubjects();
                for (Subject subject : subjects) {
                    System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName());
                }
            }
        }
        int subjectId = getRightSubjectId();

        List<Subject> subjects = getSelectedList();
        checkExistSubject(scores ,subjects,subjectId,studentId);

    }


    //inquire
    //1 student가 선택한 subject list를 가져오는 함수
    public List<Subject> getSelectedList(){
        List<Student> students = this.studentStore.findAll();
        List<Subject> subjects = List.of();

        for (Student student : students) {
            subjects = student.getSubjects();
        }

        return subjects;
    }


    //2 선택한 subject 중에서 입력된 subjectid와 같은 게 있는지 확인하는 함수
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

    //3 입력된 subjectid와 같은 Score 객체에의 모든 회차와 등급 출력하는 함수
    public void showAllGrade(HashMap<String, ArrayList<Score>> scores ,int subjectId,String studentId){
        for (int i = 0; i < scores.get(studentId).size(); i++) { //3
            if(scores.get(studentId).get(i).getsubjectId() == subjectId) {//3
                System.out.println("[" + subjectStore.findAll().get(i).getSubjectName() + "] 과목 등급 조회");
                System.out.println(scores.get(studentId).get(i).getScoreId() + "회차 " + "등급 : " + scores.get(studentId).get(i).getGrade());//3

            }
        }
    }

}

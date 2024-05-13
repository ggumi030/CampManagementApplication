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
            sc.nextLine();//buffer cleaner

            if(0 < score && score<101){
                return score;
            }
            System.out.println("점수는 1~100점까지만 입력 할 수 있습니다.");
        }
    }

    public String findStudentId(String studentId){
        Optional<Student> foundUser = this.studentStore.findById(studentId);
        if (foundUser.isPresent()) {
            return foundUser.get().getStudentId();
        }

        System.out.println("캠프에 등록되지 않은 학생입니다.");
        return null;

    }
    
    public void createScore() {
        String studentId = getStudentId();
        List<Student> students = this.studentStore.findAll();
        Optional<Student> optionalStudent = this.studentStore.findById(studentId);

        if (optionalStudent.isEmpty()) {
            System.out.println("캠프에 등록되지 않은 학생입니다.");
            return;
        }

        Student student = optionalStudent.get();

        System.out.println("[student ID: " + student.getStudentId() + "] " + student.getStudentName() + "가 선택한 과목정보를 출력합니다.");

        student.getSubjects()
            .forEach(subject -> System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName()));

        scoreStore.checkExistKey(studentId);
        int subjectId = getRightSubjectId();
        Optional<Subject> subjectOptional = student.getSubjects().stream()
                .filter(s -> s.getSubjectId().equals("" + subjectId))
                .findFirst();
        if (subjectOptional.isEmpty()) {
            System.out.println("선택하지 않은 과목입니다");
            return;
        }
        int scoreId = getRightScoreId();
        int score = getRightScore();



        ScoreToGrade grade = ScoreToGrade.getGrade(subjectId, score);

        Score studentScore = new Score(scoreId, subjectId, score, grade);
        scoreStore.save(student.getStudentId(), studentScore);
        System.out.println("\n점수 등록 성공!");
    }

    public void updateRoundScoreBySubject() {
        Optional<Student> optionalStudent = this.studentStore.findById(getStudentId());
        if (optionalStudent.isEmpty()) {
            System.out.println("캠프에 등록되지 않은 학생입니다.");
            return;
        }
        
        Student student = optionalStudent.get();
        List<Score> scores = this.scoreStore.findScoresByStudentId(student.getStudentId());

        System.out.println("시험 점수를 수정합니다...");
        System.out.println("[student ID: " + student.getStudentId() + "] " + student.getStudentName() + "가 점수를 등록한 과목정보를 출력합니다.");
        List<Subject> subjects = student.getSubjects();

        for (Subject subject : subjects) {
            for (int i = 0; i < scores.size(); i++) {
                if(Objects.equals(String.valueOf(scores.get(i).getSubjectId()),subject.getSubjectId())) {
                    System.out.println("[" + subject.getSubjectName() + "] " + scores.get(i).getScoreId() + "회차 " + "점수 : " + scores.get(i).getScore() + " ");
                }
            }
        }

        int subjectId = getRightSubjectId();

        Optional<Subject> subjectOptional = student.getSubjects().stream()
                .filter(s -> s.getSubjectId().equals("" + subjectId))
                .findFirst();
        if (subjectOptional.isEmpty()) {
            System.out.println("선택하지 않은 과목");
            return;
        }
        int scoreId = getRightScoreId();

        List<Score> subjectScores = scores.stream()
            .filter(score -> score.getSubjectId() == subjectId)
            .toList();
        Optional<Score> optionalScore = subjectScores.stream()
            .filter(score -> score.getScoreId() == scoreId)
            .findFirst();
        if (optionalScore.isEmpty()) {
            System.out.println("등록되지 않은 회차");
            return;
        }
        Score score = optionalScore.get();

        int newScore = getRightScore();

        score.setScore(newScore);
        ScoreToGrade newGrade = ScoreToGrade.getGrade(subjectId, newScore);
        score.setGrade(newGrade);
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {
        Map<String, List<Score>> scores = this.scoreStore.findAll();

        String studentId = getStudentId(); // 관리할 수강생 고유 번호

        if(Objects.equals(null,findStudentId(studentId))){
            return;
        }
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
        checkExistSubject(subjects,subjectId,studentId);
    }

    public List<Subject> getSelectedList(){
        List<Student> students = this.studentStore.findAll();
        List<Subject> subjects = List.of();

        for (Student student : students) {
            subjects = student.getSubjects();
        }
        return subjects;
    }


    public void checkExistSubject(List<Subject> subjects,int subjectId,String studentId){
        for(Subject subject : subjects) {
            if (String.valueOf(subjectId).equals(subject.getSubjectId())) {
                showAllGrade(this.scoreStore.findAll(), subjectId,studentId);
                System.out.println("\n등급 조회 성공!");
                return;
            }
        }
        System.out.println("등록된 해당 과목이 없습니다");
    }

    public void showAllGrade(Map<String, List<Score>> scores ,int subjectId,String studentId){
        for (int i = 0; i < scores.get(studentId).size(); i++) { //3
            if(scores.get(studentId).get(i).getSubjectId() == subjectId) {//3
                System.out.println("[" + subjectStore.findAll().get(i).getSubjectName() + "] 과목 등급 조회");
                System.out.println(scores.get(studentId).get(i).getScoreId() + "회차 " + "등급 : " + scores.get(studentId).get(i).getGrade());//3

            }
        }
    }

}

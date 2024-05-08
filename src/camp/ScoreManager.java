package camp;

import camp.model.Score;
import camp.store.ScoreStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ScoreManager {
    private final ScoreStore scoreStore;
    Scanner sc;

    public ScoreManager() {
        this.scoreStore = new ScoreStore();
        sc = new Scanner(System.in);
    }

    //수강생 번호 입력
    private String getStudentId() {
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
    public int regetRightScore() {
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

    //등급
    public char getGrade(int subjectId ,int score){
        int grade_score = score;
        Character grade = ' ';
        if(subjectId >5) {//6-9 선택
            if (grade_score > 89) {
                return grade = 'A';
            } else if (90 > grade_score && grade_score > 79) {
                return grade = 'B';
            } else if (80 > grade_score && grade_score > 69) {
                return grade = 'C';
            } else if (70 > grade_score && grade_score > 59) {
                return grade = 'D';
            } else if (60 > grade_score && grade_score > 49) {
                return grade = 'F';
            } else {
                return grade = 'N';
            }
        }else {//1-5 필수
            if (grade_score > 94) {
                return grade = 'A';
            } else if (grade_score > 89) {
                return grade = 'B';
            } else if (grade_score > 79) {
                return grade = 'C';
            } else if ( grade_score > 69) {
                return grade = 'D';
            } else if (grade_score > 59) {
                return grade = 'F';
            } else {
                return grade = 'N';
            }
        }
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현
        // String(key) = StudentId, arraylist(value) = 회차,과목ID, 시험점수, 등급
        //value  Score => 회차1 과목java 점수50 등급C
        // Score => 회차2 과목Spring 점수80 등급B
        // scores.get(studentId)
        //key (studentId) => key
        // 등록되지 않은 student id처리
        // scores.containsKey(studentId) == key값이 1이 있냐-> T==> 점수 등록할수있게/ F ==> 등록이 안된 사용자입니다(key new)

        //id : key값 없으면 새로 생성
        // if containsKey(key) 확인
        scoreStore.checkExistKey(studentId);


        //회차입력 (1~10범위만 입력받게)
        int scoreId = getRightScoreId();

        //과목입력 (1~9범위만 입력)
        int subjectId = getRightSubjectId();

        //점수입력 (1~100 범위만 입력)
        int score = getRightScore();

        //점수에 따른 등급 생성
        char grade = getGrade(subjectId ,score);


        //Score 인스턴스생성 <= 입력받은 값 다 넣기
        Score studentScore = new Score(scoreId, subjectId, score, grade);

        // ScoreStore 저장
        scoreStore.save(studentId, studentScore); //ggumi : 얘랑 ScoreStore class에서 18번째줄 저장 함수 추가햇어여

        //출력 확인
        //scoreStore.findAll() => return Hashmap

        for(int i =0; i < scoreStore.findAll().get(studentId).size();i++){
            System.out.println("회차 : " + scoreStore.findAll().get(studentId).get(i).getScoreId());
        }

        System.out.println("\n점수 등록 성공!");
    }

    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject() {
        HashMap<String, ArrayList<Score>> students = this.scoreStore.findAll();
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        // 회차 입력
        int scoreId = getRightScoreId();
        //과목입력
        int subjectId = getRightSubjectId();
        //점수 수정값 들어가야함
        int newScore = regetRightScore();


        //해당하는 과목, 회차의 점수 수정하기
        for(int i =0; i < students.get(studentId).size();i++){
            //과목명
            //수정??
            //students.get(studentId).get(i).

            //Hash map
            //1 -> [Score{회차, 과목, 점수, 등급},
            //      Score{회차, 과목, 점수, 등급},
            //      Score{회차, 과목, 점수, 등급}]
            //score1.getScore()// 과거 점수

            //students.get(studentId).get(i).getsubjectId().setScore(newScore);
            //

            for(Score score1 : students.get(studentId)){
                if(score1.getScoreId() == (scoreId) && score1.getsubjectId() == (subjectId)){
                    score1.setScore(newScore); //setScore로 해보세여 !!
                    char newGrade = getGrade(subjectId ,newScore);
                    score1.setGrade(newGrade);
                    break;
                }
            }
        }



        //


        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {
        HashMap<String,ArrayList<Score>> students = this.scoreStore.findAll();

        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (조회할 특정 과목)
        System.out.println("특정 과목의 등급을 조회합니다...");
        int subjectId = getRightSubjectId();

        //선택한 과목 id만 출력하도록 수정
        for(int i =0; i < students.get(studentId).size();i++){
            System.out.println(students.get(studentId).get(i).getScoreId() + "회차 "  + "등급 : " + students.get(studentId).get(i).getGrade());
        }

        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현

        System.out.println("\n등급 조회 성공!");
    }
}

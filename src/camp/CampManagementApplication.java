package camp;

import camp.model.Score;
import camp.store.StudentStore;
import camp.store.SubjectStore;
import camp.store.ScoreStore;

import java.util.*;

public class CampManagementApplication {
    private static Scanner sc;
    private final StudentStore studentStore;
    private final SubjectStore subjectStore;
    private final ScoreStore scoreStore;
    private static StudentManager studentManagement = new StudentManager();


    public CampManagementApplication() {
        this.studentStore = new StudentStore();
        this.subjectStore = new SubjectStore();
        this.scoreStore = new ScoreStore();
        sc = new Scanner(System.in);
    }

    public void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            System.out.println();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> studentManagement.displayStudentView();
                case 2 -> displayScoreView();
                case 3 -> flag = false;
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private  void displayScoreView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore();
                case 2 -> updateRoundScoreBySubject();
                case 3 -> inquireRoundGradeBySubject();
                case 4 -> flag = false;
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    //수강생 번호 입력
    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    //범위내 회차 입력 (1~10)
    public static int getRightScoreId(){
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
    private void createScore() {
        String studentId = getStudentId();
        System.out.println("시험 점수를 등록합니다...");
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
    private void updateRoundScoreBySubject() {
        HashMap<String,ArrayList<Score>> students = this.scoreStore.findAll();
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

                    for(Score score1 : students.get(studentId)){
                        if(score1.getScoreId() == (scoreId) && score1.getsubjectId() == (subjectId)){
                            score1.setScore(newScore); //setScore로 해보세여 !!
                            char newGrade = getGrade(subjectId ,newScore);
                            score1.setGrade(newGrade);
                            break;
                        }
                    }
        }

        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() {
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
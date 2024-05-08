package camp;


import camp.ScoreManegement.ScoreManager;
import camp.model.Student;
import camp.model.Subject;

import camp.model.Score;

import camp.store.StudentStore;
import camp.store.SubjectStore;
import camp.store.ScoreMapStore;

import java.util.*;

public class CampManagementApplication {
    private static Scanner sc;
    private final StudentStore studentStore;
    private final SubjectStore subjectStore;

    private final ScoreMapStore scoreStore;
    private final ScoreManager scoreManager;
    private static StudentManager studentManagement = new StudentManager();



    public CampManagementApplication() {
        this.studentStore = new StudentStore();
        this.subjectStore = new SubjectStore();
        this.scoreStore = new ScoreMapStore();
        this.scoreManager = new ScoreManager(this.studentStore);
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
            int input = sc.nextInt();

            switch (input) {

                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료

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
                case 1 -> scoreManager.createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> scoreManager.updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> scoreManager.inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }
}
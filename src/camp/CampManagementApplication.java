package camp;


import camp.ScoreManegement.ScoreManager;
import camp.store.StudentStore;
import camp.store.SubjectStore;

import java.util.Scanner;

public class CampManagementApplication {
    private final Scanner sc;

    private final ScoreManager scoreManager;
    private final StudentManager studentManager;

    public CampManagementApplication() {
        StudentStore studentStore = new StudentStore();
        SubjectStore subjectStore = new SubjectStore();

        this.scoreManager = new ScoreManager(studentStore);
        this.studentManager = new StudentManager(studentStore, subjectStore);

        sc = new Scanner(System.in);
    }

    public void displayMainView() throws InterruptedException {
        while (true) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();
            switch (input) {
                case 1 -> studentManager.displayStudentView();
                case 2 -> displayScoreView();
                case 3 -> {
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }
                default -> System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
            }
        }
    }

    private void displayScoreView() {
        while (true) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> scoreManager.createScore();
                case 2 -> scoreManager.updateRoundScoreBySubject();
                case 3 -> scoreManager.inquireRoundGradeBySubject();
                case 4 -> {
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    return;
                }
            }
        }
    }
}
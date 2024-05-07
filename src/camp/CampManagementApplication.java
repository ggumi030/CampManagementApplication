package camp;

import camp.model.Student;
import camp.model.Subject;
import camp.store.StudentStore;
import camp.store.SubjectStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CampManagementApplication {
    private static Scanner sc;
    private final StudentStore studentStore;
    private final SubjectStore subjectStore;

    public CampManagementApplication() {
        this.studentStore = new StudentStore();
        this.subjectStore = new SubjectStore();
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
                // case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private void displayStudentView() {
        while (true) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> {
                    return;
                }
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    return;
                }
            }
        }
    }

    // 수강생 등록
    private void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        //
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();
        // 기능 구현 (필수 과목, 선택 과목)

        List<Subject> selectedSubjects = selectSubjects();

        if (selectedSubjects == null) {
            System.out.println("과목 선택이 올바르지 않습니다. 등록을 취소합니다.");
            return;
        }

        Student student = new Student(studentName, selectedSubjects); // 수강생 인스턴스 생성 코드

        // is valid subject?
        if (!student.isValidSubjects()) {
            System.out.println("과목 선택 잘못함");
            return;
        }

        this.studentStore.save(student);

        System.out.println("수강생 등록 성공!\n");
    }

    private List<Subject> selectSubjects() {
        System.out.println("과목 선택: 최소 3개의 필수과목과 2개의 선택과목을 선택해야 합니다.");
        List<Subject> all = this.subjectStore.findAll();

        // TODO(민혁님)
        // 필수과목: id)name id)name id)name id)name
        System.out.println("필수 과목: subjectId)subjectName 2)객체지향 3)Spring 4)JPA 5)MySQL");
        List<Subject> mandatoryList = all.stream().filter(Subject::isMandatory).toList();
        System.out.println("선택 과목: subjectId)subjectName 7)Spring Security 8)Redis 9)MongoDB");
        List<Subject> choiceList = all.stream().filter(Subject::isChoice).toList();
        System.out.print("선택할 과목의 번호를 입력하세요 (예: 1 2 3 6 7): ");
        sc.nextLine(); // Buffer Clear
        String[] inputs = sc.nextLine().split(" ");

        List<Subject> selectedSubjects = new ArrayList<>();

        for (String number : inputs) {
            Optional<Subject> subjectOptional = this.subjectStore.findById(number);
            if (subjectOptional.isEmpty()) {
                System.out.println("잘못된 과목 번호입니다: " + number);
                return null;
            }
            selectedSubjects.add(subjectOptional.get());
        }
        return selectedSubjects;
    }

    // TODO: 꾸미기(성훈님)
    private void inquireStudent() {
        // 목록 조회
        List<Student> students = this.studentStore.findAll();
        // string format
        System.out.printf("%s | %s\n", "고유번호", "이름");
        for (Student student : students) {
            System.out.printf("%s | %s\n", student.getStudentId(), student.getStudentName());
        }
    }

}

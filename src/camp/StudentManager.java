package camp;

import camp.model.Student;
import camp.model.Subject;
import camp.store.StudentStore;
import camp.store.SubjectStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class StudentManager {
    private final Scanner sc = new Scanner(System.in);

    private final StudentStore studentStore;
    private final SubjectStore subjectStore;

    public StudentManager(StudentStore studentStore, SubjectStore subjectStore) {
        this.studentStore = studentStore;
        this.subjectStore = subjectStore;
    }

    public void displayStudentView() {
        while (true) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            System.out.println();
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent();
                case 2 -> inquireStudent();
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

    private void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

        List<Subject> selectedSubjects = selectSubjects();

        if (selectedSubjects == null) {
            System.out.println("과목 선택이 올바르지 않습니다. 등록을 취소합니다.");
            return;
        }

        Student student = new Student(studentName, selectedSubjects);

        if (!student.isValidSubjects()) {
            System.out.println("과목 선택을 잘못하셨습니다");
            return;
        }

        studentStore.save(student);

        System.out.println("수강생 등록 성공!\n");
    }

    private List<Subject> selectSubjects() {
        System.out.println("과목 선택: 최소 3개의 필수과목과 2개의 선택과목을 선택해야 합니다.");
        List<Subject> subjects = subjectStore.findAll();

        System.out.println("필수 과목: ");
        subjects.stream().filter(Subject::isMandatory)
                .forEach(subject -> System.out.printf("%s) %s ", subject.getSubjectId(), subject.getSubjectName()));

        System.out.println("\n선택 과목: ");
        subjects.stream().filter(Subject::isChoice)
                .forEach(subject -> System.out.printf("%s) %s ", subject.getSubjectId(), subject.getSubjectName()));

        System.out.print("\n선택할 과목의 번호를 입력하세요 (예: 1 2 3 6 7): ");
        sc.nextLine();
        String[] inputs = sc.nextLine().split(" ");

        List<Subject> selectedSubjects = new ArrayList<>();

        for (String subjectId : inputs) {
            Optional<Subject> subjectOptional = subjectStore.findById(subjectId);
            if (subjectOptional.isEmpty()) {
                System.out.println("잘못된 과목 번호입니다: " + subjectId);
                return null;
            }
            selectedSubjects.add(subjectOptional.get());
        }
        return selectedSubjects;
    }

    private void inquireStudent() {
        List<Student> students = studentStore.findAll();
        System.out.printf("%5s | %5s\n", "고유번호", "이  름");
        for (Student student : students) {
            System.out.printf("%6s번 | %4s\n", student.getStudentId(), student.getStudentName());
        }
    }

}

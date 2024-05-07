package camp.model;

import java.util.List;

public class Student {
    private String studentId;
    private String studentName;

    private List<Subject> subjects; // 수강한 과목 목록


    public Student(String studentName, List<Subject> subjects) {
        this.studentName = studentName;
        this.subjects = subjects;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    // domain logic
    public boolean isValidSubjects() {
        return this.hasEnoughMandatorySubjects()
                && this.hasEnoughChoiceSubjects();
    }

    // mandatory 최소 3개와 2개 이상의 choice를 가져야 한다.
    private boolean hasEnoughMandatorySubjects() {
        return this.subjects.stream().filter(Subject::isMandatory).count() >= 3;
    }

    private boolean hasEnoughChoiceSubjects() {
        return this.subjects.stream().filter(Subject::isChoice).count() >= 2;
    }
}



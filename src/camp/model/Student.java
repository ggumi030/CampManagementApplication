package camp.model;

import java.util.List;

public class Student {
    private String studentId;
    private String studentName;

    private List<Subject> subjects; // 수강한 과목 목록






    public Student(String seq, String studentName, List<Subject> subjects) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjects = subjects;


    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }


}



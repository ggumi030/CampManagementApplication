package camp.store;

import camp.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentStore extends AbstractStore<Student> {
    private final List<Student> students;

    public StudentStore() {
        this.students = new ArrayList<>();
    }

    @Override
    public void save(Student student) {
        student.setStudentId("" + this.getNextSequence());
        this.students.add(student);
    }

    @Override
    public Optional<Student> findById(String id) {
        return this.students.stream()
                .filter(student -> student.getStudentId().equals(id))
                .findFirst();
    }

    @Override
    public List<Student> findAll() {
        return this.students;
    }
}

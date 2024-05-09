package camp.store;

import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectStore extends AbstractStore<Subject> {
    private final List<Subject> subjects;

    // TODO: init 은 외부로 분리
    public SubjectStore() {
        this.subjects = new ArrayList<>(List.of(
                new Subject(
                        "" + this.getNextSequence(),
                        "Java",
                        Subject.Type.MANDATORY
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "객체지향",
                        Subject.Type.MANDATORY
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "Spring",
                        Subject.Type.MANDATORY
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "JPA",
                        Subject.Type.MANDATORY
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "MySQL",
                        Subject.Type.MANDATORY
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "디자인 패턴",
                        Subject.Type.CHOICE
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "Spring Security",
                        Subject.Type.CHOICE
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "Redis",
                        Subject.Type.CHOICE
                ),
                new Subject(
                        "" + this.getNextSequence(),
                        "MongoDB",
                        Subject.Type.CHOICE
                )));

    }

    @Override
    public Optional<Subject> findById(String id) {
        return this.subjects.stream()
                .filter(subject -> subject.getSubjectId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Subject subject) {
        subject.setSubjectId("" + this.getNextSequence());
        this.subjects.add(subject);
    }

    @Override
    public List<Subject> findAll() {
        return this.subjects;
    }
}

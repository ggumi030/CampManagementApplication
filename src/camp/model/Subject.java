package camp.model;

public class Subject {
    private final String subjectName;
    private final Type subjectType;
    private String subjectId;


    public Subject(String seq, String subjectName, Type subjectType) {
        this.subjectId = seq;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }
    // Setter

    // Getter
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Type getSubjectType() {
        return subjectType;
    }

    // logic
    public boolean isMandatory() {
        return this.subjectType.equals(Type.MANDATORY);
    }

    public boolean isChoice() {
        return this.subjectType.equals(Type.CHOICE);
    }


    public enum Type {
        MANDATORY,
        CHOICE
    }
}

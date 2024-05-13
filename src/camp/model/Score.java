package camp.model;

import camp.ScoreManegement.ScoreToGrade;

public class Score {
    private int scoreId;
    private int subjectId;
    private int score;
    private ScoreToGrade grade;

    public Score(int scoreId, int subjectId, int score, ScoreToGrade grade) {
        this.scoreId = scoreId;
        this.subjectId = subjectId;
        this.score = score;
        this.grade = grade;

    }

    // Getter
    public int getScoreId() {
        return this.scoreId;
    }

    // enterprise
    public int getSubjectId() {
        return this.subjectId;
    }

    public int getScore() {
        return this.score;
    }

    public ScoreToGrade getGrade() {
        return this.grade;
    }

    //Setter
    public void setScore(int score) {
        this.score = score;
    }

    public void setGrade(ScoreToGrade grade) {
        this.grade = grade;
    }
}

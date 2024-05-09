package camp.model;

import camp.ScoreManegement.ScoreToGradeConversation;

public class Score {
    private int scoreId; //회차 점수 고유 id
    private int subjectId; //과목id
    private int score; //시험점수
    private ScoreToGradeConversation grade; //시험 등급 (A,B,C,D...)


    public Score(int scoreId, int subjectId, int score, ScoreToGradeConversation grade) {
        this.scoreId = scoreId;
        this.subjectId = subjectId;
        this.score = score;
        this.grade = grade;

    }

    // Getter
    public int getScoreId() {
        return this.scoreId;
    }

    public int getsubjectId() {
        return this.subjectId;
    }

    public int getScore() {
        return this.score;
    }

    public ScoreToGradeConversation getGrade() {
        return this.grade;
    }

    //Setter
    public void setScore(int score) {
        this.score = score;
    }

    public void setGrade(ScoreToGradeConversation grade) {
        this.grade = grade;
    }

}

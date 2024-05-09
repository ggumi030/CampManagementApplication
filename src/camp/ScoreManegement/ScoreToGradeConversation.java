package camp.ScoreManegement;

public enum ScoreToGradeConversation {
    A,
    B,
    C,
    D,
    F,
    N;

    //등급 enum
    public static ScoreToGradeConversation getGrade(int subjectId, int score) {
        if (subjectId > 5) {//6-9 선택
            if (score > 89) {
                return A;
            } else if (score > 79) {
                return B;
            } else if (score > 69) {
                return C;
            } else if (score > 59) {
                return D;
            } else if (score > 49) {
                return F;
            } else {
                return N;
            }
        } else {//1-5 필수
            if (score > 94) {
                return A;
            } else if (score > 89) {
                return B;
            } else if (score > 79) {
                return C;
            } else if (score > 69) {
                return D;
            } else if (score > 59) {
                return F;
            } else {
                return N;
            }
        }
    }

}

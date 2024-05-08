package camp.ScoreManegement;

public enum ScoreToGradeConverstion {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    F("F"),
    N("N");

    private String grade;

    private ScoreToGradeConverstion(String s) {
        this.grade = s;
    }


    //등급 enum
    public static ScoreToGradeConverstion getGrade(int subjectId , int score){
        int grade_score = score;
        if(subjectId >5) {//6-9 선택
            if (grade_score > 89) {
                return A;
            } else if (90 > grade_score && grade_score > 79) {
                return B;
            } else if (80 > grade_score && grade_score > 69) {
                return C;
            } else if (70 > grade_score && grade_score > 59) {
                return D;
            } else if (60 > grade_score && grade_score > 49) {
                return F;
            } else {
                return N;
            }
        }else {//1-5 필수
            if (grade_score > 94) {
                return A;
            } else if (grade_score > 89) {
                return B;
            } else if (grade_score > 79) {
                return C;
            } else if ( grade_score > 69) {
                return D;
            } else if (grade_score > 59) {
                return F;
            } else {
                return N;
            }
        }
    }

}

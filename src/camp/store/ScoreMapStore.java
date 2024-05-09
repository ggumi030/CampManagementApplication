package camp.store;

import camp.model.Score;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreMapStore {
    //key : studentID  value : Score => HashMap
    HashMap<String, ArrayList<Score>> scores;
    //Score : 회차 과목 점수 등급 수강생id

    public ScoreMapStore(){
        this.scores = new HashMap<>();
    }

    //저장 ggumi
    public void save(String studentId ,Score score) {
        this.scores.get(studentId).add(score);
    }

    //checkExistKey
    public void checkExistKey(String studentId){
        if(!scores.containsKey(studentId)){ //Hash map에 해당 key값 없을 때
            scores.put(studentId, new ArrayList<>()); //새로 생성
        }
    }

    //출력
    public HashMap<String,ArrayList<Score>> findAll() {
        return this.scores;
    }
}

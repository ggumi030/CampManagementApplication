package camp.store;

import camp.model.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreMapStore {

    Map<String, List<Score>> scores;

    public ScoreMapStore(){
        this.scores = new HashMap<>();
    }


    public void save(String studentId ,Score score) {
        this.scores.get(studentId).add(score);
    }


    public void checkExistKey(String studentId){
        if(!scores.containsKey(studentId)){
            scores.put(studentId, new ArrayList<>());
        }
    }

    public Map<String, List<Score>> findAll() {
        return this.scores;
    }

    public List<Score> findScoresByStudentId(String studentId) {
        return this.scores.get(studentId);
    }
}

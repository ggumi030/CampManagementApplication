package camp.store;

import camp.model.Score;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreMapStore {

    HashMap<String, ArrayList<Score>> scores;


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


    public HashMap<String,ArrayList<Score>> findAll() {
        return this.scores;
    }
}

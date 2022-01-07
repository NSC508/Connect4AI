package data_structures;

import java.util.ArrayList;

public class RandomSelector<T> {
    private ArrayList<T> objects = new ArrayList<T>();
    private ArrayList<Double> scores = new ArrayList<Double>();

    private double total_score = 0;

    //adds an object to the RandomSelector
    public void add(T obj, double score) {
        objects.add(obj);
        scores.add(score);
        total_score += score;
    }

    //returns a random element inside the RandomSelector
    public T getRandom() {
        double random = Math.random() * total_score;
        for (int i = 0; i < objects.size(); i++) {
            random -= scores.get(i);
            if (random <= 0) {
                return objects.get(i);
            }
        }
        return null;
    }

    //resets the RandomSelector 
    public void clear() {
        objects.clear();
        scores.clear();
        total_score = 0;
    }
}

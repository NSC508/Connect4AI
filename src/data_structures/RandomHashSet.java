package data_structures;

import java.util.ArrayList;
import java.util.HashSet;

import genome.Gene;

public class RandomHashSet<T> {
    HashSet<T> set;
    ArrayList<T> data;

    //constructor
    public RandomHashSet() {
        set = new HashSet<T>();
        data = new ArrayList<T>();
    }

    //finds out if an object is in the RandomHashSet
    public boolean contains(T obj) {
        return set.contains(obj);
    }

    //returns a random element inside the RandomHashSet
    public T getRandom() {
        return data.get((int) (Math.random() * data.size()));
    }

    //returns the size of the RandomHashSet
    public int size() {
        return data.size();
    }

    //adds an object to the RandomHashSet
    public void add(T obj) {
        if (!set.contains(obj)) {
            set.add(obj);
            data.add(obj);
        }
    }

    //adds a Gene to the RandomHashSet in a sorted way
    public void addSorted(Gene object) {
        for (int i = 0; i < this.size(); i++) {
            int innovation = ((Gene)data.get(i)).getInnovationNumber();
            if (object.getInnovationNumber() < innovation) {
                data.add(i, (T) object);
                set.add((T) object);
                return;
            }
        }
        data.add((T)object);
        set.add((T)object);
    }

    //clear the RandomHashSet
    public void clear() {
        set.clear();
        data.clear();
    }

    //gets an element at a specific index
    public T get(int index) {
        return data.get(index);
    }

    //removes an element from the RandomHashSet at a specific index
    public void remove(int index) {
        set.remove(data.get(index));
        data.remove(index);
    }
    
    //removes an element from the RandomHashSet
    public void remove(T obj) {
        set.remove(obj);
        data.remove(obj);
    }

    //getters
    public HashSet<T> getSet() {
        return set;
    }

    public ArrayList<T> getData() {
        return data;
    }
}

package calculations;

import java.util.ArrayList;

public class Node implements Comparable<Node>{
    private double x;
    private double output; 
    private ArrayList<Connection> connections = new ArrayList<Connection>();

    //constructor
    public Node(double x) {
        this.x = x;
    }

    public void calculate() {
        double sum = 0;
        for (Connection c : connections) {
            if (c.isEnabled()) {
                sum += c.getWeight() * c.getFrom().getOutput();
            }
        }
        output = activationFunction(sum);
    }

    private double activationFunction(double x) {
        return 1d / (1d + Math.exp(-x));
    }

    //setters
    public void setX(double x) {
        this.x = x;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    //getters
    public double getX() {
        return x;
    }

    public double getOutput() {
        return output;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    //compareTo method
    @Override
    public int compareTo(Node o) {
        if (this.x < o.getX()) {
            return 1;
        } else if (this.x > o.getX()) {
            return -1;
        } else {
            return 0;
        }
    }

}

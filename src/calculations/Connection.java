package calculations;

public class Connection {
    private Node from;
    private Node to;

    private double weight;
    private boolean enabled = true;

    //constructor
    public Connection(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    //returns the from node
    public Node getFrom() {
        return from;
    }

    //sets the from node
    public void setFrom(Node from) {
        this.from = from;
    }

    //gets the to node
    public Node getTo() {
        return to;
    }

    //sets the to node
    public void setTo(Node to) {
        this.to = to;
    }

    //gets the weight
    public double getWeight() {
        return weight;
    }

    //sets the weight
    public void setWeight(double weight) {
        this.weight = weight;
    }

    //gets the enabled status
    public boolean isEnabled() {
        return enabled;
    }

    //sets the enabled status
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

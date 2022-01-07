package genome;

import neat.Neat;

public class ConnectionGene extends Gene{
    private NodeGene from;
    private NodeGene to;

    private double weight;
    private boolean enabled = true;

    //constructor
    public ConnectionGene(int innovation_number, NodeGene from, NodeGene to, double weight) {
        super(innovation_number);
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    //returns the from node
    public NodeGene getFrom() {
        return from;
    }

    //sets the from node
    public void setFrom(NodeGene from) {
        this.from = from;
    }

    //gets the to node
    public NodeGene getTo() {
        return to;
    }

    //sets the to node
    public void setTo(NodeGene to) {
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

    //equals method
    public boolean equals(Object o) {
        if (o instanceof ConnectionGene) {
            ConnectionGene other = (ConnectionGene) o;
            return other.getFrom().equals(this.getFrom()) && other.getTo().equals(this.getTo());
        }
        return false;
    }

    //hashcode method
    public int hashCode() {
        return this.from.getInnovationNumber() * Neat.MAX_NODES + this.to.getInnovationNumber();
    }
}

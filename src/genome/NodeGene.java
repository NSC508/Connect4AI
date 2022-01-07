package genome;

public class NodeGene extends Gene{
    private double x;
    private double y;

    //constructor
    public NodeGene(int innovation_number) {
        super(innovation_number);
    }

    //gets the x coordinate
    public double getX() {
        return x;
    }

    //sets the x coordinate
    public void setX(double x) {
        this.x = x;
    }

    //gets the y coordinate
    public double getY() {
        return y;
    }

    //sets the y coordinate
    public void setY(double y) {
        this.y = y;
    }

    //equals method 
    public boolean equals(Object o) {
        if (o instanceof NodeGene) {
            NodeGene other = (NodeGene) o;
            return other.getInnovationNumber() == this.getInnovationNumber();
        }
        return false;
    }

    //hashcode method
    public int hashCode() {
        return this.getInnovationNumber();
    }
}

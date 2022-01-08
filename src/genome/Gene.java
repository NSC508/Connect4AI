package genome;

public class Gene {
    protected int innovation_number;

    //consructor
    public Gene(int innovation_number) {
        this.innovation_number = innovation_number;
    }

    public Gene() {
        
    }

    //returns the innovation number
    public int getInnovationNumber() {
        return innovation_number;
    }

    //sets the innovation number
    public void setInnovationNumber(int innovation_number) {
        this.innovation_number = innovation_number;
    }
}

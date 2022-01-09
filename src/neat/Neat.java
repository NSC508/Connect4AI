package neat;

import java.util.HashMap;

import data_structures.RandomHashSet;
import genome.ConnectionGene;
import genome.Genome;
import genome.NodeGene;
import visual.Frame;

public class Neat {
    public static final int MAX_NODES = (int) Math.pow(2, 20);

    private double C1 = 1;
    private double C2 = 1;
    private double C3 = 1;
    
    private double WEIGHT_SHIFT_STRENGTH = 0.3; 
    private double WEIGHT_RANDOM_STRENGTH = 1;

    private double PROBABILITY_MUTATE_LINK = 0.4;
    private double PROBABILITY_MUTATE_NODE = 0.4;
    private double PROBABILITY_MUTATE_WEIGHT_SHIFT = 0.4;
    private double PROBABILITY_MUTATE_WEIGHT_RANDOM = 0.4;
    private double PROBABILITY_MUTATE_TOGGLE_LINK = 0.4;

    private HashMap<ConnectionGene, ConnectionGene> allConnections = new HashMap<ConnectionGene, ConnectionGene>();
    private RandomHashSet<NodeGene> allNodes = new RandomHashSet<NodeGene>();
    private int inputSize; 
    private int outputSize;
    private int maxClients;

    //constructor
    public Neat(int inputSize, int outputSize, int clients) {
        this.reset(inputSize, outputSize, clients);
    }

    //creates an empty genome
    public Genome createGenome() {
        Genome g = new Genome(this);
        for (int i = 0; i < inputSize + outputSize; i++) {
            g.getNodes().add(getNewNode(i + 1));
        }
        return g;
    }

    //reset method
    public void reset(int inputSize, int outputSize, int clients) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.maxClients = clients;

        allNodes.clear();
        allConnections.clear();

        //creates the input nodes
        for (int i = 0; i < inputSize; i++) {
            NodeGene node = getNewNode(); 
            node.setX(0.1);
            node.setY((i + 1) / (double) (inputSize + 1));
        }

        //creates the output nodes
        for (int i = 0; i < outputSize; i++) {
            NodeGene node = getNewNode(); 
            node.setX(0.9);
            node.setY((i + 1) / (double) (inputSize + 1));
        }
    }

    //copy a connection gene
    public ConnectionGene getConnection(ConnectionGene con) {
        ConnectionGene c = new ConnectionGene(con.getFrom(), con.getTo());
        c.setWeight(con.getWeight());
        c.setEnabled(con.isEnabled());
        return c;
    }

    //gets a connection between two nodes
    public ConnectionGene getConnection(NodeGene from, NodeGene to) {
        ConnectionGene connectionGene = new ConnectionGene(from, to);

        if (allConnections.containsKey(connectionGene)) {
            connectionGene.setInnovationNumber(allConnections.get(connectionGene).getInnovationNumber());
        } else {
            connectionGene.setInnovationNumber(allConnections.size() + 1);
            allConnections.put(connectionGene, connectionGene);
        }

        return connectionGene;
    }

    //gets a new node
    public NodeGene getNewNode() {
        NodeGene node = new NodeGene(allNodes.size() + 1);
        allNodes.add(node);
        return node;
    }
    
    //gets a node with a specific id
    public NodeGene getNewNode(int id) {
        if (id <= allNodes.size()) {
            return allNodes.get(id - 1);
        }
        return getNewNode();
    }

    //getters
    public int getInputSize() {
        return inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public int getClients() {
        return maxClients;
    }

    public double getC1() {
        return C1;
    }

    public double getC2() {
        return C2;
    }

    public double getC3() {
        return C3;
    }

    public double getWeightShiftStrength() {
        return WEIGHT_SHIFT_STRENGTH;
    }

    public double getWeightRandomStrength() {
        return WEIGHT_RANDOM_STRENGTH;
    }

    public double getProbabilityMutateLink() {
        return PROBABILITY_MUTATE_LINK;
    }

    public double getProbabilityMutateNode() {
        return PROBABILITY_MUTATE_NODE;
    }

    public double getProbabilityMutateWeightShift() {
        return PROBABILITY_MUTATE_WEIGHT_SHIFT;
    }

    public double getProbabilityMutateWeightRandom() {
        return PROBABILITY_MUTATE_WEIGHT_RANDOM;
    }

    public double getProbabilityMutateToggleLink() {
        return PROBABILITY_MUTATE_TOGGLE_LINK;
    }

    //setters
    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = outputSize;
    }

    public void setClients(int clients) {
        this.maxClients = clients;
    }

    public void setC1(double c1) {
        C1 = c1;
    }

    public void setC2(double c2) {
        C2 = c2;
    }

    public void setC3(double c3) {
        C3 = c3;
    }

    //main
    public static void main(String[] args) {
        Neat neat = new Neat(3, 2, 0);
        new Frame(neat.createGenome());
    }
}

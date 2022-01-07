package neat;

import java.util.HashMap;

import data_structures.RandomHashSet;
import genome.ConnectionGene;
import genome.Genome;
import genome.NodeGene;

public class Neat {
    public static final int MAX_NODES = (int) Math.pow(2, 20);
    
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
    public static ConnectionGene getConnection(ConnectionGene con) {
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

    //main
    public static void main(String[] args) {
        Neat neat = new Neat(3, 3, 100);
        Genome g = neat.createGenome();
        System.out.println(g.getNodes().size());
    }
}

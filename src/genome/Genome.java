package genome;

import calculations.Calculator;
import data_structures.RandomHashSet;
import neat.Neat;

public class Genome {
    private RandomHashSet<ConnectionGene> connections = new RandomHashSet<ConnectionGene>();
    private RandomHashSet<NodeGene> nodes = new RandomHashSet<NodeGene>();
    private Neat neat;


    //constructor
    public Genome(Neat neat) {
        this.neat = neat;
    }

    //calculate the distance between this genome g1 and a second genome g2
    public double distance(Genome g2) {
        Genome g1 = this; 

        int highestInnovationGene1 = 0; 
        int highestInnovationGene2 = 0;

        if (g1.getConnections().size() != 0) {
            highestInnovationGene1 = g1.getConnections().get(g1.getConnections().size() - 1).getInnovationNumber();
        }

        if (g2.getConnections().size() != 0) {
            highestInnovationGene2 = g2.getConnections().get(g2.getConnections().size() - 1).getInnovationNumber();
        }

        if (highestInnovationGene1 < highestInnovationGene2) {
            Genome g = g1; 
            g1 = g2; 
            g2 = g;
        }

        int index_1 = 0; 
        int index_2 = 0;

        int disjoint = 0;
        int excess = 0; 
        double weight_diff = 0; 
        int similar = 0;

        while (index_1 < g1.getConnections().size() && index_2 < g2.getConnections().size()) {
            ConnectionGene gene1 = g1.getConnections().get(index_1);
            ConnectionGene gene2 = g2.getConnections().get(index_2);
            int in1 = gene1.getInnovationNumber();
            int in2 = gene2.getInnovationNumber();
            
            if (in1 == in2) {
                similar++;
                weight_diff = Math.abs(gene1.getWeight() - gene2.getWeight());
                index_1++;
                index_2++;
            } if (in1 > in2) {
                disjoint++;
                index_2++;
            } else {
                disjoint++;
                index_1++;
            }
        }

        excess = g1.getConnections().size() - index_1;

        double N = Math.max(g1.getConnections().size(), g2.getConnections().size());
        if (N < 20) {
            N = 1;
        }

        return neat.getC1() *  disjoint / N + neat.getC2() * excess / N + neat.getC3() * weight_diff;
    }

    //mutate method
    public void mutate() {
        if (neat.getProbabilityMutateLink() > Math.random()) {
            mutateLink();
        }
        if (neat.getProbabilityMutateNode() > Math.random()) {
            mutateNode();
        }
        if (neat.getProbabilityMutateWeightShift() > Math.random()) {
            mutateWeightShift();
        }
        if (neat.getProbabilityMutateWeightRandom() > Math.random()) {
            mutateWeightRandom();
        }
        if (neat.getProbabilityMutateToggleLink() > Math.random()) {
            mutateLinkToggle();
        }

    }

    //adds a new link between two nodes
    public void mutateLink() {
        for (int i = 0; i < 100; i++) {
            NodeGene a = nodes.getRandom();
            NodeGene b = nodes.getRandom();
            if (a.getX() == b.getX()) {
                continue;
            }

            ConnectionGene con;
            if (a.getX() < b.getX()) {
                con = new ConnectionGene(a, b);
            } else {
                con = new ConnectionGene(b, a);
            }

            if (connections.contains(con)) {
                continue;
            }

            con = neat.getConnection(con.getFrom(), con.getTo());
            con.setWeight((Math.random() * 2 - 1) * neat.getWeightRandomStrength());
            connections.addSorted(con);

            return;
        }
    }

    //adds a new node between two existing nodes
    public void mutateNode() {
        ConnectionGene con = connections.getRandom();
        if (con == null) {
            return;
        }

        NodeGene from = con.getFrom();
        NodeGene to = con.getTo();

        NodeGene middle = neat.getNewNode();
        middle.setX((from.getX() + to.getX()) / 2);
        middle.setY((from.getY() + to.getY()) / 2 + Math.random() * 0.1 - 0.05);

        ConnectionGene con1 = neat.getConnection(from, middle);
        ConnectionGene con2 = neat.getConnection(middle, to);

        con1.setWeight(1);
        con2.setWeight(con.getWeight());
        con2.setEnabled(con.isEnabled());

        connections.remove(con);
        connections.add(con1);
        connections.add(con2);

        nodes.add(middle);
    }

    //takes a connection and shifts the weight by a factor
    public void mutateWeightShift() {
        ConnectionGene con = connections.getRandom();
        if (con != null) {
            con.setWeight(con.getWeight() + (Math.random() * 2 - 1) * neat.getWeightShiftStrength());
        }
    }

    //shifts a weight but with a random weight 
    public void mutateWeightRandom() {
        ConnectionGene con = connections.getRandom();
        if (con != null) {
            con.setWeight((Math.random() * 2 - 1) * neat.getWeightRandomStrength());
        }
    }

    //toggles a mutate link
    public void mutateLinkToggle() {
        ConnectionGene con = connections.getRandom();
        if (con != null) {
            con.setEnabled(!con.isEnabled());
        }
    }

    //crossover method
    public Genome crossover(Genome g1, Genome g2) {
        Neat neat = g1.getNeat();
        Genome genome = neat.createGenome();
        int index_1 = 0; 
        int index_2 = 0;

        while (index_1 < g1.getConnections().size() && index_2 < g2.getConnections().size()) {
            ConnectionGene gene1 = g1.getConnections().get(index_1);
            ConnectionGene gene2 = g2.getConnections().get(index_2);
            int in1 = gene1.getInnovationNumber();
            int in2 = gene2.getInnovationNumber();
            
            if (in1 == in2) {

                if (Math.random() > 0.5) {
                    genome.getConnections().add(neat.getConnection(gene1));
                } else {
                    genome.getConnections().add(neat.getConnection(gene2));
                }  
                index_1++;
                index_2++;
            } if (in1 > in2) {
                index_2++;
            } else {
                genome.getConnections().add(neat.getConnection(gene1));
                index_1++;
            }
        }

        while (index_1 < g1.getConnections().size()) {
            ConnectionGene gene1 = g1.getConnections().get(index_1);
            genome.getConnections().add(neat.getConnection(gene1));
            index_1++;
        }

        for(ConnectionGene c : genome.getConnections().getData()) {
            genome.getNodes().add(c.getFrom());
            genome.getNodes().add(c.getTo());
        }

        return genome;
    }

    //gets the connections
    public RandomHashSet<ConnectionGene> getConnections() {
        return connections;
    }

    //gets the nodes
    public RandomHashSet<NodeGene> getNodes() {
        return nodes;
    }
    
    //gets the neat
    public Neat getNeat() {
        return neat;
    }
}

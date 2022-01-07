package genome;

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

    // //distance method
    // public double distance(Genome genome) {
    //     double distance = 0;
    //     for (ConnectionGene connection : connections) {
    //         if (genome.connections.contains(connection)) {
    //             distance += Math.abs(connection.getWeight() - genome.connections.get(genome.connections.indexOf(connection)).getWeight());
    //         } else {
    //             distance += Math.abs(connection.getWeight());
    //         }
    //     }
    //     for (NodeGene node : nodes) {
    //         if (!genome.nodes.contains(node)) {
    //             distance += Math.abs(node.getX()) + Math.abs(node.getY());
    //         }
    //     }
    //     return distance;
    // }

    // //mutate method
    // public void mutate() {
    //     if (Math.random() < neat.getMutationRate()) {
    //         if (Math.random() < neat.getAddConnectionMutationRate()) {
    //             addConnectionMutation();
    //         } else if (Math.random() < neat.getAddNodeMutationRate()) {
    //             addNodeMutation();
    //         } else if (Math.random() < neat.getDisableMutationRate()) {
    //             disableMutation();
    //         } else if (Math.random() < neat.getEnableMutationRate()) {
    //             enableMutation();
    //         } else if (Math.random() < neat.getMutateWeightMutationRate()) {
    //             mutateWeightMutation();
    //         }
    //     }
    // }

    // //crossover method
    // public Genome crossover(Genome g1, Genome g2) {
    //     Genome child = new Genome(neat);
    //     for (ConnectionGene connection : g1.connections) {
    //         if (Math.random() < neat.getCrossoverRate()) {
    //             if (g2.connections.contains(connection)) {
    //                 child.connections.add(new ConnectionGene(connection));
    //             }
    //         }
    //     }
    //     for (ConnectionGene connection : g2.connections) {
    //         if (Math.random() < neat.getCrossoverRate()) {
    //             if (!child.connections.contains(connection)) {
    //                 child.connections.add(new ConnectionGene(connection));
    //             }
    //         }
    //     }
    //     for (NodeGene node : g1.nodes) {
    //         if (Math.random() < neat.getCrossoverRate()) {
    //             if (g2.nodes.contains(node)) {
    //                 child.nodes.add(new NodeGene(node));
    //             }
    //         }
    //     }
    //     for (NodeGene node : g2.nodes) {
    //         if (Math.random() < neat.getCrossoverRate()) {
    //             if (!child.nodes.contains(node)) {
    //                 child.nodes.add(new NodeGene(node));
    //             }
    //         }
    //     }
    //     return child;
    // }

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

package calculations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import data_structures.RandomHashSet;
import genome.ConnectionGene;
import genome.Genome;
import genome.NodeGene;

public class Calculator {

    private ArrayList<Node> inputNodes = new ArrayList<Node>();
    private ArrayList<Node> hiddenNodes = new ArrayList<Node>();
    private ArrayList<Node> outputNodes = new ArrayList<Node>();
    public Calculator(Genome g) {
        RandomHashSet<NodeGene> nodes = g.getNodes();
        RandomHashSet<ConnectionGene> connections = g.getConnections();

        HashMap<Integer, Node> nodeMap = new HashMap<Integer, Node>();

        for (NodeGene n : nodes.getData()) {
            Node node = new Node(n.getX());
            nodeMap.put(n.getInnovationNumber(), node);

            if (n.getX() <= 0.1) {
                inputNodes.add(node);
            } else if (n.getX() >= 0.9) {
                outputNodes.add(node);
            } else {
                hiddenNodes.add(node);
            }
        }

        hiddenNodes.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.compareTo(o2);
            }
        });

        for (ConnectionGene c : connections.getData()) {
            NodeGene from = c.getFrom();
            NodeGene to = c.getTo();

            Node nodeFrom = nodeMap.get(from.getInnovationNumber());
            Node nodeTo = nodeMap.get(to.getInnovationNumber());
            Connection con = new Connection(nodeFrom, nodeTo);
            con.setWeight(c.getWeight());
            con.setEnabled(c.isEnabled());

            nodeTo.getConnections().add(con);
        }
    }

    public double[] calculate(double... input) {
        if (input.length != inputNodes.size()) {
            throw new IllegalArgumentException("Input size does not match");
        }
        for (int i = 0; i < inputNodes.size(); i++) {
            inputNodes.get(i).setOutput(input[i]);
        }
        for (Node node : hiddenNodes) {
            node.calculate();
        }
        double[] output = new double[outputNodes.size()];
        for (int i = 0; i < outputNodes.size(); i++) {
            outputNodes.get(i).calculate();
            output[i] = outputNodes.get(i).getOutput();
        }
        return output;
    }
}

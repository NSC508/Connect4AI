package neat;

import java.nio.ReadOnlyBufferException;

import data_structures.RandomHashSet;

public class Species {
    
    private RandomHashSet<Client> clients = new RandomHashSet<Client>();
    private Client representative;
    private double score; 

    public Species(Client representative) {
        this.representative = representative;
        this.representative.setSpecies(this);
        clients.add(representative);
    }

    public boolean put(Client client) {
        if(client.distance(this.representative) < this.representative.getGenome().getNeat().getCP()) {
            client.setSpecies(this);
            clients.add(representative);
            return true;
        }
        return false;
    }

    public void forcePut(Client client) {
        client.setSpecies(this);
        clients.add(representative);
    }

    public void goExtinct() {
        for(Client client : this.clients.getData()) {
            client.setSpecies(null);
        }
    }

    public void evaluateScore() {
        double v = 0; 
        for (Client c : clients.getData()) {
            v += c.getScore();
        }
        score = v / clients.size();
    }

    public void reset() {
        representative = clients.getRandom();
        for (Client c : clients.getData()) {
            c.setSpecies(null);
        }
        clients.clear();

        clients.add(representative);
        representative.setSpecies(this);
        score = 0;
    }
}

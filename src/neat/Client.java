package neat;

import calculations.Calculator;
import genome.Genome;

public class Client {
    private Calculator calculator;

    private Genome genome;
    private double score; 
    private Species species; 


    //generates the calculator 
    public void generateCalculator() {
        this.calculator = new Calculator(genome);
    }

    //calculates an array of double 
    public double[] calculate(double... input) {
        if (this.calculator == null) {
            generateCalculator();
        }
        return this.calculator.calculate(input);
    }

    public double distance(Client other) {
        return this.getGenome().distance(other.genome);
    }

    public void mutate() {
        getGenome().mutate();
    }

    //getters
    public Calculator getCalculator() {
        return calculator;
    }

    public Genome getGenome() {
        return genome;
    }

    public double getScore() {
        return score;
    }

    public Species getSpecies() {
        return species;
    }

    //setters
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}

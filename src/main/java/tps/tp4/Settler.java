package tps.tp4;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public abstract class Settler {

    private String name;
    private int age;
    private double health;
    private double happiness;
    private Colony colony;
    private double entertainmentImpact;
    private double foodImpact;
    private double healthImpact;
    private double workImpact;
    private Job assignedJob;

    public Settler(String name, int age, Colony colony) {
        Random r = new Random();
        this.name = name;
        this.age = age;
        this.health = 100;
        this.happiness = r.nextDouble() * 50 + 50; // Initial happiness between 50-100
        this.colony = colony;
        calcImpacts(r);
        this.assignedJob = null;
    }

    /**
     * Calculates the impacts of different factors on the settler's happiness.
     * The impacts are randomly generated and sanitized to ensure they sum to 1.
     * @param r the random number generator used to create the impacts
     */
    private void calcImpacts(Random r) {
        double[] aux = new double[3];
        aux[0] = r.nextDouble();
        aux[1] = r.nextDouble();
        aux[2] = r.nextDouble();
        Arrays.sort(aux);
        this.entertainmentImpact = aux[0];
        this.foodImpact = aux[1] - aux[0];
        this.healthImpact = aux[2] - aux[1];
        this.workImpact = 1 - aux[2];
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public double updateHappiness() {
        this.health = (entertainmentImpact * colony.getEntertainment() + 
                        foodImpact * colony.getFood() + 
                        healthImpact * this.health + 
                        workImpact * (assignedJob != null? 0 : 100)) * 100;
        return health;
    }

}

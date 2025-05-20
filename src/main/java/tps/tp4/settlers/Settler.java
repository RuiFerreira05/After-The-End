package tps.tp4.settlers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import tps.tp4.Colony;
import tps.tp4.Job;

public class Settler implements Serializable{

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

    public boolean healthWarning;
    public boolean happinessWarning;

    public Settler(String name, Colony colony) {
        Random r = new Random();
        this.name = name;
        this.age = r.nextInt(61) + 19; // Random age between 19-80
        this.health = 100;
        this.happiness = r.nextDouble() * 50 + 50; // Initial happiness between 50-100
        this.colony = colony;
        calcImpacts(r);
        this.assignedJob = null;
        this.healthWarning = false;
        this.happinessWarning = false;
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
        this.happiness = (entertainmentImpact * colony.getEntertainment() + 
                        foodImpact * colony.getFood() + 
                        healthImpact * this.health + 
                        workImpact * (assignedJob != null? 0 : 100)) * 100;
        return happiness;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
        if (health < 50) {
            this.healthWarning = true;
        } else {
            this.healthWarning = false;
        }
    }

    public Job getAssignedJob() {
        return assignedJob;
    }

    public double getEntertainmentImpact() {
        return entertainmentImpact;
    }

    public double getFoodImpact() {
        return foodImpact;
    }

    public double getHealthImpact() {
        return healthImpact;
    }

    public double getWorkImpact() {
        return workImpact;
    }

    // John Doe - 19 years old - 100% health - 100% happiness - Miner
    public String getSettlerInfo(boolean detailed) {
        // A bit of repeated code, but avoids using a StringBuilder
        if (!detailed) {
            return this.name + " - " + this.age + " years old - " + (int) this.health + "% health - " + (int) this.happiness + "% happiness - " + (assignedJob != null ? assignedJob.getName() : "Unemployed");
        } else {
            return this.name + " - " + this.age + " years old - " + (int) this.health + "% health - " + (int) this.happiness + "% happiness - " + (assignedJob != null ? assignedJob.getName() : "Unemployed") +
                    "\nEntertainment impact: " + (int) (entertainmentImpact * 100) + "%" +
                    "\nFood impact: " + (int) (foodImpact * 100) + "%" +
                    "\nHealth impact: " + (int) (healthImpact * 100) + "%" +
                    "\nWork impact: " + (int) (workImpact * 100) + "%";
        }
    }
}

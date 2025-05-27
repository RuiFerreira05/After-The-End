package tps.tp4.settlers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import tps.tp4.Colony;

public class Settler implements Serializable{

    private String name;
    private int age;
    private double health;
    private double happiness;
    private Colony colony;
    private double entertainmentImpact;
    private double foodImpact;
    private double healthImpact;

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
        this.healthWarning = false;
        this.happinessWarning = false;
    }

    /**
     * Calculates the impacts of different factors on the settler's happiness.
     * The impacts are randomly generated and sanitized to ensure they sum to 1.
     * @param r the random number generator used to create the impacts
     */
    private void calcImpacts(Random r) {
        double[] aux = new double[2];
        aux[0] = r.nextDouble();
        aux[1] = r.nextDouble();
        Arrays.sort(aux);
        this.entertainmentImpact = aux[0];
        this.foodImpact = aux[1] - aux[0];
        this.healthImpact = 1 - aux[1];
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
        this.happiness = Math.min((entertainmentImpact * colony.getEntertainment() + 
                        foodImpact * colony.getFood() + 
                        healthImpact * this.health), 100);
        if (happiness < 30) {
            this.happinessWarning = true;
        } else {
            this.happinessWarning = false;
        }
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

    public double getEntertainmentImpact() {
        return entertainmentImpact;
    }

    public double getFoodImpact() {
        return foodImpact;
    }

    public double getHealthImpact() {
        return healthImpact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEntertainmentImpact(double entertainmentImpact) {
        this.entertainmentImpact = entertainmentImpact;
    }

    public void setFoodImpact(double foodImpact) {
        this.foodImpact = foodImpact;
    }

    public void setHealthImpact(double healthImpact) {
        this.healthImpact = healthImpact;
    }

    // John Doe - 19 years old - 100% health - 100% happiness - Miner
    public String getSettlerInfo(boolean detailed) {
        // A bit of repeated code, but avoids using a StringBuilder
        if (!detailed) {
            return this.name + " - " + this.age + " years old - " + (int) this.health + "% health - " + (int) this.happiness + "% happiness" + (healthWarning ? " - LOW HEALTH" : "") + (happinessWarning ? " - LOW HAPPINESS" : "");
        } else {
            return this.name + " - " + this.age + " years old - " + (int) this.health + "% health - " + (int) this.happiness + "% happiness" + (healthWarning ? " - LOW HEALTH" : "") + (happinessWarning ? " - LOW HAPPINESS" : "") +
                    "\nEntertainment impact: " + (int) (entertainmentImpact * 100) + "%" +
                    "\nFood impact: " + (int) (foodImpact * 100) + "%" +
                    "\nHealth impact: " + (int) (healthImpact * 100) + "%";
        }
    }
}

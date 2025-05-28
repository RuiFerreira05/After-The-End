package tps.tp4.settlers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import tps.tp4.Colony;

/**
 * Represents a settler in the colony, with attributes for health, happiness, and impacts.
 */
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

    /**
     * Constructs a new Settler with a random age and happiness.
     * @param name The name of the settler.
     * @param colony The colony the settler belongs to.
     */
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
     * Calculates the impacts of entertainment, food, and health on happiness.
     * The impacts are randomly generated and normalized to sum to 1.
     * @param r The random number generator.
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

    /**
     * Gets the name of the settler.
     * @return The settler's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the age of the settler.
     * @return The settler's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the happiness value of the settler.
     * @return The settler's happiness.
     */
    public double getHappiness() {
        return happiness;
    }

    /**
     * Sets the happiness value of the settler.
     * @param happiness The new happiness value.
     */
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    /**
     * Updates and returns the settler's happiness based on impacts and colony state.
     * @return The updated happiness value.
     */
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

    /**
     * Gets the health value of the settler.
     * @return The settler's health.
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the health value of the settler and updates health warnings.
     * @param health The new health value.
     */
    public void setHealth(double health) {
        this.health = health;
        if (health < 50) {
            this.healthWarning = true;
        } else {
            this.healthWarning = false;
        }
    }

    /**
     * Gets the entertainment impact factor.
     * @return The entertainment impact.
     */
    public double getEntertainmentImpact() {
        return entertainmentImpact;
    }

    /**
     * Gets the food impact factor.
     * @return The food impact.
     */
    public double getFoodImpact() {
        return foodImpact;
    }

    /**
     * Gets the health impact factor.
     * @return The health impact.
     */
    public double getHealthImpact() {
        return healthImpact;
    }

    /**
     * Sets the name of the settler.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the age of the settler.
     * @param age The new age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets the entertainment impact factor.
     * @param entertainmentImpact The new entertainment impact.
     */
    public void setEntertainmentImpact(double entertainmentImpact) {
        this.entertainmentImpact = entertainmentImpact;
    }

    /**
     * Sets the food impact factor.
     * @param foodImpact The new food impact.
     */
    public void setFoodImpact(double foodImpact) {
        this.foodImpact = foodImpact;
    }

    /**
     * Sets the health impact factor.
     * @param healthImpact The new health impact.
     */
    public void setHealthImpact(double healthImpact) {
        this.healthImpact = healthImpact;
    }

    /**
     * Returns a string with settler information, optionally detailed.
     * @param detailed Whether to include detailed impact info.
     * @return The settler info string.
     */
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

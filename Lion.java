import java.util.List;
import java.util.Random;

/**
 * This file is part of the Predator-Prey Simulation.
 *
 * A Lion predator in the simulation.
 *
 * @author Ubayd Khan (k20044237) and Omar Ahmad (k21052417)
 * @version 2022.03.02
 */
public class Lion extends Predator {

    // define fields
    private static final double BREEDING_PROBABILITY = 0.115;
    private static final double EATING_PROBABILITY = 0.6;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int BREEDING_AGE = 32;
    private static final int MAX_AGE = 130;

    private static final int DEFAULT_FOOD_LEVEL = 19;

    private static final double SPREAD_DISEASE_PROBABILITY = 0.01;
    private static final double DEATH_BY_DISEASE_PROBABILITY = 0.01;

    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for a lion in the simulation.
     *
     * @param foodLevel The food level the lion is at initially.
     * @param randomAge Whether we assign this lion a random age or not.
     * @param field The field in which this lion resides.
     * @param location The location in which this lion is spawned into.
     */
    public Lion(int foodLevel, boolean randomAge, Field field, Location location) {
        super(foodLevel, randomAge, field, location);
    }

    /**
     * Getter method for the probability to breed of the lion.
     *
     * @return A double value representing the breeding probability.
     */
    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Getter method for the maximum litter size of the lion's newborns.
     *
     * @return An integer value representing the maximum allowed litter size.
     */
    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Getter method for the maximum age of the lion.
     *
     * @return An integer value representing the maximum age.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Getter method for the age of breeding of the lion.
     *
     * @return A double value representing the breeding age.
     */
    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Getter method to return this lion's disease spreading probability.
     *
     * @return The lion's disease spreading probability.
     */
    @Override
    protected double getDiseaseSpreadProbability() {
        return SPREAD_DISEASE_PROBABILITY;
    }

    /**
     * Getter method to return the probability this lion dies from disease.
     *
     * @return The lion's disease death probability.
     */
    @Override
    protected double getDeathByDiseaseProbability() {
        return DEATH_BY_DISEASE_PROBABILITY;
    }

    /**
     * Create a new instance of Lion.
     * @param field The field in which the spawn will reside in.
     * @param location The location in which the spawn will occupy.
     * @return A new lion instance.
     */
    @Override
    protected Organism createNewOrganism(Field field, Location location) {
        return new Lion(DEFAULT_FOOD_LEVEL, true, field, location);
    }

    /**
     * Method for what the lion does, i.e. what is always run at every step.
     *
     * @param newLions A list of all newborn lions in this simulation step.
     * @param weather The current state of weather in the simulation.
     * @param time The current state of time in the simulation.
     */
    @Override
    public void act(List<Entity> newLions, Weather weather, TimeOfDay time) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {

            giveBirth(newLions);

            if (time == TimeOfDay.NIGHT){
                return;
            }

            if (rand.nextDouble() <= getDeathByDiseaseProbability() ) {
                remove();
                return;
            }

            // Move towards a source of food if found.
            Location newLocation;

            if (rand.nextDouble() <= getDiseaseSpreadProbability() ) {
                newLocation = findAnimalToInfect();
            } else {
                newLocation = findFood();
            }

            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }

            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                //setDead();
                remove();
            }
        }
    }

    /**
     * Getter method to return this lion's probability of eating if food is found.
     *
     * @return The lion's eating probability.
     */
    @Override
    public double getEatingProbability() {
        return EATING_PROBABILITY;
    }

    /**
     * Checks all adjacent location for lions that meet specific
     * breeding conditions, and returns true if it is even possible.
     *
     * @return Whether this lion can breed or not.
     */
    @Override
    public boolean canBreed() {
        if (getAge() < getBreedingAge()) {
            return false;
        }

        for (Location loc : getField().adjacentLocations(getLocation())) {
            Object animal = getField().getObjectAt(loc);
            if (animal instanceof Lion) {
                Lion lion = (Lion) animal;
                if (!(((lion.isMale() && isMale())) || ((!lion.isMale() && !isMale())))) {
                    return true;
                }
            }
        }
        return false;
    }
}

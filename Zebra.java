import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This file is part of the Predator-Prey Simulation.
 *
 * A Zebra prey in the simulation.
 *
 * @author Ubayd Khan (k20044237) and Omar Ahmad (k21052417)
 * @version 2022.03.02
 */
public class Zebra extends Prey {

    // define fields
    private static final double BREEDING_PROBABILITY = 0.305;
    private static final int MAX_LITTER_SIZE = 3;
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 150;

    private static final int DEFAULT_FOOD_VALUE = 5;

    private static final double SPREAD_DISEASE_PROBABILITY = 0.1;
    private static final double DEATH_BY_DISEASE_PROBABILITY = 0.001;

    // shared random generator to generate consistent results
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for a Zebra in the simulation.
     *
     * @param randomAge Whether we assign this zebra a random age or not.
     * @param field The field in which this zebra resides.
     * @param location The location in which this zebra is spawned into.
     */
    public Zebra(int foodValue, boolean randomAge, Field field, Location location) {
        super(foodValue, randomAge, field, location);
    }

    /**
     * Getter method for the probability to breed of the zebra.
     *
     * @return A double value representing the breeding probability.
     */
    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Getter method for the maximum litter size of the zebra's newborns.
     *
     * @return An integer value representing the maximum allowed litter size.
     */
    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Getter method for the maximum age of the zebra.
     *
     * @return An integer value representing the maximum age.
     */
    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Getter method for the age of breeding of the zebra.
     *
     * @return A double value representing the breeding age.
     */
    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Getter method to return this zebra's disease spreading probability.
     *
     * @return The zebra's disease spreading probability.
     */
    @Override
    protected double getDiseaseSpreadProbability() {
        return SPREAD_DISEASE_PROBABILITY;
    }

    /**
     * Getter method to return the probability this zebra dies from disease.
     *
     * @return The zebra's disease death probability.
     */
    @Override
    protected double getDeathByDiseaseProbability() {
        return DEATH_BY_DISEASE_PROBABILITY;
    }

    /**
     * Create a new instance of zebra.
     * @param field The field in which the spawn will reside in.
     * @param location The location in which the spawn will occupy.
     * @return A new zebra instance.
     */
    @Override
    protected Organism createNewOrganism(Field field, Location location) {
        return new Zebra(DEFAULT_FOOD_VALUE, true, field, location);
    }

    /**
     * Method for what the zebra does, i.e. what is always run at every step.
     *
     * @param newZebras A list of all newborn zebras in this simulation step.
     * @param weather The current state of weather in the simulation.
     * @param time The current state of time in the simulation.
     */
    @Override
    public void act(List<Entity> newZebras, Weather weather, TimeOfDay time) {
        incrementAge();
        setActiveness(1);//resets activeness

        if(isAlive()) {
            giveBirth(newZebras);

            if (rand.nextDouble() <= getDeathByDiseaseProbability() ) {
                remove();
                return;
            }

            if (time == TimeOfDay.AROUND_MIDNIGHT){
                this.setActiveness(0.9);
            }

            if (rand.nextDouble() <= getActiveness()){
                // Try to move into a free location.
                Location newLocation;

                if (rand.nextDouble() <= getDiseaseSpreadProbability() ) {
                    newLocation = findAnimalToInfect();
                } else {
                    newLocation = findFood();
                }

                // Random chance to do either?
                if ((newLocation == null) || (getFoodValue() > 10)) {
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }

                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    //setDead();
                    remove();
                }
            }

        } else {
            decayifDead();
        }
    }

    /**
     * Checks all adjacent location for zebras that meet specific
     * breeding conditions, and returns true if it is even possible.
     *
     * @return Whether this zebra can breed or not.
     */
    @Override
    public boolean canBreed() {
        if (getAge() < getBreedingAge()) {
            return false;
        }

        for (Location loc : getField().adjacentLocations(getLocation())) {
            Object animal = getField().getObjectAt(loc);
            if (animal instanceof Zebra) {
                Zebra zebra = (Zebra) animal;
                if (!(((zebra.isMale() && isMale())) || ((!zebra.isMale() && !isMale())))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Find a food source the zebra would want to eat.
     * @return The location of the food source.
     */
    @Override
    public Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();

            Object organism = field.getObjectAt(where);
            if(organism instanceof Plant) {
                Plant plant = (Plant) organism;

                if (plant.isAlive()) {
                    // kill plant
                    plant.setDead();
                    boolean eaten = eat(plant);

                    return eaten ? where : null;
                }
            }
        }
        return null;
    }
}

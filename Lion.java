import java.util.List;

public class Lion extends Predator {

    private static final double BREEDING_PROBABILITY = 0.115;
    private static final double EATING_PROBABILITY = 0.3;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int BREEDING_AGE = 35;
    private static final int MAX_AGE = 130;

    private static final int DEFAULT_FOOD_LEVEL = 20;

    private static final double SPREAD_DISEASE_PROBABILITY = 0.01;
    private static final double DEATH_BY_DISEASE_PROBABILITY = 0.01;

    public Lion(int foodLevel, boolean randomAge, Field field, Location location) {
        super(foodLevel, randomAge, field, location);
    }

    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected double getDiseaseSpreadProbability() {
        return SPREAD_DISEASE_PROBABILITY;
    }

    @Override
    protected double getDeathByDiseaseProbability() {
        return DEATH_BY_DISEASE_PROBABILITY;
    }

    @Override
    protected Organism createNewOrganism(Field field, Location location) {
        return new Lion(DEFAULT_FOOD_LEVEL, true, field, location);
    }

    @Override
    public void act(List<Entity> newPredators, Weather weather, TimeOfDay time) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {

            giveBirth(newPredators);

            //Only call givebirth method if its "night time" for the lion.
            //TODO: We could make the below three lines of code into an abstract method in Predator
            //TODO: class, or we could make it a SLEEP interface
            if (time == TimeOfDay.NIGHT){
                return;
            }

            if (getRandom().nextDouble() <= getDeathByDiseaseProbability() ) {
                remove();
                return;
            }

            // Move towards a source of food if found.
            Location newLocation;

            if (getRandom().nextDouble() <= getDiseaseSpreadProbability() ) {
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

    @Override
    public double getEatingProbability() {
        return EATING_PROBABILITY;
    }

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

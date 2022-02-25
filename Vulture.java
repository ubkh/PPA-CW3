public class Vulture extends Scavenger {

    private static final double BREEDING_PROBABILITY = 0.12;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int BREEDING_AGE = 20;
    private static final int MAX_AGE = 90;

    private static final int DEFAULT_FOOD_LEVEL = 15;

    public Vulture(int foodLevel, boolean randomAge, Field field, Location location) {
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
    protected Organism createNewOrganism(Field field, Location location) {
        return new Vulture(DEFAULT_FOOD_LEVEL, true, field, location);
    }

    @Override
    public boolean canBreed() {
        if (getAge() < getBreedingAge()) {
            return false;
        }

        for (Location loc : getField().adjacentLocations(getLocation())) {
            Object animal = getField().getObjectAt(loc);
            if (animal instanceof Vulture) {
                Vulture vulture = (Vulture) animal;
                if (!(((vulture.isMale() && isMale())) || ((!vulture.isMale() && !isMale())))) {
                    return true;
                }
            }
        }
        return false;
    }
}

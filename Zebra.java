import java.util.List;

public class Zebra extends Prey {

    private static final double BREEDING_PROBABILITY = 0.13;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 100;

    private static final int DEFAULT_FOOD_VALUE = 1;

    public Zebra(int foodValue, boolean randomAge, Field field, Location location) {
        super(foodValue, randomAge, field, location);
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
    protected Organism createNewOrganism(Field field, Location location) {
        return new Zebra(DEFAULT_FOOD_VALUE, true, field, location);
    }

    @Override
    public void act(List<Entity> newZebras) {
        incrementAge();
        if(isAlive()) {
            giveBirth(newZebras);
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
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
    public boolean canBreed() {
        if (getAge() < BREEDING_AGE) {
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
}

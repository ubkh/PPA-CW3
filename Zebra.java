import java.util.Iterator;
import java.util.List;

public class Zebra extends Prey {

    private static final double BREEDING_PROBABILITY = 0.2;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 100;

    private static final int DEFAULT_FOOD_VALUE = 1;
    private static final int DEFAULT_FOOD_LEVEL = 15;

    public Zebra(int foodValue, int foodLevel, boolean randomAge, Field field, Location location) {
        super(foodValue, foodLevel, randomAge, field, location);
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
        return new Zebra(DEFAULT_FOOD_VALUE, DEFAULT_FOOD_LEVEL, true, field, location);
    }

    @Override
    public void act(List<Entity> newZebras, Weather weather, TimeOfDay time) {
        incrementAge();
        incrementHunger();

        decayifDead();
        if(isAlive()) {
            giveBirth(newZebras);
            // Try to move into a free location.
            Location newLocation;

            System.out.println("FOOD " + getFoodLevel());
            if (getFoodLevel() < 10) {
                newLocation = findFood();
            } else {
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
    }

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

    @Override
    public Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();

            Object organism = field.getObjectAt(where);
            if(organism instanceof Grass) {
                Grass grass = (Grass) organism;
                // kills animal
                //prey.setDead();
                //eatOrLeave(prey);
                if (grass.isAlive()) {
                    //System.out.println("ALIVE");
                    grass.setDead();
                    // NOTE: ONLY RETURN WHERE IF EATEN
                    boolean eaten = eat(grass);

                    //return where;
                    return eaten ? where : null;
                }
            }
        }
        return null;
    }

    @Override
    public boolean eat(Consumable consumable) {
        incrementFoodLevel(consumable.getFoodValue());
        consumable.setEaten();
        return true;
    }
}

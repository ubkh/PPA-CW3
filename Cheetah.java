import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cheetah extends Predator {

    private static final double EATING_PROBABILITY = 0.8;

    Random random = new Random();

    public Cheetah(int foodLevel, boolean randomAge, Field field, Location location) {
        super(foodLevel, randomAge, field, location, 200);
    }

    @Override
    public void act(List<Animal> newLions) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newLions);
            // Move towards a source of food if found.
            Location newLocation = findFood();
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
                setDead();
            }
        }
    }

    protected boolean canBreed()
    {
        for (Location loc : getField().adjacentLocations(getLocation())) {

            Object animal = getField().getObjectAt(loc);

            if (animal instanceof Cheetah) {
                Cheetah cheetah = (Cheetah) animal;

                if (!cheetah.getLocation().equals(loc)) {
                    return false;
                }

                if (((cheetah.isMale() && isMale())) || ((!cheetah.isMale() && !isMale()))) {
                    return false;
                }
            }
        }

        return age >= breedingAge;
    }

    @Override
    public void eatOrLeave(Animal animal) {
        Prey prey = (Prey) animal;
        if (random.nextDouble() <= EATING_PROBABILITY) {
            animal.setEaten();
            incrementFoodLevel(prey.getFoodValue());
        }
    }

    @Override
    void giveBirth(List<Animal> newLions) {
        // New lions are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Cheetah young = new Cheetah(super.getFoodLevel(),true, field, loc);
            newLions.add(young);
        }
    }
}

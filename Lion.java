import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lion extends Predator {

    private static final double EATING_PROBABILITY = 0.8;

    Random random = new Random();

    public Lion(int foodLevel, boolean randomAge, Field field, Location location, ArrayList<Prey> prey) {
        super(foodLevel, randomAge, field, location, prey);
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
            Lion young = new Lion(super.getFoodLevel(),true, field, loc, super.getPrey());
            newLions.add(young);
        }
    }
}

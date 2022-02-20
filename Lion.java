import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lion extends Predator {

    private static final double EATING_PROBABILITY = 0.8;

    Random random = new Random();

    public Lion(int foodLevel, boolean randomAge, Field field, Location location) {
        super(foodLevel, randomAge, field, location, 150);
        super.breedingAge = 20;
        super.breedingProbability = 0.12;
        super.maxLitterSize = 2;
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
        //if (random.nextDouble() <= EATING_PROBABILITY) {
            animal.setEaten();
            incrementFoodLevel(prey.getFoodValue());
            System.out.println("PREY EATEN");
        //} else {
        //    System.out.println("PREY LEFT");
        //}
    }


    // TODO: Account for fact adjacent lion may have free adjacent location, but current one may not.
    @Override
    void giveBirth(List<Animal> newLions) {
        // New lions are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        //System.out.println("BIRTHS: " + births);
        for(int b = 0; b < births && free.size() > 0; b++) {
            //System.out.println("LION BIRTH");
            Location loc = free.remove(0);
            Lion young = new Lion(super.getFoodLevel(),true, field, loc);
            newLions.add(young);
        }
    }

    /**
     * An animal can breed if it is adjacent to another animal of the same species
     * and opposite gender, and has reached the breeding age.
     */
    protected boolean canBreed()
    {
//        //boolean returnValue = false;
//
//        if (age < breedingAge) {
//            return false;
//        }
//
//        for (Location loc : getField().adjacentLocations(getLocation())) {
//
//            Object animal = getField().getObjectAt(loc);
//
//            if (animal instanceof Lion) {
//                Lion lion = (Lion) animal;
//
////                if (!lion.getLocation().equals(loc)) {
////                    continue;
////                }
//
//                if (!(((lion.isMale() && isMale())) || ((!lion.isMale() && !isMale())))) {
//                    return true;
//                }
//            }
//        }
//        return false;
        return age >= breedingAge;
    }
}

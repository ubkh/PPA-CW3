import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Predator extends Animal implements AbleToEat, Hunger {

    private int foodLevel;
    //private int foodValue;
    //private static final int FOOD_VALUE = 9;

    /**
     * Create a new animal at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */

    // Note: Potentially replace ArrayList parameter with lambda
    public Predator(int foodLevel, boolean randomAge, Field field, Location location, int maxAge) {
        super(field, location);
        super.maxAge = maxAge;
        // Note: Replaced constant field RABBIT_FOOD_VALUE with stomachCapacity
        // We do this so max capacity doesn't depend on a single animal it eats
        if(randomAge) {
            System.out.println(maxAge);
            age = rand.nextInt(maxAge);
            this.foodLevel = rand.nextInt(foodLevel);
        }
        else {
            age = 0;
            this.foodLevel = foodLevel;
        }
    }

    /**
     * Make this predator act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born predators.
     */
    abstract public void act(List<Animal> newAnimals);

    protected Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Prey) {
                //TODO: just return where, and do the below elsewhere.
                //Rabbit rabbit = (Rabbit) animal;
                Prey prey = (Prey) animal;
                // kills animal
                if (prey.isAlive()) {
                    prey.setDead();
                    // random chance to eat
                    eatOrLeave(prey);
                    //foodLevel += prey.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    @Override
    public void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    protected int getFoodLevel() {
        return this.foodLevel;
    }


    @Override
    public void incrementFoodLevel(int foodLevel) {
        this.foodLevel += foodLevel;
    }
}

import java.util.Iterator;
import java.util.List;

// Test
// Note: FlyingAnimal can be used as an interface

public class Vulture extends Animal implements AbleToEat {

    private int foodLevel;

    /**
     * Create a new animal at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Vulture(Field field, Location location) {
        super(field, location);
    }

    @Override
    public void act(List<Animal> newVultures) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newVultures);
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
        animal.setEaten();
    }

    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Prey) {
                //Rabbit rabbit = (Rabbit) animal;
                Prey prey = (Prey) animal;
                if (!prey.isAlive()) {
                    // eat anything that is already dead
                    foodLevel += prey.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    void giveBirth(List<Animal> newAnimals) {

    }

    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
}

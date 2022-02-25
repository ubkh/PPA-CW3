import java.util.Iterator;
import java.util.List;

public abstract class Scavenger extends Animal implements AbleToEat {

    private int foodLevel;

    public Scavenger(int foodLevel, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.foodLevel = foodLevel;
    }

    @Override
    public void act(List<Entity> newScavengers, Weather weather, TimeOfDay time) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newScavengers);
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
                //setDead();
                remove();
            }
        }
    }

    @Override
    public Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Prey) {
                Prey prey = (Prey) animal;
                // eats animal if dead only
                if (!prey.isAlive()) {
                    //System.out.println("EATEN DEAD");
                    eatOrLeave(prey);
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    public boolean eatOrLeave(Prey prey) {
        // the scavenger does not leave its prey
        prey.setEaten();
        incrementFoodLevel(prey.getFoodValue());
        return true;
    }

    @Override
    public void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            remove();
        }
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public void setFoodLevel(int level) {
        this.foodLevel = level;
    }

    @Override
    public void incrementFoodLevel(int foodLevel) {
        this.foodLevel += foodLevel;
    }

    @Override
    abstract public boolean canBreed();
}

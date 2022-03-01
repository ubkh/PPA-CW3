import java.util.Iterator;
import java.util.List;

public abstract class Scavenger extends Animal implements AbleToEat {

    private int foodLevel;

    public Scavenger(int foodLevel, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    @Override
    public void act(List<Entity> newScavengers, Weather weather, TimeOfDay time) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newScavengers);

            //Could turn into interface
            if (time == TimeOfDay.SUNSET){
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
                    eat(prey);
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    public boolean eat(Consumable consumable) {
        // the scavenger does not leave its prey
        consumable.setEaten();
        incrementFoodLevel(consumable.getFoodValue());
        return true;
    }

    @Override
    abstract public boolean canBreed();

    protected int getFoodLevel() {
        return this.foodLevel;
    }

    private void setFoodLevel(int level) {
        this.foodLevel = level;
    }

    public void incrementFoodLevel(int foodLevel) {
        this.foodLevel += foodLevel;
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    public void incrementHunger() {
        //if (isAlive()) {
        foodLevel--;
        if (foodLevel <= 0) {
            remove();
        }
        //}
    }
}

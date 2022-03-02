import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Predator extends Animal {

    private static final Random rand = Randomizer.getRandom();

    private int foodLevel;
    private boolean isAsleep;

    public Predator(int foodLevel, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.isAsleep = false;
        this.foodLevel = foodLevel;
    }

    @Override
    abstract public void act(List<Entity> newPredators, Weather weather, TimeOfDay time);

    @Override
    public Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();

            Object animal = field.getObjectAt(where);
            if(animal instanceof Prey) {
                Prey prey = (Prey) animal;
                // kills animal
                //prey.setDead();
                //eatOrLeave(prey);
                if (prey.isAlive()) {
                    //System.out.println("ALIVE");
                    prey.setDead();
                    // NOTE: ONLY RETURN WHERE IF EATEN
                    // random chance to eat
                    boolean eaten = eat(prey);

                    //return where;
                    return eaten ? where : null;
                }
            }
        }
        return null;
    }

    abstract public double getEatingProbability();

    @Override
    public boolean eat(Consumable consumable) {
        if (rand.nextDouble() <= getEatingProbability()) {
            incrementFoodLevel(consumable.getFoodValue());
            consumable.setEaten();
            //System.out.println("EATEN PREY");
            return true;
        } else {
            //System.out.println("LEFT PREY");
            return false;
        }
    }

    @Override
    abstract protected boolean canBreed();

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

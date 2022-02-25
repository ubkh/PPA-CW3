import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Predator extends Animal implements AbleToEat {

    private int foodLevel;

    private Random rand = new Random();

    public Predator(int foodLevel, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.foodLevel = foodLevel;
    }

    @Override
    abstract public void act(List<Entity> newPredators, Weather weather, TimeOfDay time);

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
                    boolean eaten = eatOrLeave(prey);

                    //return where;
                    return eaten ? where : null;
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
        //if (isAlive()) {
            foodLevel--;
            if (foodLevel <= 0) {
                remove();
            }
        //}
    }

    protected int getFoodLevel() {
        return this.foodLevel;
    }

    protected void setFoodLevel(int level) {
        this.foodLevel = level;
    }

    @Override
    public void incrementFoodLevel(int foodLevel) {
        this.foodLevel += foodLevel;
    }

    abstract public double getEatingProbability();

    @Override
    public boolean eatOrLeave(Prey prey) {
        if (rand.nextDouble() <= getEatingProbability()) {
            incrementFoodLevel(prey.getFoodValue());
            prey.setEaten();
            //System.out.println("EATEN PREY");
            return true;
        } else {
            //System.out.println("LEFT PREY");
            return false;
        }
    }

    @Override
    abstract protected boolean canBreed();
}

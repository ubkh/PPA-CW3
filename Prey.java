import java.util.List;
import java.util.Random;

public abstract class Prey extends Animal implements Consumable {

    protected static final Random rand = Randomizer.getRandom();
    private static final double DEFAULT_ACTIVENESS = 1;
    private int foodValue;
    private double activeness;  // denotes how likely it is for the act method to be called
    // This is so that we can have

    public Prey(int foodValue, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.foodValue = foodValue;
        this.activeness = DEFAULT_ACTIVENESS;
    }

    @Override
    abstract public void act(List<Entity> newPrey, Weather weather, TimeOfDay time);

    @Override
    abstract public boolean canBreed();

    @Override
    public void setEaten() {
        if(getLocation() != null) {
            getField().clear(getLocation());
            setLocationToNull();
            setField(null);
        }
    }

    @Override
    public int getFoodValue() {
        return this.foodValue;
    }

    protected void incrementFoodValue(int foodValue) {
        this.foodValue += foodValue;
    }

    @Override
    public boolean eat(Consumable consumable) {
        if ((consumable.isPoisonous()) && (!isInfected())) {
            //System.out.println(isInfected());
            infect(this);
            //setInfected(true);
            //System.out.println("INFECTED PREY");
        }
        incrementFoodValue(consumable.getFoodValue());
        consumable.setEaten();
        return true;
    }

    // Prey can never be poisonous.
    @Override
    public boolean isPoisonous() {
        return false;
    }

    public double getActiveness() {
        return activeness;
    }

    public void setActiveness(double activeness) {
        this.activeness = activeness;
    }
}

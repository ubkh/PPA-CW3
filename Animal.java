import java.util.List;

public abstract class Animal extends Organism implements AbleToEat {

    private final Gender gender;
    private boolean isAsleep;

    private int foodLevel;

    public Animal(int foodLevel, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        // randomly assign male or female
        this.gender = Gender.getRandom();
        this.isAsleep = false;

        this.foodLevel = foodLevel;
    }

    @Override
    abstract public void act(List<Entity> newAnimals, Weather weather, TimeOfDay time);

    @Override
    abstract public boolean eat(Consumable consumable);

    @Override
    abstract protected boolean canBreed();


    private Gender getGender() {
        return this.gender;
    }

    protected boolean isMale() {
        return getGender() == Gender.MALE;
    }

    protected void setAsleep(boolean asleep) {
        isAsleep = asleep;
    }

    protected boolean getIsAsleep() {
         return isAsleep;
    }

    protected int getFoodLevel() {
        return this.foodLevel;
    }

    private void setFoodLevel(int level) {
        this.foodLevel = level;
    }

    @Override
    public void incrementFoodLevel(int foodLevel) {
        this.foodLevel += foodLevel;
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
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
}